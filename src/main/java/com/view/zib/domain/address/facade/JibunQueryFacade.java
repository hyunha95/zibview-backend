package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.controller.response.ApartmentResponse;
import com.view.zib.domain.address.controller.response.JibunSearchResponse;
import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.service.JibunQueryService;
import com.view.zib.domain.client.vworld.client.VWorldClient;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.event.publisher.TransactionApartmentPublisher;
import com.view.zib.domain.transaction.facade.TransactionApartmentQueryFacade;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.global.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Component
public class JibunQueryFacade {

    private final TransactionApartmentQueryFacade transactionApartmentQueryFacade;

    private final JibunQueryService jibunQueryService;

    private final TransactionApartmentPublisher transactionApartmentPublisher;

    private final VWorldClient vWorldClient;

    private final JibunDetailCommandFacade jibunDetailCommandFacade;

    public List<JibunSearchResponse> findAddressesInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, List<Long> jibunIds) {
        List<JibunSearchResultDTO> jibunSearchResultDTOS = jibunQueryService.findAddressesInUtmkAndNotInJibunIds(minX, minY, maxX, maxY, jibunIds);
        Set<String> sggCodes = jibunSearchResultDTOS.stream()
                .map(jibunSearchResultDTO -> jibunSearchResultDTO.getLegalDongCode().substring(0, 5))
                .collect(Collectors.toSet());

        final String searchYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        final String searchMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));

        // 이미 저장된 거래 정보가 있는지 확인
        List<DuplicateTransactionBuildingDTO> duplicatedTransactionApartments = transactionApartmentQueryFacade.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
        Set<String> registeredSggCodes = duplicatedTransactionApartments.stream()
                .map(DuplicateTransactionBuildingDTO::getSggCode)
                .collect(Collectors.toSet());

        // 이미 저장된 거래 정보는 제외
        sggCodes.removeIf(registeredSggCodes::contains);

        List<ApartmentTransactionResponse.Item> itemsToSave = new CopyOnWriteArrayList<>();
        sggCodes.parallelStream().forEach(legalDongCode -> {
            try {
                Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(
                        legalDongCode,
                        searchYear + searchMonth
                );
                if (optional.isPresent()) {
                    ApartmentTransactionResponse apartmentTransactionResponse = optional.get();
                    List<ApartmentTransactionResponse.Item> items = apartmentTransactionResponse.body().items().item();
                    itemsToSave.addAll(items);
                }
            } catch (ResourceAccessException e) {
                log.error("VWorld API 호출 중 에러 발생", e);
            }
        });
        transactionApartmentPublisher.publishEvent(itemsToSave);

        List<Long> jibunIdList = jibunSearchResultDTOS.stream().map(JibunSearchResultDTO::getJibunId).toList();
        List<TransactionApartment> transactionApartments = transactionApartmentQueryFacade.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIdList);
        return JibunSearchResponse.of(jibunSearchResultDTOS, transactionApartments);

    }

    public List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber) {
        return jibunQueryService.findByLegalDongCodeAndJibunNumber(legalDongCode, jibunNumber);
    }

    /**
     * 실거래가는 2000년 이후의 데이터만 조회
     */
    public ApartmentResponse findJibunById(Long jibunId) {
        Jibun jibun = jibunQueryService.getById(jibunId);

        if (jibun.getJibunDetail() == null) {
            jibunDetailCommandFacade.create(jibun);
        }

        List<TransactionApartment> transactionApartments = jibun.getTransactionApartments();
        Set<String> registeredDealYearMonth = transactionApartments.stream()
                .map(TransactionApartment::getDealYearMonth)
                .collect(Collectors.toSet());

        // 이미 저장된 거래 정보는 제외
        Set<String> yearMonthRangeFrom2000 = DateUtils.generateYearMonthRangeFrom2000(LocalDate.now());
        yearMonthRangeFrom2000.removeAll(registeredDealYearMonth);

        // 실거래가 정보 조회
        List<ApartmentTransactionResponse.Item> itemsToSave = new CopyOnWriteArrayList<>();
        yearMonthRangeFrom2000.forEach(yearMonth -> {
            try {
                Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(
                        jibun.getSsgCode(),
                        yearMonth
                );
                if (optional.isPresent()) {
                    ApartmentTransactionResponse apartmentTransactionResponse = optional.get();
                    List<ApartmentTransactionResponse.Item> items = apartmentTransactionResponse.body().items().item();
                    itemsToSave.addAll(items);
                    // 이벤트 발행
                    transactionApartmentPublisher.publishEvent(items);
                }
            } catch (ResourceAccessException e) {
                log.error("VWorld API 호출 중 에러 발생", e);
            }
        });



        // 실거래가 정보 저장 & 엔티티 관계 설정
        if (CollectionUtils.isNotEmpty(itemsToSave)) {
            List<TransactionApartment> newTransactionApartments = itemsToSave.stream()
                    .map(item -> TransactionApartment.from(jibun, item))
                    .toList();

            jibun.addEntity(newTransactionApartments);
        }

        return ApartmentResponse.from(jibun);
    }

    public List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<ApartmentTransactionResponse.Item> items) {
        return jibunQueryService.findByMultipleLegalDongCodeAndJibunNumber(items);
    }
}


















