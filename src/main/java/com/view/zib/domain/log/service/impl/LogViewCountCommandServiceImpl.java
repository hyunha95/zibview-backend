package com.view.zib.domain.log.service.impl;

import com.view.zib.domain.log.entity.LogViewCount;
import com.view.zib.domain.log.repository.LogViewCountRepository;
import com.view.zib.domain.log.service.LogViewCountCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogViewCountCommandServiceImpl implements LogViewCountCommandService {

    private final LogViewCountRepository logViewCountRepository;

    @Override
    public void create(LogViewCount logViewCount) {
        logViewCountRepository.save(logViewCount);
    }
}
