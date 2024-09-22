package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.JibunDetail;
import com.view.zib.domain.address.repository.jpa.JibunDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JibunDetailRepository {

    private final JibunDetailJpaRepository jibunDetailJpaRepository;

    public void save(JibunDetail jibunDetail) {
        jibunDetailJpaRepository.save(jibunDetail);
    }
}
