package com.view.zib.domain.image.service.impl;

import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.image.repository.ImageRepository;
import com.view.zib.domain.image.service.ImageQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageQueryServiceImpl implements ImageQueryService {

    private final ImageRepository imageRepository;
    private final AuthService authService;

    @Override
    public Image getByUuid(String imageUuid) {
        return this.findByUuid(imageUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "uuid", imageUuid));
    }

    @Override
    public Optional<Image> findByUuid(String imageUuid) {
        return imageRepository.findByUuid(imageUuid);
    }
}
