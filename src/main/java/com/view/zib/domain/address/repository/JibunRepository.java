package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.JibunMultipleConditionDTO;
import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.address.repository.jpa.JibunJpaRepository;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class JibunRepository {

    private final JibunJpaRepository jibunJpaRepository;
    
    public Jibun getById(Long jibunId) {
        return jibunJpaRepository.findById(jibunId)
                .orElseThrow(() -> new ResourceNotFoundException("Jibun", jibunId));
    }

    public Jibun getJibunByManagementNo(String managementNo) {
        return jibunJpaRepository.findJByManagementNoAndRepresentativeIsTrue(managementNo)
                .orElseThrow(() -> new ResourceNotFoundException("Jibun", managementNo));
    }

    public List<JibunSearchResultDTO> findAddressesInUtmkAndNotInJibunIds(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY, Set<Long> jibunIds) {
        return jibunJpaRepository.findAddressesInUtmkAndNotInJibunIds(minX, minY, maxX, maxY, jibunIds);
    }

    public List<Jibun> findByLegalDongCodeAndJibunMainAndJibunSub(String legalDongCode, String jibunMain, String jibunSub) {
        return jibunJpaRepository.findByLegalDongCodeAndJibunMainAndJibunSub(legalDongCode, jibunMain, jibunSub);
    }

    public List<Jibun> findByMultipleLegalDongCodeAndJibunNumber(List<JibunMultipleConditionDTO> conditions) {
        return jibunJpaRepository.findByMultipleLegalDongCodeAndJibunNumber(conditions);
    }

    public List<Jibun> findByJibunIdIn(List<Long> jibunIds) {
        return jibunJpaRepository.findByJibunIdIn(jibunIds);
    }

    public List<JibunSearchResultDTO> findAddressInUtmk(BigDecimal minX, BigDecimal minY, BigDecimal maxX, BigDecimal maxY) {
        return jibunJpaRepository.findAddressInUtmk(minX, minY, maxX, maxY);
    }
}
