package com.view.zib.domain.log.service.impl;

import com.view.zib.domain.log.repository.LoginLogRepository;
import com.view.zib.domain.log.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogRepository loginLogRepository;

    @Override
    public void log() {

    }
}
