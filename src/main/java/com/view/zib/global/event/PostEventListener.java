package com.view.zib.global.event;

import com.view.zib.domain.address.service.AddressCommandService;
import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.post.facade.PostCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostEventListener implements ApplicationListener<PostViewEvent> {

    private final PostCommandFacade postCommandFacade;
    private final AddressCommandService addressCommandService;

    @Override
    public void onApplicationEvent(PostViewEvent event) {
        log.info("Received spring post event - postId: [{}], eventType: [{}]", event.getPostId(), event.getEventType());

        if (event.getEventType() == PostEventType.POST_VIEWED) {
            String ipAddress = (String) event.getMessage();
            postCommandFacade.increaseViewCount(event.getPostId(), ipAddress);

        } else if (event.getEventType() == PostEventType.POST_COORDINATE_UPDATED) {
            Coordinate coordinate = (Coordinate) event.getMessage();
            addressCommandService.updateCoordinate(event.getPostId(), coordinate);
        }
    }
}
