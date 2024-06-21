package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.post.entity.ContractInfo;
import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import com.view.zib.domain.storage.service.StorageService;

import java.time.LocalDateTime;

public record GetPostsResponse(
        Long postId,
        String address,
        String buildingName,
        String imageUrl,
        String imageUrn,
        int likeCount,
        int commentCount
) {
    public GetPostsResponse(LatestResidentialPost response, StorageService storageService) {
        this(
                response.postId(),
                String.format("%s %s %s %s-%s",
                        response.sidoName(),
                        response.sigunguName(),
                        response.roadName(),
                        response.buildingNum(),
                        response.buildingSubNum()
                ),
                response.buildingName(),
                storageService.generateImageUrl(response.imagePath(), response.imageStoredFilename()),
                storageService.generateImageUrn(response.imagePath(), response.imageStoredFilename()),
                response.likeCount(),
                response.commentCount()
        );
    }
}
