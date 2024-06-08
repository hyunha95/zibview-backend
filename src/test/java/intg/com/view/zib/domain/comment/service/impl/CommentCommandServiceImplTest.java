package com.view.zib.domain.comment.service.impl;

import com.view.zib.domain.comment.controller.request.LikeCommentRequest;
import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.comment.repository.CommentLikeRepository;
import com.view.zib.domain.comment.repository.CommentRepository;
import com.view.zib.domain.comment.service.CommentCommandService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ActiveProfiles("test")
@SpringBootTest
class CommentCommandServiceImplTest {

    @Autowired
    private CommentCommandService commentCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @BeforeEach
    void beforeEach() {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    User user = User.builder()
                            .id((long) i)
                            .email("user" + i + "@test.com")
                            .password("password")
                            .name("user" + i)
                            .build();
                    userRepository.save(user);
                });

        Comment comment = Comment.builder()
                .id(1L)
                .comment("comment")
                .deleted(false)
                .build();

        commentRepository.save(comment);
    }

    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
        commentLikeRepository.deleteAll();
    }

    @Test
    void concurrentLikeRequestShouldSuccess() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        IntStream.rangeClosed(1, threadCount)
                .forEach(i -> {
                    executorService.execute(() -> {
                        try {
                            commentCommandService.likeComment(new LikeCommentRequest(1L));
                        } finally {
                            latch.countDown();
                        }
                    });
                });

        // then
        latch.await();
        Comment comment = commentRepository.findById(1L).orElseThrow();
        Assertions.assertThat(threadCount).isEqualTo(comment.getLikeCount().intValue());
    }

}