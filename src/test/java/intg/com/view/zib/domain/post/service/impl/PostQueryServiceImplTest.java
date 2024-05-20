package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.service.PostQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostQueryServiceImplTest {

    @Autowired
    private PostQueryService postQueryService;

    @Test
    void testGetPostByClosestAddress() {
        // given
        GetPostsRequest request = new GetPostsRequest("127.039136433366", "37.4682787075426", 10000, List.of(3L));

        // when
        List<GetPostsResponse> postByClosestAddress = postQueryService.getPostByClosestAddress(request);

        // when
        Assertions.assertThat(postByClosestAddress.size()).isEqualTo(2L);
    }
}