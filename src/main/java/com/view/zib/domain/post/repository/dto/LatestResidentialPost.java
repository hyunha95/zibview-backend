package com.view.zib.domain.post.repository.dto;

public record LatestResidentialPost (
        Long postId,
        String sidoName,
        String sigunguName,
        String roadName,
        String buildingNum,
        String buildingSubNum,
        String buildingName,
        int likeCount,
        int commentCount,
        String imagePath,
        String imageStoredFilename
) {
}
