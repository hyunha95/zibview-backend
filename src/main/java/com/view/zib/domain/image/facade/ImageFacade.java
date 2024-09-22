package com.view.zib.domain.image.facade;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.service.ImageCommandService;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.exception.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageFacade {

    private final StorageService storageService;
    private final ImageCommandService imageCommandService;
    private final ImageQueryService imageQueryService;
    private final AuthService authService;
    private final ClockHolder clockHolder;

    /**
     * 이미지 파일을 저장하는 기능을 트랜잭션에서 분리
     *
     * @param saveImageRequest
     */
    public void saveImage(SaveImageRequest saveImageRequest) {
        // 이미지 저장 어뷰징 방지
        preventImageAbusing(saveImageRequest);

        // 스토리지에 이미지 파일 저장
        Map<String, Storage> storageByUuid = saveImageRequest.getImages().stream()
                .map(image -> storageService.store(image.getImage(), image.getUuid(), clockHolder))
                .collect(toMap(Storage::uuid, Function.identity()));

        // 데이터베이스에 이미지 데이터 저장
        imageCommandService.saveImage(saveImageRequest, storageByUuid);
    }

    public void deleteImage(String imageUuid) {
        User currentUser = authService.getCurrentUser();
        Image image = imageQueryService.getByUuid(imageUuid);

        if (!currentUser.isMyImage(image)) {
            log.error("{} has no permission to delete image({})", authService.getEmail(), imageUuid);
            throw new ForbiddenException("이미지 삭제 권한이 없습니다.");
        }

        // 스토리지에서 이미지 파일 삭제
        storageService.deleteImage(image);

        // 이미지 데이터 삭제(삭제 컬럼 업데이트)
        imageCommandService.delete(image);
    }

    public Resource getImage(String storedFilename) {
        // 이미지 정보 가져오기
        Image image = imageQueryService.getByStoredFilename(storedFilename);

        // 리소스 반환
        return storageService.loadAsResource(image);
    }

    /**
     * 이미지 저장 어뷰징 방지
     *
     * @param saveImageRequest
     */
    private void preventImageAbusing(SaveImageRequest saveImageRequest) {
        List<Image> unassignedImages = imageQueryService.findByUserIdAndSubPostIdIsNullAndDeletedFalse(authService.getCurrentUser().getId());
        List<Image> imagesToDelete = imageCommandService.preventAbusing(saveImageRequest.getImages(), unassignedImages);
        imagesToDelete.forEach(storageService::deleteImage);
    }
}
