package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.service.PostQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostQueryServiceImplTest {

    @Autowired
    private PostQueryService postQueryService;

    @Test
    void testGetPostByClosestAddress() {
        // given
        GetPostsRequest request = new GetPostsRequest("127.039136433366", "37.4682787075426", 1000);

        // when
        postQueryService.getPostByClosestAddress(request);

        // whenrt
//        fail("This test has yet to be implemented");
    }
}