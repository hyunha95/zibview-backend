package com.view.zib.global.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AddressEvent extends ApplicationEvent {

    private final String addressId;
    private final Object message;
    private final AddressEventType eventType;
    private final LocalDateTime eventDateTime;

    public AddressEvent(Object source, String addressId, Object message, AddressEventType eventType, LocalDateTime eventDateTime) {
        super(source);
        this.addressId = addressId;
        this.message = message;
        this.eventType = eventType;
        this.eventDateTime = eventDateTime;
    }
}
