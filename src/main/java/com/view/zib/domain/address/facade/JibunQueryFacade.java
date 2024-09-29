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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JibunQueryFacade {

    private final TransactionApartmentQueryFacade transactionApartmentQueryFacade;

    private final JibunQueryService jibunQueryService;

    private final TransactionApartmentPublisher transactionApartmentPublisher;

    private final VWorldClient vWorldClient;

    public List<JibunSearchResponse> findAddressesInUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan) {
        List<JibunSearchResultDTO> jibunSearchResultDTOS = jibunQueryService.findAddressesInUtmk(utmkX, utmkY, utmkXSpan, utmkYSpan);
        Set<String> sggCodes = jibunSearchResultDTOS.stream()
                .map(jibunSearchResultDTO -> jibunSearchResultDTO.getLegalDongCode().substring(0, 5))
                .collect(Collectors.toSet());

        String searchYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String searchMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));

        // 이미 저장된 거래 정보가 있는지 확인
        List<DuplicateTransactionBuildingDTO> duplicatedTransactionApartments = transactionApartmentQueryFacade.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
        Set<String> registeredSggCodes = duplicatedTransactionApartments.stream()
                .map(DuplicateTransactionBuildingDTO::getSggCode)
                .collect(Collectors.toSet());

        // 이미 저장된 거래 정보는 제외
        sggCodes.removeIf(registeredSggCodes::contains);

        List<ApartmentTransactionResponse.Item> itemsToSave = new CopyOnWriteArrayList<>();

        sggCodes.forEach(legalDongCode -> {
                    Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(
                            legalDongCode,
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))
                    );
                    if (optional.isPresent()) {
                        ApartmentTransactionResponse apartmentTransactionResponse = optional.get();
                        List<ApartmentTransactionResponse.Item> items = apartmentTransactionResponse.body().items().item();
                        itemsToSave.addAll(items);
                    }
                });
        transactionApartmentPublisher.publishEvent(itemsToSave);

        List<Long> jibunIds = jibunSearchResultDTOS.stream().map(JibunSearchResultDTO::getJibunId).toList();
        List<TransactionApartment> transactionApartments =  transactionApartmentQueryFacade.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIds);
        return JibunSearchResponse.of(jibunSearchResultDTOS, transactionApartments);

    }

    public List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber) {
        return jibunQueryService.findByLegalDongCodeAndJibunNumber(legalDongCode, jibunNumber);
    }
}
