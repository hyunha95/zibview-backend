package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.storage.service.StorageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GetPostsResponse(
        Long postId,
        String sggName,
        String emdName,
        String buildingName,
        String sggBuildingName,
        Long likeCount,
        Long commentCount,
        Long viewCount,
        List<String> imagePaths,
        List<String> imageStoredFilename,
        LocalDateTime updatedAt
) {
    public GetPostsResponse(LatestPost latestPost, Map<Long, List<Image>> imagesByPost, StorageService storageService) {
        this(
                latestPost.getPostId(),
                latestPost.getSggName(),
                latestPost.getEmdName(),
                latestPost.getBuildingName(),
                latestPost.getSggBuildingName(),
                latestPost.getLikeCount(),
                latestPost.getCommentCount(),
                latestPost.getViewCount(),
                storageService.generateImageUrls(imagesByPost.get(latestPost.getPostId())),
                storageService.generateImageUrns(imagesByPost.get(latestPost.getPostId())),
                latestPost.getUpdatedAt()
        );
    }
}
