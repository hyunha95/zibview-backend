package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.address.address = :address AND p.address.addressType = :addressType")
    Optional<Post> findByAddressAndAddressType(@Param("address") String address, @Param("addressType") String addressType);

    @Query("SELECT p FROM Post p WHERE p.address.id IN :addressIds")
    List<Post> findByAddressIdIn(@Param("addressIds") List<Integer> addressIds);
}
