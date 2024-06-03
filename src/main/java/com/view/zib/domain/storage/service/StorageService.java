package com.view.zib.domain.storage.service;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.utils.NumberUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public interface StorageService {

    Storage store(MultipartFile file, String uuid, ClockHolder clockHolder);
    String generateImageUrl(Image image);
    String generateImageUrn(Image image);
    void deleteImage(Image image);
    Resource loadAsResource(Image image);

    /**
     * 파일 저장 루트 경로를 생성
     * @param rootPath
     * @param path
     * @return
     */
    default Path createDirectories(String rootPath, String path) {
        try {
            Path directoryPath = Paths.get(rootPath, path);
            if (!Files.exists(directoryPath)) {
                return Files.createDirectories(directoryPath);
            }
            return directoryPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage directory("+rootPath+")", e);
        }
    }

    /**
     * 파일 저장 경로를 생성
     * @param clockHolder
     * @param numberUtils
     * @return
     */
    default String createPath(String directory, ClockHolder clockHolder, NumberUtils numberUtils) {
        LocalDateTime now = clockHolder.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        return directory + "/" + year + numberUtils.zeroPadNumber(month, 2) + numberUtils.zeroPadNumber(day, 2);
    }

    /**
     * 파일 확장자를 추출
     * @param originalFilename
     * @return
     */
    default String getExtension(String originalFilename) {
        if (StringUtils.isBlank(originalFilename) || !originalFilename.contains(".")) {
            return "";
        }

        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    /**
     * 파일 확장자를 추출
     * @param multipartFile
     * @return
     */
    default String getExtension(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "";
        }
        return getExtension(multipartFile.getOriginalFilename());
    }
}
