package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.jpa.JibunJpaRepository;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JibunRepository {

    private final JibunJpaRepository jibunJpaRepository;
    
    public Jibun getById(Long jibunId) {
        return jibunJpaRepository.findById(jibunId)
                .orElseThrow(() -> new ResourceNotFoundException("Jibun", jibunId));
    }
}
