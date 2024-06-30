package com.view.zib.domain.post.facade;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.service.impl.SubPostLikeQueryServiceImpl;
import com.view.zib.domain.post.service.impl.SubPostQueryServiceImpl;
import com.view.zib.domain.user.entity.User;
import com.view.zib.mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PostCommandFacadeTest {

    PostCommandFacade postCommandFacade;
    FakeAuthService authService = new FakeAuthService();
    FakeUserRepository fakeUserRepository = new FakeUserRepository();
    FakeSubPostRepository subPostRepository = new FakeSubPostRepository();
    FakeSubPostLikeRepository subPostLikeRepository = new FakeSubPostLikeRepository();

    SubPost subPost;

    @BeforeEach
    void beforeEach() {
        User user = TestEntityFactory.createUser();
        authService.setUser(user);

        subPost = SubPost.builder()
                .user(user)
                .title("title")
                .description("description")
                .deleted(false)
                .build();

        fakeUserRepository.save(user);
        subPostRepository.save(subPost);

        postCommandFacade = PostCommandFacade.builder()
                .authService(authService)
                .subPostQueryService(new SubPostQueryServiceImpl(subPostRepository))
                .subPostLikeQueryService(new SubPostLikeQueryServiceImpl(subPostLikeRepository))
                .build();
    }

    @Test
    void 최초_subPost_좋아요_시_subPostLike_객체가_생성되고_subPost의_likeCount가_증가한다() {
        // given
        // when
        postCommandFacade.likeSubPost(1L);
        SubPostLike subPostLike = subPostLikeRepository.findById(1L).get();

        // then
        assertThat(subPost.getLikeCount()).isEqualTo(1);
        assertThat(subPost.getDislikeCount()).isZero();

        assertThat(subPostLike.getId()).isEqualTo(1L);
        assertThat(subPostLike.getSubPost()).isEqualTo(subPost);
        assertThat(subPostLike.isLiked()).isTrue();
    }

    @Test
    void 종아요_상태에서_좋아요를_다시_요청하면_IllegalStateException을_발생시킨다() {
        // given
        postCommandFacade.likeSubPost(1L);

        // when
        // then
        assertThatThrownBy(() -> postCommandFacade.likeSubPost(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 좋아요 상태입니다.");
    }

    @Test
    void 싫어요_상태에서_좋아요를_요청하면_subPostLike_객체가_좋아요로_변경되고_subPost의_likeCount가_증가하고_dislikeCount가_감소한다() {
        // given
        postCommandFacade.dislikeSubPost(1L);

        // when
        postCommandFacade.likeSubPost(1L);

        // then
        assertThat(subPost.getLikeCount()).isEqualTo(1);
        assertThat(subPost.getDislikeCount()).isZero();

        SubPostLike subPostLike = subPostLikeRepository.findById(1L).get();
        assertThat(subPostLike.getId()).isEqualTo(1L);
        assertThat(subPostLike.getSubPost()).isEqualTo(subPost);
        assertThat(subPostLike.isLiked()).isTrue();
    }

    @Test
    void 최초_subPost_싫어요_시_subPostLike_객체가_생성되고_subPost의_dislikeCount가_증가한다() {
        // given
        // when
        postCommandFacade.dislikeSubPost(1L);
        SubPostLike subPostLike = subPostLikeRepository.findById(1L).get();

        // then
        assertThat(subPost.getDislikeCount()).isEqualTo(1);
        assertThat(subPost.getLikeCount()).isZero();

        assertThat(subPostLike.getId()).isEqualTo(1L);
        assertThat(subPostLike.getSubPost()).isEqualTo(subPost);
        assertThat(subPostLike.isLiked()).isFalse();
    }

    @Test
    void 백명의_유저가_순차적으로_좋아요를_요청하면_subPost의_likeCount가_백이_증가한다() {
        // given
        for (int i = 0; i < 100; i++) {
            User user = TestEntityFactory.createUser();
            fakeUserRepository.save(user);
            authService.setUser(user);
            postCommandFacade.likeSubPost(1L);
        }

        // when
        // then
        assertThat(subPost.getLikeCount()).isEqualTo(100);
    }

    @Test
    void 백명의_유저가_순차적으로_싫어요를_요청하면_subPost의_dislikeCount가_백이_증가한다() {
        // given
        for (int i = 0; i < 100; i++) {
            User user = TestEntityFactory.createUser();
            fakeUserRepository.save(user);
            authService.setUser(user);
            postCommandFacade.dislikeSubPost(1L);
        }

        // when
        // then
        assertThat(subPost.getDislikeCount()).isEqualTo(100);
    }

    @Test
    void 좋아요_상태에서_removeLike를_요청하면_subPost의_likeCount가_감소한다() {
        // given
        postCommandFacade.likeSubPost(1L);

        // when
        postCommandFacade.removeSubPostLike(1L);

        // then
        Optional<SubPostLike> subPostLike = subPostLikeRepository.findById(1L);

        assertThat(subPostLike).isEmpty();
        assertThat(subPost.getLikeCount()).isZero();
    }
}