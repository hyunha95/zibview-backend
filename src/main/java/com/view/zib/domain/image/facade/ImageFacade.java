package com.view.zib.domain.image.facade;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.service.ImageCommandService;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.exception.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @param saveImageRequest
     */
    public void saveImage(SaveImageRequest saveImageRequest) {
        // 스토리지에 이미지 파일 저장
        Map<String, Storage> storageByUuid = saveImageRequest.getImages().stream()
                .map(image -> storageService.store(image.getImage(), image.getUuid(), clockHolder))
                .collect(Collectors.toMap(Storage::uuid, Function.identity()));

        // 데이터베이스에 이미지 데이터 저장
        try {
            imageCommandService.saveImage(saveImageRequest, storageByUuid);
        } catch (Exception e) {
            // TODO 이미지 저장 실패 시 이미지 삭제 로직 추가
//            storageByUuid.values().forEach(storage -> this.deleteImage(storage));
            throw e;
        }
    }

    public void deleteImage(String imageUuid) {
        boolean mine = imageQueryService.isMyImage(imageUuid);

        if (!mine) {
            log.error("{} has no permission to delete image({})", authService.getEmail(), imageUuid);
            throw new ForbiddenException("이미지 삭제 권한이 없습니다.");
        }

        // 스토리지에서 이미지 파일 삭제
        storageService.deleteImage(imageUuid);

        // 데이터베이스에서 이미지 데이터 삭제
        imageCommandService.deleteImage(imageUuid);
    }
}
