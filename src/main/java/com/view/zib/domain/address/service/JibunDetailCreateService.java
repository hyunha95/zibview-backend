package com.view.zib.domain.address.service;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;

public interface JibunDetailCreateService {
    void create(Jibun jibun, VWorldResponseDto vWorldResponseDto);
}
