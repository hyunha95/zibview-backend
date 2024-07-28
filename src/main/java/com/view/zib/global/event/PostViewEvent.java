package com.view.zib.global.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class PostViewEvent extends ApplicationEvent {
    private final Long postId;
    private final Object message;
    private final PostEventType eventType;
    private final LocalDateTime eventDateTime;

    public PostViewEvent(Object source, Long postId, Object message, PostEventType eventType, LocalDateTime eventDateTime) {
        super(source);

        // 로그인하지 않은 경우 postId는 null로 들어올 수 있다.
        Objects.requireNonNull(message);
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(eventDateTime);

        this.postId = postId;
        this.message = message;
        this.eventType = eventType;
        this.eventDateTime = eventDateTime;
    }
}
