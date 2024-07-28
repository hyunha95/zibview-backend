package com.view.zib.domain.post.facade;

import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.api.kako.service.KakaoService;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import com.view.zib.domain.elasticsearch.service.PostElasticSearchService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.domain.post.service.SubPostLikeQueryService;
import com.view.zib.domain.post.service.SubPostQueryService;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.global.event.PostEventPublisher;
import com.view.zib.global.event.PostEventType;
import com.view.zib.global.utils.IpAddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostQueryFacade {

    private final PostEventPublisher postEventPublisher;

    private final PostQueryService postQueryService;
    private final StorageService storageService;
    private final KakaoService kakaoService;
    private final ImageQueryService imageQueryService;
    private final SubPostQueryService subPostQueryService;
    private final SubPostLikeQueryService subPostLikeQueryService;
    private final AuthService authService;
    private final PostElasticSearchService postElasticSearchService;

    private final HttpServletRequest httpServletRequest;

    public Slice<GetPostsResponse> getLatestPosts(Pageable pageable) {
        Slice<LatestPost> latestPosts = postQueryService.getLatestPosts(pageable);

        // make collectionUtils
        List<Long> postIds = latestPosts.getContent().stream()
                .map(LatestPost::getPostId)
                .toList();

        Map<Long, List<Image>> imagesByPostId = imageQueryService.findByPostIdInOrderByCreatedAtDesc(postIds).stream()
                .collect(Collectors.groupingBy(image -> image.getPost().getId()));

        return latestPosts.map(response -> new GetPostsResponse(response, imagesByPostId, storageService));
    }

    public GetPostResponse getPostDetails(Long postId) {
7        // 로그인 여부 체크
        Long userId = authService.isLoggedIn() ? authService.getCurrentUser().getId() : null;

        Post post = postQueryService.getById(postId);
        List<SubPost> subPosts = subPostQueryService.findByPostIdAndDeletedFalseOrderByIdDesc(postId);
        List<Long> subPostIds = subPosts.stream().map(SubPost::getId).toList();

        // 로그인하지 않은 사용자에게는 좋아요 체크 여부 정보를 제공하지 않음
        List<SubPostLike> subPostLikes = subPostLikeQueryService.findBySubPostIdInAndUserId(subPostIds, userId);

        GetPostResponse postDetails = GetPostResponse.of(post, subPosts, subPostLikes, storageService);

        // 좌표가 없는 데이터라면
        if (!postDetails.hasCoordinate()) {
            // 카카오 API를 통해 주소로 좌표 검색
            Coordinate coordinate = kakaoService.searchCoordinateByRoadAddress(postDetails.roadNameAddress());
            postDetails = postDetails.setNewCoordinate(coordinate);
            postEventPublisher.publishEvent(post.getId(), coordinate, PostEventType.POST_COORDINATE_UPDATED, LocalDateTime.now());
        }

        postEventPublisher.publishEvent(post.getId(), IpAddressUtil.getClientIp(httpServletRequest), PostEventType.POST_VIEWED, LocalDateTime.now());
        return postDetails;
    }

    public List<PostSearchAsYouType> searchAsYouTypeAddressAndBuildingName(String query) {
        return postElasticSearchService.searchAsYouTypeAddressAndBuildingName(query);
    }
}
