package com.view.zib.domain.post.facade;

import com.view.zib.TestSecurityUtils;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.SubPostLikeRepository;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.repository.UserRepository;
import com.view.zib.mock.TestEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(value = "/sql/post-command-facade-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class PostCommandFacadeIntgTest {

    @Autowired
    SubPostRepository subPostRepository;

    @Autowired
    SubPostLikeRepository subPostLikeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    PostCommandFacade postCommandFacade;

    @BeforeEach
    void beforeEach() {
        subPostLikeRepository.deleteAll();
        subPostRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 백명의_유저가_동시에_좋아요를_요청하면_subPost의_likeCount가_백이_증가한다() throws InterruptedException {
        // given
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        SubPost subPost = subPostRepository.save(SubPost.builder()

                .title("title")
                .description("description")
                .deleted(false)
                .build());

        // when
        IntStream.range(0, 100).forEach(i -> {
            executorService.execute(() -> {
                try {
                    User user = TestEntityFactory.createUser(String.format("testuser%s@test.com", i));
                    userRepository.save(user);
                    TestSecurityUtils.setJwtAuthentication(user.getEmail());
                    postCommandFacade.likeSubPost(subPost.getId());
                } finally {
                    countDownLatch.countDown();
                }
            });
        });

        countDownLatch.await();

        // then
        SubPost foundSubPost = subPostRepository.findById(subPost.getId()).get();
        assertThat(foundSubPost.getLikeCount()).isEqualTo(100);
        assertThat(foundSubPost.getDislikeCount()).isZero();
    }

    @Test
    void 백명의_유저가_동시에_좋아요_50번_싫어요_50번을_요청하면_subPost의_likeCount가_50_dislikeCount가_50이_증가한다() throws InterruptedException {
        // given
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        SubPost subPost = subPostRepository.save(SubPost.builder()
                .title("title")
                .description("description")
                .deleted(false)
                .build());

        // when
        IntStream.range(0, 100).forEach(i -> {
            executorService.execute(() -> {
                try {
                    User user = TestEntityFactory.createUser(String.format("testuser%s@test.com", i));
                    userRepository.save(user);
                    TestSecurityUtils.setJwtAuthentication(user.getEmail());
                    if (i % 2 == 0) {
                        postCommandFacade.likeSubPost(subPost.getId());
                    } else {
                        postCommandFacade.dislikeSubPost(subPost.getId());
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        });

        countDownLatch.await();

        // then
        SubPost foundSubPost = subPostRepository.findById(subPost.getId()).get();
        assertThat(foundSubPost.getLikeCount()).isEqualTo(50);
        assertThat(foundSubPost.getDislikeCount()).isEqualTo(50);
    }
}