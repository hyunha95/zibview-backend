package com.view.zib.domain.image.service;

import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.domain.storage.service.StorageService;
import com.view.zib.global.common.ClockHolder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final ClockHolder clockHolder;

    @Transactional
    @Override
    public void saveImage(SaveImageRequest saveImageRequest) {
        saveImageRequest.getImages().forEach(image -> {
            Storage storage = storageService.store(image.getImage(), clockHolder);
            Image newImage = Image.from(image, storage);
            imageRepository.save(newImage);
        });
    }
}
