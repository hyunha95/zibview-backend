package com.view.zib.domain.user.repository;

import com.view.zib.domain.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByUser_Id(Long userId);
}
