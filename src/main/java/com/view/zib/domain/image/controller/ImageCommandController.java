package com.view.zib.domain.image.controller;

import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.image.facade.ImageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/images")
@RequiredArgsConstructor
@RestController
public class ImageCommandController {

    private final ImageFacade imageFacade;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveImage(SaveImageRequest saveImageRequest) {
        imageFacade.saveImage(saveImageRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{imageUuid}")
    public ResponseEntity<Void> deleteImage(@PathVariable(name = "imageUuid") String imageUuid) {
        imageFacade.deleteImage(imageUuid);
        return ResponseEntity.noContent().build();
    }
}
