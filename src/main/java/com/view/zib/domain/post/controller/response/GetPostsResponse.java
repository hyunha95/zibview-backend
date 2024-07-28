package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.storage.service.StorageService;

import java.util.List;
import java.util.Map;

public record GetPostsResponse(
        Long postId,
        String roadNameAddress,
        String jibunAddress,
        String buildingName,
        String sigunguBuildingName,
        List<String> imageUrl,
        List<String> imageUrn,
        long likeCount,
        long commentCount,
        long viewCount
) {
    public GetPostsResponse(LatestPost response, Map<Long, List<Image>> imagesByPost, StorageService storageService) {
        this(
                response.getPostId(),
                response.getRoadNameAddress(),
                response.getJibunAddress(),
                response.getBuildingName(),
                response.getSigunguBuildingName(),
                storageService.generateImageUrls(imagesByPost.get(response.getPostId())),
                storageService.generateImageUrns(imagesByPost.get(response.getPostId())),
                response.getLikeCount(),
                response.getCommentCount(),
                response.getViewCount()
        );
    }
}
