package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.service.JibunDetailCreateService;
import com.view.zib.domain.address.service.JibunQueryService;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JibunDetailCommandFacade {

    private final JibunQueryService jibunQueryService;

    private final JibunDetailCreateService jibunDetailCreateService;

    public void create(Long jibunId, VWorldResponseDto vWorldResponseDto) {
        Jibun jibun = jibunQueryService.getById(jibunId);
        jibunDetailCreateService.create(jibun, vWorldResponseDto);
    }
}
