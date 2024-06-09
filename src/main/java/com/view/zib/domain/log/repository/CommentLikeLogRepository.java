package com.view.zib.domain.log.repository;

import com.view.zib.domain.log.entity.CommentLikeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeLogRepository extends JpaRepository<CommentLikeLog, Long> {
}
