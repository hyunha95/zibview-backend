package com.view.zib.domain.post.facade;

import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.api.kako.service.KakaoAddressClient;
import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.response.PostResponse;
import com.view.zib.domain.post.service.PostCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostFacade {

    private final PostCommandService postCommandService;
    private final KakaoAddressClient kakaoAddressClient;

    /**
     * 외부 API 호출을 디비 트랜잭션에서 제거하기 위해 Facade 패턴을 사용한다.
     */
    public PostResponse.Save save(PostRequest.Save request) {
        log.info("kakao Address API Call with {}", request.getAddress().getAddress());
        KakaoAddressResponse kakaoAddressResponse = kakaoAddressClient.searchAddress(request.getAddress().getAddress(), "", 1, 10)
                .orElse(new KakaoAddressResponse());

        Long postId = postCommandService.save(request, kakaoAddressResponse.getDocuments().getFirst());
        return new PostResponse.Save(postId);
    }

    public Long createComment(CreateCommentRequest request) {
        return null;
    }
}
