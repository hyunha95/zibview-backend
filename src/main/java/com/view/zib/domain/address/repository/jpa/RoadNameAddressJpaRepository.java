package com.view.zib.domain.address.repository.jpa;

import com.view.zib.domain.address.entity.RoadNameAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadNameAddressJpaRepository extends JpaRepository<RoadNameAddress, String> {
}
