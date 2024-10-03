package com.view.zib.domain.address.facade;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class JibunQueryFacade {

    private final TransactionApartmentQueryFacade transactionApartmentQueryFacade;

    private final JibunQueryService jibunQueryService;

    private final TransactionApartmentPublisher transactionApartmentPublisher;

    private final VWorldClient vWorldClient;

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

        sggCodes.forEach(legalDongCode -> {
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
}
