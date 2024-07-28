package com.view.zib.domain.log.service.impl;

import com.view.zib.domain.log.entity.LogViewCount;
import com.view.zib.domain.log.repository.LogViewCountRepository;
import com.view.zib.domain.log.service.LogViewCountQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LogViewCountQueryServiceImpl implements LogViewCountQueryService {

    private final LogViewCountRepository logViewCountRepository;

    @Override
    public Optional<LogViewCount> findByPostIdAndIpAddressAndCreatedAt(Long postId, String ipAddress, LocalDate today) {
        return logViewCountRepository.findByPostIdAndIpAddressAndCreatedAtBetween(postId, ipAddress, today.atStartOfDay(), today.atTime(LocalTime.MAX));
    }
}
