package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.storage.service.StorageService;
import lombok.Builder;

import java.time.LocalDate;

public record GetPostsResponse(
        Long postId,
        String address,
        String buildingName,
        String imageUrl,
        int likeCount,
        int commentCount,

        double monthlyRent,
        LocalDate monthlyRentUpdatedAt,
        double annualRent,
        LocalDate annualRentUpdatedAt
) {

    @Builder
    public GetPostsResponse {
    }

    public static GetPostsResponse from(Post post, StorageService storageService) {
        return GetPostsResponse.builder()
                .postId(post.getId())
                .address(post.getAddress().getAddress())
                .buildingName(post.getAddress().getBuildingName())
                .imageUrl(storageService.generateImageUrl(post.getImage()))
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .monthlyRent(post.getLastestSubPost().getRent().getMonthlyRent())
                .monthlyRentUpdatedAt(post.getLastestSubPost().getRent().getMonthlyRentUpdatedAt().toLocalDate())
                .annualRent(post.getLastestSubPost().getRent().getAnnualRent())
                .annualRentUpdatedAt(post.getLastestSubPost().getRent().getAnnualRentUpdatedAt().toLocalDate())
                .build();
    }
}
