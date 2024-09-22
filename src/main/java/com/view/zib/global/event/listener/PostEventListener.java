package com.view.zib.global.event.listener;

import com.view.zib.domain.post.facade.PostCommandFacade;
import com.view.zib.global.event.PostEvent;
import com.view.zib.global.event.PostEventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostEventListener implements ApplicationListener<PostEvent> {

    private final PostCommandFacade postCommandFacade;

    @Override
    public void onApplicationEvent(PostEvent event) {
        log.info("Received spring post event - postId: [{}], eventType: [{}]", event.getPostId(), event.getEventType());

        if (event.getEventType() == PostEventType.POST_VIEWED) {
            String ipAddress = (String) event.getMessage();
            postCommandFacade.increaseViewCount(event.getPostId(), ipAddress);
        }
    }
}
