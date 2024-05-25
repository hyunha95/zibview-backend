package com.view.zib.domain.storage.service;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.utils.NumberUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface StorageService {

    Storage store(MultipartFile file, String uuid, ClockHolder clockHolder);

    String generateImageUrl(Image image);

    void deleteImage(String imageUuid);

    /**
     * 파일 저장 경로를 생성
     * @param clockHolder
     * @param numberUtils
     * @return
     */
    default String createPath(ClockHolder clockHolder, NumberUtils numberUtils) {
        LocalDateTime now = clockHolder.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        return year + numberUtils.zeroPadNumber(month, 2) + numberUtils.zeroPadNumber(day, 2);
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
