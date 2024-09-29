package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.facade.JibunQueryFacade;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.service.TransactionApartmentCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TransactionApartmentCommandFacade {

    private final JibunQueryFacade jibunQueryFacade;

    private final TransactionApartmentCreateService transactionApartmentCreateService;

    public void create(List<ApartmentTransactionResponse.Item> items) {
        List<Jibun> jibuns = new CopyOnWriteArrayList<>();
        items.parallelStream()
                .forEach(item -> jibuns.addAll(jibunQueryFacade.findByLegalDongCodeAndJibunNumber(item.legalDongCode(), item.jibun())));

        Map<Jibun, List<ApartmentTransactionResponse.Item>> itemsByJibunId = items.stream()
                .collect(Collectors.groupingBy(
                        item -> jibuns.stream()
                                .filter(j -> j.isSameJibun(item))
                                .findAny()
                                .orElseThrow(),
                        Collectors.toList()));

        itemsByJibunId.forEach((jibun, itemList) -> transactionApartmentCreateService.create(jibun, itemList));


    }
}
