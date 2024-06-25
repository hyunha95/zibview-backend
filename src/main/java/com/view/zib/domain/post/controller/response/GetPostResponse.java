package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.storage.service.StorageService;

import java.time.LocalDateTime;
import java.util.List;
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
            Long subPostId,
            Long userId,
            String userName,
            String profileImageUrl,
            LocalDateTime createdAt,
            String title,
            String description,
            List<String> imageUrls,
            Long commentCount,
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

    public static GetPostResponse from(Post post, StorageService storageService) {
        RoadNameAddress roadNameAddress = post.getRoadNameAddress();
        List<SubPostDto> subPostDtos = post.getSubPostsNotDeletedOrderByIdDesc().stream()
                .map(mapToSubPostDtos(storageService))
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
    private static Function<SubPost, SubPostDto> mapToSubPostDtos(StorageService storageService) {
        return subPost -> {
            List<String> imageUrls = subPost.getImages().stream()
                    .map(storageService::generateImageUrl)
                    .toList();

            CommentDto latestComment = subPost.getLatestComment()
                    .map(CommentDto::from)
                    .orElse(null);

            return new SubPostDto(
                    subPost.getId(),
                    subPost.getUser().getId(),
                    subPost.getUser().getName(),
                    subPost.getUser().getPictureUrl(),
                    subPost.getCreatedAt(),
                    subPost.getTitle(),
                    subPost.getDescription(),
                    imageUrls,
                    subPost.getCommentCount(),
                    latestComment
            );
        };
    }

    public GetPostResponse setNewCoordinate(Coordinate coordinate) {
        return new GetPostResponse(coordinate.latitude(), coordinate.longitude(), buildingName, address, subPosts);
    }
}
