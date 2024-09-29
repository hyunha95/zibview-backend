package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.repository.jpa.JibunJpaRepository;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JibunRepository {

    private final JibunJpaRepository jibunJpaRepository;
    
    public Jibun getById(Long jibunId) {
        return jibunJpaRepository.findById(jibunId)
                .orElseThrow(() -> new ResourceNotFoundException("Jibun", jibunId));
    }

    public List<JibunSearchResultDTO> findAddressesInUtmk(BigDecimal utmkX, BigDecimal utmkY, BigDecimal utmkXSpan, BigDecimal utmkYSpan) {
        return jibunJpaRepository.findAddressesInUtmk(utmkX, utmkY, utmkXSpan, utmkYSpan);
    }

    public List<Jibun> findByLegalDongCodeAndJibunMainAndJibunSub(String legalDongCode, String jibunMain, String jibunSub) {
        return jibunJpaRepository.findByLegalDongCodeAndJibunMainAndJibunSub(legalDongCode, jibunMain, jibunSub);
    }
}
