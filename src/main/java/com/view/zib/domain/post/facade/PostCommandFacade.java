package com.view.zib.domain.post.facade;

import com.view.zib.domain.api.kako.client.KakaoAddressClient;
import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.request.SubPostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.service.*;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.exception.exceptions.ForbiddenException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Builder
@RequiredArgsConstructor
@Component
public class PostCommandFacade {

    private final PostQueryService postQueryService;
    private final SubPostQueryService subPostQueryService;
    private final ImageQueryService imageQueryService;
    private final SubPostLikeQueryService subPostLikeQueryService;

    private final PostCommandService postCommandService;
    private final SubPostCommandService subPostCommandService;
    private final SubPostLikeCommandService subPostLikeCommandService;
    private final AuthService authService;

    private final KakaoAddressClient kakaoAddressClient;

    /**
     * 외부 API 호출을 디비 트랜잭션에서 제거하기 위해 Facade 패턴을 사용한다.
     */
    public Long create(PostRequest.Save request) {
        log.info("kakao Address API Call with {}", request.getAddress().getAddress());
        KakaoAddressResponse kakaoAddressResponse = kakaoAddressClient.searchAddress(request.getAddress().getAddress(), "", 1, 10);

        return postCommandService.create(request, kakaoAddressResponse);
    }

    public Long createSubPost(SubPostRequest.Save request) {
        Post post = postQueryService.getById(request.getPostId());
        List<Image> images = imageQueryService.findByUuidIn(request.getImageUuids());
        images.forEach(image -> image.addEntity(post));

        User currentUser = authService.getCurrentUser();

        if (!currentUser.isMyImages(images)) {
            throw new ForbiddenException("내가 등록한 이미지만 등록할 수 있습니다.");
        }

        return subPostCommandService.create(request, post, images, currentUser);
    }

    @Transactional
    public void likeSubPost(Long subPostId) {
        User currentUser = authService.getCurrentUser();
        SubPost subPost = subPostQueryService.getByIdForUpdate(subPostId);

        subPostLikeQueryService.findBySubPostIdAndUserId(subPostId, currentUser.getId())
                .ifPresentOrElse(
                        likeSubPost(subPost),
                        createSubPostLike(subPost, currentUser, true)
                );
    }

    @Transactional
    public void dislikeSubPost(Long subPostId) {
        User currentUser = authService.getCurrentUser();
        SubPost subPost = subPostQueryService.getByIdForUpdate(subPostId);

        subPostLikeQueryService.findBySubPostIdAndUserId(subPostId, currentUser.getId())
                .ifPresentOrElse(
                        dislikeSubPost(subPost),
                        createSubPostLike(subPost, currentUser, false)
                );
    }

    @Transactional
    public void removeSubPostLike(Long subPostId) {
        User currentUser = authService.getCurrentUser();
        SubPost subPost = subPostQueryService.getByIdForUpdate(subPostId);

        subPostLikeQueryService.findBySubPostIdAndUserId(subPostId, currentUser.getId())
                .ifPresentOrElse(
                        dislikeSubPost(subPost),
                        () -> { throw new IllegalStateException("좋아요 상태가 아닙니다."); }
                );
    }

    private Consumer<SubPostLike> dislikeSubPost(SubPost subPost) {
        return subPostLike -> {
            if (!subPostLike.isLiked()) {
                throw new IllegalStateException("이미 좋아요 취소 상태입니다.");
            }
            subPost.decreaseLikeCount();
            subPost.increaseDislikeCount();
            subPostLike.dislike();
        };
    }

    private Consumer<SubPostLike> likeSubPost(SubPost subPost) {
        return subPostLike -> {
            if (subPostLike.isLiked()) {
                throw new IllegalStateException("이미 좋아요 상태입니다.");
            }
            subPost.increaseLikeCount();
            subPost.decreaseDislikeCount();
            subPostLike.like();
        };
    }

    private Runnable createSubPostLike(SubPost subPost, User currentUser, boolean liked) {
        return () -> {
            subPostLikeQueryService.create(subPost, currentUser, liked);
            if (liked) {
                subPost.increaseLikeCount();
            } else {
                subPost.increaseDislikeCount();
            }
        };
    }
}
