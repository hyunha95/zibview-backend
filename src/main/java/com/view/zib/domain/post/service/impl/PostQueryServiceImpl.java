package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    @Transactional
    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Override
    public Slice<LatestPost> getLatestPosts(Pageable pageable) {
        return postRepository.findAllLatestPosts(pageable);
    }
}

/*
        String sql = """
            SELECT p.post_id
            FROM post p
                join building_info bi
                    on p.building_info_id = bi.building_info_id
            WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(bi.longitude, bi.latitude)) <= :distance
                AND p.post_id NOT IN (:postIds)
        """;

        List<Integer> postIds = jdbcClient.sql(sql)
                .param("longitude", request.longitude() != null ? request.longitude() : "127.028361548")
                .param("latitude", request.latitude() != null ? request.latitude() : "37.496486063")
                .param("distance", request.maxDistance() != null ? request.maxDistance() : 10000)
                .param("postIds", request.postIds() != null ? request.postIds() : 0)
                .query(Integer.class)
                .list();

 */