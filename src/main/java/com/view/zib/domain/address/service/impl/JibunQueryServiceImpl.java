package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.JibunRepository;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.service.JibunQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JibunQueryServiceImpl implements JibunQueryService {

    private final JibunRepository jibunRepository;

    @Override
    public Jibun getById(Long jibunId) {
        return jibunRepository.getById(jibunId);
    }

    @Override
    public List<JibunSearchResultDTO> findAddressesInUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan) {
        return jibunRepository.findAddressesInUtmk(utmkX, utmkY, utmkXSpan, utmkYSpan);
    }

    @Override
    public List<Jibun> findByLegalDongCodeAndJibunNumber(String legalDongCode, String jibunNumber) {
        String jibunMain = jibunNumber;
        String jibunSub = "0";
        if (jibunNumber.contains("-")) {
            String[] jibunNumbers = jibunNumber.split("-");
            jibunMain = jibunNumbers[0];
            jibunSub = jibunNumbers[1];
        }
        return jibunRepository.findByLegalDongCodeAndJibunMainAndJibunSub(legalDongCode, jibunMain, jibunSub);
    }
}
