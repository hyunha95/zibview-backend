package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.service.JibunDetailQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JibunDetailQueryFacade {

    private final JibunDetailQueryService jibunDetailQueryService;



}
