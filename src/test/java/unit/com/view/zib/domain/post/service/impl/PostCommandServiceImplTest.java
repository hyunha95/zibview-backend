package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.building.enums.BuildingType;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.service.PostCommandService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.mock.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class PostCommandServiceImplTest {

    private PostCommandService postCommandService;

    private FakeImageRepository fakeImageRepository = new FakeImageRepository();

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        fakeUserRepository.save(User.builder().build());

        postCommandService = PostCommandServiceImpl.builder()
                .userService(new FakeUserService(fakeUserRepository))
                .postRepository(new FakePostRepository())
                .userAddressRepository(new FakeUserAddressRepository())
                .authService(new FakeAuthService())
                .addressRepository(new FakeAddressRepository())
                .imageRepository(fakeImageRepository)
                .build();
    }

    @Test
    void testSaveShouldSuccess() {
        // given
        fakeImageRepository.save(Image.builder()
                .uuid("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .mimeType("image/jpeg")
                .fileSize(10000)
                .storedFilename("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg")
                .originalFilename("랜덤 이미지 파일.jpg")
                .extension("jpg")
                .path("20240516")
                .dateTimeOriginal("2012:08:09 06:55:30")
                .latitudeGPS("19.5112")
                .longitudeGPS("63.5314")
                .representative(true)
                .build());

        PostRequest.Save postRequestSave = PostRequest.Save.builder()
            .title("Your Title")
            .description("Your Description")
            .imageUuids(List.of("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
            .buildingType(BuildingType.APARTMENT)
            .address(new PostRequest.Address("서울 서초구 강남대로 27", "27, Gangnam-daero, Seocho-gu, Seoul, Korea", "R", "N", "",
                                                "", "", "", "1165010200", "양재동", "", "", "양재동", "Yangjae-dong", "Yangjae-dong",
                                                "1165010200102320000000001", "AT센터", "", "", "서울 서초구 양재동 232",
                                                "232, Yangjae-dong, Seocho-gu, Seoul, Korea", "N", "", "", "", "", "서초",
                                                "서울 서초구 강남대로 27", "27, Gangnam-daero, Seocho-gu, Seoul, Korea", "강남대로",
                                                "2102001", "Gangnam-daero", "서울", "Seoul", "서초구", "11650", "Seocho-gu", "K",
                                                "R", "0677"))
            .residencyStartDate(LocalDate.parse("2022-01-01"))
            .residencyEndDate(LocalDate.parse("2022-12-31"))
            .build();

        // when
        postCommandService.save(postRequestSave, null);

        // then
        Image image = fakeImageRepository.findById(1L).get();
        Assertions.assertThat(image.getUuid()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}