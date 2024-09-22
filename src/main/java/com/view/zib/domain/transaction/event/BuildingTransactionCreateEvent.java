package com.view.zib.domain.transaction.event;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;

import java.time.LocalDateTime;

public record BuildingTransactionCreateEvent(Jibun jibun, OfficeTelTransactionClientDTO officeTelTransactionClientDTO, LocalDateTime eventDateTime) {

    public static BuildingTransactionCreateEvent of(
            Jibun jibun,
            OfficeTelTransactionClientDTO officeTelTransactionClientDTO,
            LocalDateTime eventDateTime
    ) {
        return new BuildingTransactionCreateEvent(jibun, officeTelTransactionClientDTO, eventDateTime);
    }
}
