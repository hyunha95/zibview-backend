package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.repository.JibunDetailRepository;
import com.view.zib.domain.address.service.JibunDetailQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JibunDetailQueryServiceImpl implements JibunDetailQueryService {

    private final JibunDetailRepository jibunDetailRepository;


}
