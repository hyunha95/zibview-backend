package com.view.zib.domain.image.service;

import com.view.zib.domain.image.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageQueryService {
    Image getByUuid(String imageUuid);
    Optional<Image> findByUuid(String imageUuid);
    Image getByStoredFilename(String storedFileName);
    Optional<Image> findMostRecentImageByPostIdIn(Long postId);

    List<Image> findByUuidIn(List<String> imageUuids);

    List<Image> findByPostIdInOrderByCreatedAtDesc(List<Long> postIds);

    List<Image> findByUserIdAndSubPostIdIsNullAndDeletedFalse(Long currentUserId);
}
