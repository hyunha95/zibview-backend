package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.post.id = :postId")
    Optional<Address> findByPostId(@Param("postId") Long postId);
}
