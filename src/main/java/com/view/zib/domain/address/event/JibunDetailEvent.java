package com.view.zib.domain.address.event;

import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JibunDetailEvent {

    private final Long jibunId;
    private final VWorldResponseDto vWorldResponseDto;
    private final LocalDateTime eventDateTime;

    private JibunDetailEvent(Long jibunId, VWorldResponseDto vWorldResponseDto, LocalDateTime eventDateTime) {
        this.jibunId = jibunId;
        this.vWorldResponseDto = vWorldResponseDto;
        this.eventDateTime = eventDateTime;
    }

    public static JibunDetailEvent of(Long jibunId, VWorldResponseDto vWorldResponseDto, LocalDateTime now) {
        return new JibunDetailEvent(jibunId, vWorldResponseDto, now);
    }
}
