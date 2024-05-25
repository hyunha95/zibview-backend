package com.view.zib.domain.image.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageQueryServiceImpl implements ImageQueryService {

    private final ImageRepository imageRepository;
    private final AuthService authService;

    @Override
    public boolean isMyImage(String imageUuid) {
        Image image = imageRepository.findByUuid(imageUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "imageUuid", imageUuid));

        return image.getUser() == authService.getCurrentUser();
    }
}
