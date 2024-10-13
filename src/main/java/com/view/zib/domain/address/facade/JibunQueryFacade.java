package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.controller.response.ApartmentResponse;
import com.view.zib.domain.address.controller.response.JibunSearchResponse;
import com.view.zib.domain.address.controller.response.TransactionApartmentResponse;
import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.repository.dto.TransactionApartmentDTO;
import com.view.zib.domain.address.service.JibunQueryService;
import com.view.zib.domain.client.vworld.client.VWorldClient;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.event.publisher.TransactionApartmentPublisher;
import com.view.zib.domain.transaction.facade.TransactionApartmentCommandFacade;
import com.view.zib.domain.transaction.facade.TransactionApartmentQueryFacade;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.global.utils.DateUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JibunQueryFacade {

    private final TransactionApartmentQueryFacade transactionApartmentQueryFacade;
    private final JibunDetailQueryFacade jibunDetailQueryFacade;

    private final JibunQueryService jibunQueryService;

    private final TransactionApartmentPublisher transactionApartmentPublisher;

    private final VWorldClient vWorldClient;

    private final JibunDetailCommandFacade jibunDetailCommandFacade;
    private final TransactionApartmentCommandFacade transactionApartmentCommandFacade;

    public List<JibunSearchResponse> findAddressesInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, List<Long> jibunIds) {
        List<JibunSearchResultDTO> jibunSearchResultDTOS = jibunQueryService.findAddressesInUtmkAndNotInJibunIds(minX, minY, maxX, maxY, jibunIds);
        Set<String> sggCodes = jibunSearchResultDTOS.stream()
                .map(jibunSearchResultDTO -> jibunSearchResultDTO.getLegalDongCode().substring(0, 5))
                .collect(Collectors.toSet());

        final int searchYear = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));
        final int searchMonth = Integer.parseInt(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM")));

        // 이미 저장된 거래 정보가 있는지 확인
        List<DuplicateTransactionBuildingDTO> duplicatedTransactionApartments = transactionApartmentQueryFacade.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
        Set<String> registeredSggCodes = duplicatedTransactionApartments.stream()
                .map(DuplicateTransactionBuildingDTO::getSggCode)
                .collect(Collectors.toSet());

        // 이미 저장된 거래 정보는 제외
        sggCodes.removeIf(registeredSggCodes::contains);

        List<TransactionApartment> newTransactionApartments = new ArrayList<>();
        sggCodes.forEach(legalDongCode -> {
            try {
                Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(
                        legalDongCode,
                        String.valueOf(searchYear) + searchMonth
                );
                if (optional.isPresent()) {
                    ApartmentTransactionResponse apartmentTransactionResponse = optional.get();
                    List<ApartmentTransactionResponse.Item> items = apartmentTransactionResponse.body().items().item();

                    // 거래내역 저장
                    List<Jibun> foundJibuns = jibunQueryService.findByMultipleLegalDongCodeAndJibunNumber(items);
                    for (Jibun foundJibun : foundJibuns) {
                        newTransactionApartments.addAll(items.stream()
                                .filter(foundJibun::isSameJibun)
                                .map(item -> TransactionApartment.from(foundJibun, item))
                                .toList());
                    }
                }
            } catch (ResourceAccessException e) {
                log.error("VWorld API 호출 중 에러 발생", e);
            }
        });

        // TODO make it work
//        transactionApartmentCommandFacade.create(newTransactionApartments);

        List<Long> jibunIdList = jibunSearchResultDTOS.stream().map(JibunSearchResultDTO::getJibunId).toList();
        List<TransactionApartment> transactionApartments = transactionApartmentQueryFacade.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIdList);
        return JibunSearchResponse.of(jibunSearchResultDTOS, transactionApartments);

    }

    public List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber) {
        return jibunQueryService.findByLegalDongCodeAndJibunNumber(legalDongCode, jibunNumber);
    }


    /**
     * 지번 ID로 지번 정보 조회
     * @param jibunId
     * @return
     */
    public ApartmentResponse findJibunById(Long jibunId) {
        Jibun jibun = jibunQueryService.getById(jibunId);

        if (jibun.getJibunDetail() == null) {
            jibunDetailCommandFacade.create(jibun);
        }

        List<TransactionApartment> foundTransactionApartments = transactionApartmentQueryFacade.findByJibunIdGroupByExclusiveUseAreaOrderByYMD(jibunId);

        return ApartmentResponse.from(jibun, foundTransactionApartments);
    }

    public List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<ApartmentTransactionResponse.Item> items) {
        return jibunQueryService.findByMultipleLegalDongCodeAndJibunNumber(items);
    }

    /**
     * 이전 n년도의 실거래가 정보 조회
     * 실거래가는 2000년 이후의 데이터만 조회
     *
     * @param jibunId
     * @param fromYear
     * @param exclusiveUseArea
     */
    @Transactional
    public List<TransactionApartmentResponse> fecthPastYearsTransactions(Long jibunId, Integer fromYear, BigDecimal exclusiveUseArea) {
        Jibun jibun = jibunQueryService.getById(jibunId);

        // TODO DTO로 조회하기
        List<TransactionApartment> transactionApartments = transactionApartmentQueryFacade.findByJibunIdAndDealYearAfterAndExclusiveUseArea(jibunId, fromYear, exclusiveUseArea);
        Set<String> registeredDealYearMonth = transactionApartments.stream()
                .map(TransactionApartment::getDealYearMonth)
                .collect(Collectors.toSet());

        // 이미 저장된 거래 정보는 제외
        Set<String> yearMonthRangeFrom = DateUtils.generateYearMonthRangeFrom(LocalDate.now(), fromYear);
        yearMonthRangeFrom.removeAll(registeredDealYearMonth);

        // 실거래가 정보 조회
        List<TransactionApartmentDTO> newTransactionApartmentDTOs = new CopyOnWriteArrayList<>();
        yearMonthRangeFrom.parallelStream().forEach(yearMonth -> {
            try {
                Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(
                        jibun.getSsgCode(),
                        yearMonth
                );
                if (optional.isPresent()) {
                    ApartmentTransactionResponse apartmentTransactionResponse = optional.get();
                    List<ApartmentTransactionResponse.Item> items = apartmentTransactionResponse.body().items().item();

                    // 거래내역 저장
                    List<Jibun> foundJibuns = jibunQueryService.findByMultipleLegalDongCodeAndJibunNumber(items);


                    for (Jibun jibunEntity : foundJibuns) {
                        newTransactionApartmentDTOs.addAll(items.stream()
                                .filter(jibunEntity::isSameJibun)
                                .map(item -> TransactionApartmentDTO.from(jibunEntity, item))
                                .toList());

                    }

                }
            } catch (ResourceAccessException e) {
                log.error("VWorld API 호출 중 에러 발생", e);
            }
        });

        transactionApartmentCommandFacade.create(newTransactionApartmentDTOs);

        transactionApartments = transactionApartmentQueryFacade.findByJibunIdAndDealYearAfterAndExclusiveUseArea(jibunId, fromYear, exclusiveUseArea);
        return TransactionApartmentResponse.from(transactionApartments);
    }
}


















