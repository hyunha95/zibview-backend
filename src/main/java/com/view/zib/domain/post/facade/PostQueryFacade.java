package com.view.zib.domain.post.facade;

import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.api.kako.service.KakaoService;
import com.view.zib.domain.building.service.BuildingCommandService;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.domain.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostQueryFacade {

    private final BuildingCommandService buildingCommandService;

    private final PostQueryService postQueryService;
    private final StorageService storageService;
    private final KakaoService kakaoService;

    public Slice<GetPostsResponse> getLatestPosts(Pageable pageable) {
        return postQueryService.getLatestPosts(pageable)
                .map(response -> new GetPostsResponse(response, storageService));
    }

    public GetPostResponse getPostDetails(Long postId) {
        GetPostResponse postDetails = postQueryService.getPostDetails(postId, storageService);

        if (!postDetails.hasCoordinate()) {
            // 카카오 API를 통해 주소로 좌표 검색
            KakaoAddressResponse kakaoAddressResponse = kakaoService.searchAddress(postDetails.address());

            // 좌표 업데이트
            Post post = postQueryService.getById(postId);
            buildingCommandService.updateCoordinate(post, kakaoAddressResponse.getCoordinate());
            postDetails = postDetails.setNewCoordinate(kakaoAddressResponse.getCoordinate());
        }

        return postDetails;
    }
}
