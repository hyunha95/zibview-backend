package com.view.zib.domain.address.repository.jpa;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.custom.JibunCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JibunJpaRepository extends JpaRepository<Jibun, Long>, JibunCustomRepository {

    @Query("SELECT j FROM Jibun j WHERE j.legalDongCode = :legalDongCode AND j.jibunMain = :jibunMain AND j.jibunSub = :jibunSub")
    List<Jibun> findByLegalDongCodeAndJibunMainAndJibunSub(String legalDongCode, String jibunMain, String jibunSub);

    @Query("SELECT j FROM Jibun j WHERE j.id IN :jibunIds")
    List<Jibun> findByJibunIdIn(List<Long> jibunIds);
}
