package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.entity.JibunDetail;
import com.view.zib.domain.address.repository.JibunDetailRepository;
import com.view.zib.domain.address.service.JibunDetailCreateService;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class JibunDetailCreateServiceImpl implements JibunDetailCreateService {

    private final JibunDetailRepository jibunDetailRepository;

    @Override
    public void create(Jibun jibun, VWorldResponseDto vWorldResponseDto) {
        VWorldResponseDto.Item item = vWorldResponseDto.response().body().items().item();
        JibunDetail jibunDetail = JibunDetail.of(jibun, item);
        jibunDetailRepository.save(jibunDetail);
    }

    @Override
    public void create(JibunDetail jibunDetail) {
        jibunDetailRepository.save(jibunDetail);
    }
}
