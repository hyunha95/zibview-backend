package com.view.zib.global.event;

import java.time.LocalDateTime;

public abstract class AbstractEvent {
    protected final LocalDateTime eventDateTime;

    protected AbstractEvent(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
}
