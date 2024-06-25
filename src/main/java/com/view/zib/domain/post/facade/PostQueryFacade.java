package com.view.zib.domain.post.facade;

import com.view.zib.domain.address.service.RoadNameAddressCommandService;
import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.api.kako.service.KakaoService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.domain.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostQueryFacade {

    private final RoadNameAddressCommandService roadNameAddressCommandService;

    private final PostQueryService postQueryService;
    private final StorageService storageService;
    private final KakaoService kakaoService;
    private final ImageQueryService imageQueryService;

    public Slice<GetPostsResponse> getLatestPosts(Pageable pageable) {
        Slice<LatestResidentialPost> latestPosts = postQueryService.getLatestPosts(pageable);

        // make collectionUtils
        List<Long> postIds = latestPosts.getContent().stream()
                .map(LatestResidentialPost::getPostId)
                .toList();

        Map<Long, List<Image>> imagesByPostId = imageQueryService.findByPostIdInOrderByCreatedAtDesc(postIds).stream()
                .collect(Collectors.groupingBy(image -> image.getPost().getId()));


        return postQueryService.getLatestPosts(pageable)
                .map(response -> new GetPostsResponse(response, imagesByPostId, storageService));
    }

    public GetPostResponse getPostDetails(Long postId) {
        GetPostResponse postDetails = postQueryService.getPostDetails(postId, storageService);

        // 좌표가 없는 데이터라면
        if (!postDetails.hasCoordinate()) {
            // 카카오 API를 통해 주소로 좌표 검색
            KakaoAddressResponse kakaoAddressResponse = kakaoService.searchAddress(postDetails.address());

            // 좌표 업데이트
            Post post = postQueryService.getById(postId);
            roadNameAddressCommandService.updateCoordinate(post, kakaoAddressResponse.getCoordinate());
            postDetails = postDetails.setNewCoordinate(kakaoAddressResponse.getCoordinate());
        }

        return postDetails;
    }
}
