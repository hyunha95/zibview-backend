package com.view.zib.domain.image.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.image.service.ImageCommandService;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.exception.exceptions.SqlFailedException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ImageCommandServiceImpl implements ImageCommandService {

    private final ImageRepository imageRepository;
    private final AuthService authService;

    public static final int MAX_NUMBER_OF_IMAGE = 10;

    @Transactional
    @Override
    public void saveImage(SaveImageRequest saveImageRequest, Map<String, Storage> storageByUuid) {
        saveImageRequest.getImages().forEach(image -> {
            Image newImage = Image.from(image, storageByUuid.get(image.getUuid()), authService.getCurrentUser());
            saveImage(newImage);
        });
    }

    private void saveImage(Image newImage) {
        try {
            imageRepository.save(newImage);
        } catch (Exception e) {
            throw new SqlFailedException("이미지 저장에 실패했습니다. 다시 시도해 주세요." + e);
        }
    }

    @Transactional
    @Override
    public void deleteImage(String imageUuid) {
        imageRepository.deleteByUuid(imageUuid);
    }

    @Transactional
    @Override
    public void delete(Image image) {
        image.delete();
    }

    /**
     *  이전에 이미지 저장까지만하고 submit 버튼을 누르지 않고 나갔을 경우 10개를 초과하는 이미지파일을 삭제(이미지 파일 저장 어뷰징 방지) -> 어뷰징이 아닌 경우는 배치로 처리
     *
     * 예외 케이스
     * 1. 이미 저장 됐지만 subPost등록까지 되지 않은 이미지 파일 8개
     * 최종 등록으로 5개 등록 -> 8 + 5 = 13개 -> 최초 3개 파일 삭제
     * 2. 이미 저장 됐지만 subPost등록까지 되지 않은 이미지 파일 10개
     * 최종 등록으로 5개 등록 -> 10 + 5 = 15개 -> 최초 5개 파일 삭제
     */
    @Transactional
    @Override
    public List<Image> preventAbusing(List<SaveImageRequest.Image> imagesToSave, List<Image> unassignedImages) {
        if (CollectionUtils.isNotEmpty(imagesToSave) && CollectionUtils.isNotEmpty(unassignedImages)) {
            return Collections.emptyList();
        }

        int sum = imagesToSave.size() + unassignedImages.size();
        if (sum < MAX_NUMBER_OF_IMAGE) {
            return Collections.emptyList();
        }

        int deleteCount = sum - MAX_NUMBER_OF_IMAGE;
        List<Image> imagesToDelete = unassignedImages.stream()
                .sorted(Comparator.comparing(Image::getCreatedAt))
                .limit(deleteCount)
                .toList();

        imagesToDelete.forEach(this::delete);
        return imagesToDelete;
    }
}
