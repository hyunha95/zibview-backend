package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostQueryServiceImpl implements PostQueryService {

    private final JdbcClient jdbcClient;
    private final PostRepository postRepository;
    private final StorageService storageService;

    /**
     * 입력 받은 좌표를 기준으로 가장 가까운 주소를 가진 게시글을 조회한다.
     * @param request
     * @return
     */
    @Override
    public List<GetPostsResponse> getPostByClosestAddress(GetPostsRequest request) {
        String sql = """
            SELECT address_id
            FROM address  
            WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(longitude, latitude)) <= :distance
                AND post_id NOT IN (:postIds)
        """;

        List<Integer> addressIds = jdbcClient.sql(sql)
                .param("longitude", request.longitude())
                .param("latitude", request.latitude())
                .param("distance", request.maxDistance())
                .param("postIds", request.postIds() != null ? request.postIds() : 0)
                .query(Integer.class)
                .list();
        log.info("query: {}, params: {}, result: {}", sql, request, addressIds);

        List<Post> posts = postRepository.findByAddressIdIn(addressIds);

        return posts.stream()
                .map(post -> GetPostsResponse.from(post, storageService))
                .toList();
    }

    @Override
    public GetPostResponse getPostDetails(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        post.getSubPosts();
        Address address = post.getAddress();

        return null;
    }
}
