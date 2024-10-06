package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.entity.JibunDetail;
import com.view.zib.domain.address.service.JibunDetailCreateService;
import com.view.zib.domain.address.service.JibunQueryService;
import com.view.zib.domain.client.vworld.client.VWorldClient;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JibunDetailCommandFacade {

    private final JibunQueryService jibunQueryService;
    private final JibunDetailCreateService jibunDetailCreateService;
    private final VWorldClient vWorldClient;

    public void create(Long jibunId, VWorldResponseDto vWorldResponseDto) {
        Jibun jibun = jibunQueryService.getById(jibunId);
        jibunDetailCreateService.create(jibun, vWorldResponseDto);
    }

    @Transactional
    public void create(Jibun jibun) {
        // jibunDetail api 호출
        Optional<VWorldResponseDto> vWorldResponseDto = vWorldClient.searchJibunDetail(jibun.getLegalDongCode(), jibun.getJibunMain(), jibun.getJibunSub());
        if (vWorldResponseDto.isPresent()) {
            JibunDetail jibunDetail = JibunDetail.of(jibun, vWorldResponseDto.get().response().body().items().item());
            jibunDetailCreateService.create(jibunDetail);
            jibun.addEntity(jibunDetail);
        }
    }
}
