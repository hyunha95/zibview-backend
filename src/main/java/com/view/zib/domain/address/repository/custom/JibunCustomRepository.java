package com.view.zib.domain.address.repository.custom;

import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;

import java.math.BigDecimal;
import java.util.List;

public interface JibunCustomRepository {

    List<JibunSearchResultDTO> findAddressesInUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan);
}
