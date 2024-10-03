package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.facade.JibunQueryFacade;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.service.TransactionApartmentCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentCommandFacade {

    private final JibunQueryFacade jibunQueryFacade;

    private final TransactionApartmentCreateService transactionApartmentCreateService;

    public void create(List<ApartmentTransactionResponse.Item> items) {
        List<Jibun> jibuns = new CopyOnWriteArrayList<>();

        if (CollectionUtils.isNotEmpty(items)) {
            long start = System.currentTimeMillis();
            log.info("[Query] Start to find jibuns by legalDongCode and jibun. items size: {}", items.size());
            items.parallelStream().forEach(item -> {
                        List<Jibun> foundJibuns = jibunQueryFacade.findByLegalDongCodeAndJibunNumber(item.legalDongCode(), item.jibun());
                        if (CollectionUtils.isNotEmpty(foundJibuns)) {
                            jibuns.addAll(foundJibuns);
                        }
                    });
            log.info("[Query] End to find jibuns by legalDongCode and jibun. elapsed time: {} ms", System.currentTimeMillis() - start);
        }

        Map<Jibun, List<ApartmentTransactionResponse.Item>> itemsByJibunId = items.stream()
                .filter(item -> jibuns.stream().anyMatch(j -> j.isSameJibun(item)))
                .collect(Collectors.groupingBy(
                        item -> jibuns.stream()
                                .filter(j -> j.isSameJibun(item))
                                .findAny()
                                .orElseThrow(),
                        Collectors.toList()));

        List<TransactionApartment> newTransactionApartments = new ArrayList<>();
        itemsByJibunId.forEach((jibun, itemList) -> {
            itemList.forEach(item -> {
                TransactionApartment transactionApartment = TransactionApartment.from(jibun, item);
                newTransactionApartments.add(transactionApartment);
            });
        });

        transactionApartmentCreateService.create(newTransactionApartments);
    }
}
