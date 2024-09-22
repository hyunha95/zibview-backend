package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.JibunRepository;
import com.view.zib.domain.address.service.JibunQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JibunQueryServiceImpl implements JibunQueryService {

    private final JibunRepository jibunRepository;

    @Override
    public Jibun getById(Long jibunId) {
        return jibunRepository.getById(jibunId);
    }
}
