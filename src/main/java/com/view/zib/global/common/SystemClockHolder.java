package com.view.zib.global.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
