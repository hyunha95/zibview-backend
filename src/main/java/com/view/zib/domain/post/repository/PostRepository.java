package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.custom.PostCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

    @EntityGraph(attributePaths = {"buildingInfo"})
    Page<Post> findAll(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.address.address = :address AND p.address.addressType = :addressType")
    Optional<Post> findByAddressAndAddressType(@Param("address") String address, @Param("addressType") String addressType);

}
