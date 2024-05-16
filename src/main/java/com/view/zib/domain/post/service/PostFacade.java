package com.view.zib.domain.post.service;

import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.api.kako.domain.KakaoMapRequest;
import com.view.zib.domain.api.kako.service.KakaoMapService;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostFacade {

    private final PostCommandService postCommandService;
    private final KakaoMapService kakaoMapService;

    /**
     * 외부 API 호출을 디비 트랜잭션에서 제거하기 위해 Facade 패턴을 사용한다.
     */
    public PostResponse.Save save(PostRequest.Save request) {

        Document document = kakaoMapService.searchAddress(new KakaoMapRequest(request.getAddress().getAddress()));
        return new PostResponse.Save(postCommandService.save(request, document));
    }

}
