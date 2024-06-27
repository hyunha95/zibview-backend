package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.storage.service.StorageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public record GetPostResponse(
        double latitude,
        double longitude,
        String buildingName,
        String address,
        List<SubPostDto> subPosts
) {

    public record SubPostDto(
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
            CommentDto latestComment
    ) {
    }

    public record CommentDto(
            Long userId,
            String userName,
            String profileImageUrl,
            String comment,
            LocalDateTime createdAt
    ) {
        // Comment Entity를 CommentDto로 변환
        public static CommentDto from(Comment comment) {
            return new CommentDto(
                    comment.getUser().getId(),
                    comment.getUser().getName(),
                    comment.getUser().getPictureUrl(),
                    comment.getComment(),
                    comment.getCreatedAt()
            );
        }
    }

    public boolean hasCoordinate() {
        return latitude != 0.0 && longitude != 0.0;
    }

    public static GetPostResponse of(Post post, List<SubPost> subPosts, List<SubPostLike> subPostLikes, StorageService storageService) {
        RoadNameAddress roadNameAddress = post.getRoadNameAddress();
        List<SubPostDto> subPostDtos = subPosts.stream()
                .map(mapToSubPostDtos(subPostLikes, storageService))
                .toList();

        return new GetPostResponse(
                Optional.ofNullable(roadNameAddress.getLatitude()).orElse(0.0),
                Optional.ofNullable(roadNameAddress.getLongitude()).orElse(0.0),
                roadNameAddress.getAdditionalInfo().getBuildingName(),
                roadNameAddress.getFullAddress(),
                subPostDtos
        );
    }

    // SubPost Entity를 SubPostDto로 변환
    private static Function<SubPost, SubPostDto> mapToSubPostDtos(List<SubPostLike> subPostLikes, StorageService storageService) {
        return subPost -> {
            List<String> imageUrls = subPost.getImages().stream()
                    .map(storageService::generateImageUrl)
                    .toList();

            CommentDto latestComment = subPost.getLatestComment()
                    .map(CommentDto::from)
                    .orElse(null);

            return new SubPostDto(
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

    public GetPostResponse setNewCoordinate(Coordinate coordinate) {
        return new GetPostResponse(coordinate.latitude(), coordinate.longitude(), buildingName, address, subPosts);
    }
}
