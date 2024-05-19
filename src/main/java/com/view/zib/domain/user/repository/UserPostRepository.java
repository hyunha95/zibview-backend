package com.view.zib.domain.user.repository;

import com.view.zib.domain.user.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UserPost, Long> {
}
