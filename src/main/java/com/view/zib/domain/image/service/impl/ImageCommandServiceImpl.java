package com.view.zib.domain.image.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.image.service.ImageCommandService;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.exception.exceptions.SqlFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ImageCommandServiceImpl implements ImageCommandService {

    private final ImageRepository imageRepository;
    private final AuthService authService;

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
}
