package com.view.zib.global.event;

import lombok.Getter;

@Getter
public class GenericEvent<ID, T> {

    private final ID id;
    private final T what;
    private final EventType eventType;

    public GenericEvent(ID id, T what, EventType eventType) {
        this.id = id;
        this.what = what;
        this.eventType = eventType;
    }
}
