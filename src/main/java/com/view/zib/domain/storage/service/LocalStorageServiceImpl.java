package com.view.zib.domain.storage.service;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.utils.NumberUtils;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalStorageServiceImpl implements StorageService {
    private final String zibViewUrl;

    private final NumberUtils numberUtils;

    @Builder
    public LocalStorageServiceImpl(@Value("${api.zibview.url}") String zibViewUrl, NumberUtils numberUtils) {
        this.zibViewUrl = zibViewUrl;
        this.numberUtils = numberUtils;
    }

    @Override
    public Storage store(MultipartFile file, String uuid, ClockHolder clockHolder) {
        // TODO: save file to storage


        return Storage.builder()
                .uuid(uuid)
                .originalFilename(file.getOriginalFilename())
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .extension(getExtension(file))
                .path(createPath(clockHolder, numberUtils))
                .build();
    }

    /**
     * 이미지 URL 생성
     * @param image
     * @return
     */
    @Override
    public String generateImageUrl(Image image) {
        return String.format("%s/%s/%s", zibViewUrl, image.getPath(), image.getStoredFilename());
    }

    @Override
    public void deleteImage(String imageUuid) {
        // TODO: delete file from storage
    }
}
