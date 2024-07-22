package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.custom.PostCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

    @EntityGraph(attributePaths = {"buildingInfo"})
    Page<Post> findAll(Pageable pageable);
}
