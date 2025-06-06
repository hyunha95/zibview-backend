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
import com.view.zib.domain.transaction.facade.TransactionApartmentCommandFacade;
import com.view.zib.domain.transaction.facade.TransactionApartmentQueryFacade;
import com.view.zib.domain.transaction.hash.TransactionApartmentHash;
import com.view.zib.domain.user.facade.AnonymousUserCommandFacade;
import com.view.zib.domain.user.facade.AnonymousUserQueryFacade;
import com.view.zib.global.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JibunQueryFacade {

    private final TransactionApartmentQueryFacade transactionApartmentQueryFacade;
    private final AnonymousUserQueryFacade anonymousUserQueryFacade;

    private final JibunQueryService jibunQueryService;

    private final VWorldClient vWorldClient;

    private final JibunDetailCommandFacade jibunDetailCommandFacade;
    private final TransactionApartmentCommandFacade transactionApartmentCommandFacade;
    private final AnonymousUserCommandFacade anonymousUserCommandFacade;

    /**
     * 최근 한달 간의 실거래가 정보 조회
     * 현재 10/01이라면 09/01 ~ 10/01까지의 실거래가 정보 조회
     * 현재 10/31이라면 09/01 ~ 10/31까지의 실거래가 정보 조회
     *
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @param zoomLevel
     * @param anonymousUserUUID
     * @return
     */
    public List<JibunSearchResponse> findAddressesInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, int zoomLevel, UUID anonymousUserUUID) {
        long start = System.currentTimeMillis();
        Set<Long> searchedJibunIds = anonymousUserQueryFacade.members(anonymousUserUUID);
        log.info("took {}ms to get searchedJibunIds", System.currentTimeMillis() - start);

        List<JibunSearchResultDTO> jibunSearchResultDTOS = jibunQueryService.findAddressesInUtmkAndNotInJibunIds(minX, minY, maxX, maxY, searchedJibunIds); //searchedJibunIds

        Set<Long> foundJibunIds = jibunSearchResultDTOS.stream().map(JibunSearchResultDTO::getJibunId).collect(Collectors.toSet());
        anonymousUserCommandFacade.addSet(anonymousUserUUID, foundJibunIds);

        LocalDate now = LocalDate.now();
        LocalDate prevMonth = now.minusMonths(1);
        start = System.currentTimeMillis();


        List<TransactionApartmentHash> transactionApartments =
                transactionApartmentQueryFacade.findByJibunIdInAndDealYearAndDealMonth(
                        foundJibunIds,
                        List.of(YearMonth.of(prevMonth.getYear(), prevMonth.getMonthValue()),
                                YearMonth.of(now.getYear(), now.getMonthValue()))
                );
        long end = System.currentTimeMillis();
        log.info("transactionApartments: time: {}ms", (end - start));

        return JibunSearchResponse.of(jibunSearchResultDTOS, transactionApartments);
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

    public ApartmentResponse findJibunByManagementNo(String managementNo) {
        Jibun jibun = jibunQueryService.getJibunByManagementNo(managementNo);

        return findJibunById(jibun.getId());
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
        for (String yearMonth : yearMonthRangeFrom) {
            Thread.ofVirtual().start(fetchTransactionInfo(jibun, yearMonth));
        }

        transactionApartments = transactionApartmentQueryFacade.findByJibunIdAndDealYearAfterAndExclusiveUseArea(jibunId, fromYear, exclusiveUseArea);
        return TransactionApartmentResponse.from(transactionApartments);
    }

    public Runnable fetchTransactionInfo(Jibun jibun, String yearMonth) {
        return () -> {
            try {
                List<TransactionApartmentDTO> newTransactionApartmentDTOs = new ArrayList<>();
                Optional<ApartmentTransactionResponse> optional = vWorldClient.getRTMSDataSvcAptTradeDev(jibun.getSsgCode(), yearMonth);
                if (optional.isEmpty()) {
                    return;
                }

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
                transactionApartmentCommandFacade.bulkInsert(newTransactionApartmentDTOs);
            } catch (ResourceAccessException e) {
                log.error("VWorld API 호출 중 에러 발생", e);
            }
        };
    }


}


















