package com.view.zib.domain.building.repository;

import com.view.zib.domain.building.entity.BuildingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingInfo, Long> {

    Page<BuildingInfo> findByLatitudeIsNullOrLongitudeIsNull(Pageable pageable);
}
