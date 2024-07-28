package com.view.zib.domain.log.repository;

import com.view.zib.domain.log.entity.LogViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LogViewCountRepository extends JpaRepository<LogViewCount, Long> {
    Optional<LogViewCount> findByPostIdAndIpAddressAndCreatedAtBetween(Long postId, String ipAddress, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
