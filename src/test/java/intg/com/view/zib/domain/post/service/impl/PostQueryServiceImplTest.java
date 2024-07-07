package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.domain.storage.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

@SpringBootTest
class PostQueryServiceImplTest {

    @Autowired
    private PostQueryService postQueryService;

    @Autowired
    private StorageService storageService;

    @Test
    void testGetLatestPosts() {
        // given
//        GetPostsRequest request = new GetPostsRequest("127.039136433366", "37.4682787075426", 10000, List.of(3L));

        // when
        Slice<LatestPost> latestPosts = postQueryService.getLatestPosts(Page.empty().getPageable());

        // when
//        Assertions.assertThat(postByClosestAddress.size()).isEqualTo(2L);
    }
}