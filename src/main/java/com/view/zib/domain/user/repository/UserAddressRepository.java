package com.view.zib.domain.user.repository;

import com.view.zib.domain.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
