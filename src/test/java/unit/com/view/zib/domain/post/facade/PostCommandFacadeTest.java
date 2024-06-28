package com.view.zib.domain.post.facade;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.service.impl.SubPostLikeQueryServiceImpl;
import com.view.zib.domain.post.service.impl.SubPostQueryServiceImpl;
import com.view.zib.domain.user.entity.User;
import com.view.zib.mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostCommandFacadeTest {

    PostCommandFacade postCommandFacade;
    FakeUserRepository fakeUserRepository = new FakeUserRepository();
    FakeSubPostRepository subPostRepository = new FakeSubPostRepository();
    FakeSubPostLikeRepository subPostLikeRepository = new FakeSubPostLikeRepository();

    SubPost subPost;

    @BeforeEach
    void beforeEach() {
        User user = TestEntityFactory.createUser();
         subPost = SubPost.builder()
                .user(user)
                .title("title")
                .description("description")
                .deleted(false)
                .build();

        fakeUserRepository.save(user);
        subPostRepository.save(subPost);

        postCommandFacade = PostCommandFacade.builder()
                .authService(new FakeAuthService())
                .subPostQueryService(new SubPostQueryServiceImpl(subPostRepository))
                .subPostLikeQueryService(new SubPostLikeQueryServiceImpl(subPostLikeRepository))
                .build();
    }

    @Test
    void 최초_subPost_좋아요_시_subPostLike_객체가_생성되고_subPost의_likeCount가_증가한다() {
        // given
        // when
        postCommandFacade.likeSubPost(1L);

        // then
        assertThat(subPost.getLikeCount()).isEqualTo(1);

        SubPostLike subPostLike = subPostLikeRepository.findById(1L).get();
        assertThat(subPostLike.getId()).isEqualTo(1L);
        assertThat(subPostLike.getSubPost()).isEqualTo(subPost);
    }

}