package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.storage.service.StorageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public record SubPostDTO(
        long subPostId,
        LocalDateTime createdAt,
        String title,
        String description,
        List<String> imageUrls,
        boolean liked,
        long likeCount,
        boolean disliked,
        long dislikeCount,
        long commentCount,
        CommentDTO latestComment
) {

    // SubPost Entity를 SubPostDto로 변환
    public static Function<SubPost, SubPostDTO> mapToSubPostDtos(List<SubPostLike> subPostLikes, StorageService storageService) {
        return subPost -> {
            List<String> imageUrls = subPost.getImages().stream()
                    .map(storageService::generateImageUrl)
                    .toList();

            CommentDTO latestComment = subPost.getLatestComment()
                    .map(CommentDTO::from)
                    .orElse(null);

            return new SubPostDTO(
                    subPost.getId(),
                    subPost.getCreatedAt(),
                    subPost.getTitle(),
                    subPost.getDescription(),
                    imageUrls,
                    subPostLikes.stream().anyMatch(subPostLike -> Objects.equals(subPostLike.getSubPost(), subPost) && subPostLike.isLiked()),
                    subPost.getLikeCount(),
                    subPostLikes.stream().anyMatch(subPostLike -> Objects.equals(subPostLike.getSubPost(), subPost) && !subPostLike.isLiked()),
                    subPost.getDislikeCount(),
                    subPost.getCommentCount(),
                    latestComment
            );
        };
    }
}
