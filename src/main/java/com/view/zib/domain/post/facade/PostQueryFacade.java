package com.view.zib.domain.post.facade;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.address.event.publisher.JibunDetailEventPublisher;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.client.kako.domain.Coordinate;
import com.view.zib.domain.client.kako.service.KakaoClient;
import com.view.zib.domain.client.vworld.client.VWorldClient;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
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
import com.view.zib.domain.transaction.event.publisher.BuildingTransactionPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostQueryFacade {

    private final AuthService authService;
    private final PostQueryService postQueryService;
    private final StorageService storageService;
    private final ImageQueryService imageQueryService;
    private final SubPostQueryService subPostQueryService;
    private final SubPostLikeQueryService subPostLikeQueryService;
    private final PostElasticSearchService postElasticSearchService;

    private final JibunDetailEventPublisher jibunDetailEventPublisher;
    private final BuildingTransactionPublisher buildingTransactionPublisher;

    private final KakaoClient kakaoClient;
    private final VWorldClient vWorldClient;

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

    public GetPostResponse findByPostId(Long postId) {
        // 로그인 여부 체크
        Long userId = authService.isLoggedIn() ? authService.getCurrentUser().getId() : null;

        Post post = postQueryService.getById(postId);
        List<SubPost> subPosts = subPostQueryService.findByPostIdAndDeletedFalseOrderByIdDesc(postId);
        List<Long> subPostIds = subPosts.stream().map(SubPost::getId).toList();

        // 로그인하지 않은 사용자에게는 좋아요 체크 여부 정보를 제공하지 않음
        List<SubPostLike> subPostLikes = subPostLikeQueryService.findBySubPostIdInAndUserId(subPostIds, userId);

        GetPostResponse getPostResponse = GetPostResponse.of(post, subPosts, subPostLikes, storageService);

        // 좌표 검색
        getPostResponse = updateCoordinate(getPostResponse, post.getRoadNameAddress());
        // 주요 용도 검색
        getPostResponse = updateJibunDetail(getPostResponse, post.getRoadNameAddress());

        // 국토교통부_오피스텔 전월세 실거래가 자료
        updateOfficeTransaction(getPostResponse, post.getRoadNameAddress().getJibuns(), LocalDate.now());


        return getPostResponse;
    }

    private void updateOfficeTransaction(GetPostResponse getPostResponse, List<Jibun> jibuns, LocalDate now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        for (Jibun jibun : jibuns) {
            Optional<OfficeTelTransactionClientDTO> rtmsDataSvcOffiRent = vWorldClient.getRTMSDataSvcOffiRent(jibun.getLegalDongCode(), now.format(formatter));
            if (rtmsDataSvcOffiRent.isPresent()) {
                OfficeTelTransactionClientDTO officeTelTransactionClientDTO = rtmsDataSvcOffiRent.get();
                buildingTransactionPublisher.publishCreateEvent(jibun, officeTelTransactionClientDTO, LocalDateTime.now());
            }

        }

//        return getPostResponse.updateJibunDetail(rtmsDataSvcOffiRent.get());
    }

    private GetPostResponse updateJibunDetail(GetPostResponse getPostResponse, RoadNameAddress roadNameAddress) {
        List<VWorldResponseDto> responseDtos = new ArrayList<>();

        for (Jibun jibun : roadNameAddress.getJibuns()) {
            if (jibun.getJibunDetail() != null) {
                continue;
            }

            var ssgCd = jibun.getLegalDongCode().substring(0, 5);
            var hjdCd = jibun.getLegalDongCode().substring(5);
            Optional<VWorldResponseDto> vWorldResponseDto = vWorldClient.searchJibunDetail(ssgCd, hjdCd, jibun.getJibunMain(), jibun.getJibunSub());

            if (vWorldResponseDto.isEmpty()) {
                return getPostResponse;
            }

            VWorldResponseDto responseDto = vWorldResponseDto.get();
            responseDtos.add(responseDto);
            jibunDetailEventPublisher.publishCreateEvent(jibun.getId(), responseDto);
        }

        if (responseDtos.isEmpty()) {
            return getPostResponse;
        }

        return getPostResponse.updateJibunDetails(responseDtos);
    }

    private GetPostResponse updateCoordinate(GetPostResponse postDetails, RoadNameAddress roadNameAddress) {
        // 좌표가 없는 데이터라면
        if (!postDetails.hasCoordinate()) {
            // 카카오 API를 통해 주소로 좌표 검색
            Coordinate coordinate = kakaoClient.searchCoordinateByRoadAddress(postDetails.roadNameAddress(), roadNameAddress.getManagementNo());
            postDetails = postDetails.setNewCoordinate(coordinate);
        }
        return postDetails;
    }

    public List<PostSearchAsYouType> searchAsYouTypeAddressAndBuildingName(String query) {
        return postElasticSearchService.searchAsYouTypeAddressAndBuildingName(query);
    }
}
