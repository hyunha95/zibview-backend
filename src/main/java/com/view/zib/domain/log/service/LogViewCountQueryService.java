package com.view.zib.domain.log.service;

import com.view.zib.domain.log.entity.LogViewCount;

import java.time.LocalDate;
import java.util.Optional;

public interface LogViewCountQueryService {

    Optional<LogViewCount> findByPostIdAndIpAddressAndCreatedAt(Long postId, String ipAddress, LocalDate today);
}
