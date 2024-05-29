package com.view.zib.domain.image.service;

import com.view.zib.domain.image.entity.Image;

import java.util.Optional;

public interface ImageQueryService {
    Image getByUuid(String imageUuid);
    Optional<Image> findByUuid(String imageUuid);

    Image getByStoredFilename(String storedFileName);
}
