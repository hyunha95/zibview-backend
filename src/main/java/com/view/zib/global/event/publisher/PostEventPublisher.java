package com.view.zib.global.event.publisher;

import com.view.zib.global.event.PostEvent;
import com.view.zib.global.event.PostEventType;
import com.view.zib.global.event.listener.PostEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * @see PostEventListener
     * @param postId
     * @param eventType
     * @param eventDateTime
     */
    public void publishEvent(long postId, Object message, PostEventType eventType, LocalDateTime eventDateTime) {
        log.info("Publishing post event. postId: [{}], eventType: [{}]", postId, eventType);
        PostEvent postEvent = new PostEvent(this, postId, message, eventType, eventDateTime);
        applicationEventPublisher.publishEvent(postEvent);
    }
}
