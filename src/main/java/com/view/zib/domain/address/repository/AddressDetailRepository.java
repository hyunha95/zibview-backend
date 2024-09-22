package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDetailRepository extends JpaRepository<AddressDetail, String> {
}
