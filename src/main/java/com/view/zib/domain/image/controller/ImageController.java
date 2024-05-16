package com.view.zib.domain.image.controller;

import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveImage(SaveImageRequest saveImageRequest) {
        imageService.saveImage(saveImageRequest);
    }
}
