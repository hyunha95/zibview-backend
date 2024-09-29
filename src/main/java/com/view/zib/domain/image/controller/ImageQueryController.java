package com.view.zib.domain.image.controller;

import com.view.zib.domain.image.facade.ImageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/images")
@RequiredArgsConstructor
@RestController
public class ImageQueryController {

    private final ImageFacade imageFacade;

    @GetMapping(value = "/{path}/{storedFilename}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/webp"})
    public Resource getImage(
            @PathVariable(name = "path") String path,
            @PathVariable(name = "storedFilename") String storedFilename
    ) throws IOException {
        return imageFacade.getImage(storedFilename);
    }

    @GetMapping("/{imageName}")
    public Resource findImage(@PathVariable String imageName) {
        Resource resource = new ClassPathResource("static/images/" + imageName);
        return resource;
    }
}
