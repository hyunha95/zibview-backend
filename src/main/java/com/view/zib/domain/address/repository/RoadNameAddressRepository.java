package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.RoadNameAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoadNameAddressRepository extends JpaRepository<RoadNameAddress, Long> {

    @Query("SELECT rna FROM RoadNameAddress rna WHERE rna.post.id = :id")
    Optional<RoadNameAddress> findByPostId(Long id);
}
