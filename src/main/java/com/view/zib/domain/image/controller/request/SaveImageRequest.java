package com.view.zib.domain.image.controller.request;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveImageRequest {
    @Size(max = 10)
    List<Image> images;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Image {
        private String uuid;
        private String dateTimeOriginal;
        private String latitudeGPS;
        private String longitudeGPS;
        private boolean representative;
        private MultipartFile image;
    }
}
