package com.view.zib.mock;

import com.view.zib.global.common.ClockHolder;

import java.time.LocalDateTime;

public class TestClockHolder implements ClockHolder {

    private final LocalDateTime now;

    public TestClockHolder(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public LocalDateTime now() {
        return now;
    }
}
