package com.view.zib.domain.address.repository.custom;

import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;

import java.math.BigDecimal;
import java.util.List;

public interface JibunCustomRepository {

    List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, List<Long> jibunIds);
}
