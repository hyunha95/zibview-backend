package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import com.view.zib.domain.storage.service.StorageService;

import java.util.List;
import java.util.Map;

public record GetPostsResponse(
        Long postId,
        String address,
        String buildingName,
        List<String> imageUrl,
        List<String> imageUrn,
        int likeCount,
        int commentCount
) {
    public GetPostsResponse(LatestResidentialPost response, Map<Long, List<Image>> imagesByPost, StorageService storageService) {
        this(
                response.getPostId(),
                String.format("%s %s %s %s-%s",
                        response.getSidoName(),
                        response.getSigunguName(),
                        response.getRoadName(),
                        response.getBuildingNum(),
                        response.getBuildingSubNum()
                ),
                response.getBuildingName(),
                storageService.generateImageUrls(imagesByPost.get(response.getPostId())),
                storageService.generateImageUrns(imagesByPost.get(response.getPostId())),
                response.getLikeCount(),
                response.getCommentCount()
        );
    }
}
