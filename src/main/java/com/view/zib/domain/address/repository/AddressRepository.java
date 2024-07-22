package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByPostId(Long postId);
}
