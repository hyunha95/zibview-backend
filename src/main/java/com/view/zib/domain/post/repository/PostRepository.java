package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.custom.PostCustomRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

    @EntityGraph(attributePaths = {"buildingInfo"})
    Page<Post> findAll(Pageable pageable);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findByIdForUpdate(Long postId);
}
