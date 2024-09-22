package com.view.zib.domain.transaction.event.listener;

import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import com.view.zib.domain.transaction.entity.BuildingTransaction;
import com.view.zib.domain.transaction.event.BuildingTransactionCreateEvent;
import com.view.zib.domain.transaction.facade.BuildingTransactionCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BuildingTransactionCreateListener {

    private final BuildingTransactionCommandFacade buildingTransactionCommandFacade;

    @EventListener
    public void onApplicationEvent(BuildingTransactionCreateEvent buildingTransactionCreateEvent) {
        log.info("Received spring building transaction create buildingTransactionCreateEvent");
        OfficeTelTransactionClientDTO officeTelTransactionClientDTO = buildingTransactionCreateEvent.officeTelTransactionClientDTO();
        List<BuildingTransaction> buildingTransactions = officeTelTransactionClientDTO.body().items().item().stream()
                .map(BuildingTransaction::from)
                .toList();
        buildingTransactionCommandFacade.create(buildingTransactions);
    }
}
