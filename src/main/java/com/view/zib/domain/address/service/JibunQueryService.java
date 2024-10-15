package com.view.zib.domain.address.service;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface JibunQueryService {
    Jibun getById(Long jibunId);

    List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, List<Long> jibunIds);

    List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber);

    List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<ApartmentTransactionResponse.Item> items);

    List<Jibun> findByIdIn(List<Long> jibunIds);

    List<JibunSearchResultDTO> findAddressInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY);
}
