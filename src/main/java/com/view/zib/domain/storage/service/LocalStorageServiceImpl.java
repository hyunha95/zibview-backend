package com.view.zib.domain.storage.service;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.exception.exceptions.StorageFileNotFoundException;
import com.view.zib.global.utils.NumberUtils;
import io.jsonwebtoken.lang.Collections;
import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
public class LocalStorageServiceImpl implements StorageService {
    private final String zibViewUrl;
    private final String storageRootPath;
    private final NumberUtils numberUtils;

    @Builder
    public LocalStorageServiceImpl(
            @Value("${api.zibview.url}") String zibViewUrl,
            @Value("${storage.root-path}") String storageRootPath,
            NumberUtils numberUtils) {
        this.zibViewUrl = zibViewUrl;
        this.storageRootPath = storageRootPath;
        this.numberUtils = numberUtils;
    }

    @Override
    public Storage store(MultipartFile file, String uuid, ClockHolder clockHolder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file.");
        }

        // create path
        String path = createPath("images", clockHolder, numberUtils);
        Path directories = createDirectories(storageRootPath, path);
        Path destinationFile = directories.resolve(uuid + "." + getExtension(file));

        // This is a security check
        if (!destinationFile.getParent().equals(Paths.get(storageRootPath, path))) {
            throw new IllegalArgumentException("Cannot store file outside current directory.");
        }

        // save file
        saveFile(file, destinationFile);

        return Storage.builder()
                .uuid(uuid)
                .originalFilename(file.getOriginalFilename())
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .extension(getExtension(file))
                .path(path)
                .build();
    }

    private static void saveFile(MultipartFile file, Path destinationFile) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saving a file [{} -> {}]", file.getOriginalFilename(), destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file to storage", e);
        }
    }

    /**
     * 이미지 URL 생성
     *
     * @param image
     * @return
     */
    @Override
    public String generateImageUrl(Image image) {
        if (image == null) {
            return null;
        }

        return String.format("%s/api/%s/%s", zibViewUrl, image.getPath(), image.getStoredFilename());
    }

    @Override
    public List<String> generateImageUrls(List<Image> images) {
        if (Collections.isEmpty(images)) return List.of();
        return images.stream().map(this::generateImageUrl).toList();
    }

    @Override
    public String generateImageUrl(String path, String storedFilename) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(storedFilename)) {
            return null;
        }

        return String.format("%s/api/%s/%s", zibViewUrl, path, storedFilename);
    }

    @Override
    public String generateImageUrn(Image image) {
        if (image == null) {
            return null;
        }

        return String.format("/api/%s/%s", image.getPath(), image.getStoredFilename());
    }

    @Override
    public String generateImageUrn(String path, String storedFilename) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(storedFilename)) {
            return null;
        }

        return String.format("/api/%s/%s", path, storedFilename);
    }

    @Override
    public List<String> generateImageUrns(List<Image> images) {
        if (Collections.isEmpty(images)) return List.of();
        return images.stream().map(this::generateImageUrn).toList();
    }

    /**
     * 이미지 삭제
     * @param image
     */
    @Override
    public void deleteImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Image is null");
        }

        File file = Paths.get(storageRootPath)
                .resolve(image.getPath())
                .resolve(image.getStoredFilename())
                .toFile();

        boolean deleted = file.delete();
        if (deleted) {
            log.info("Deleting a file: [{}]", file.getPath());
        } else {
            log.error("Failed to delete a file: [{}]", file.getPath());
            throw new IllegalArgumentException("Failed to delete a file.");
        }
    }

    @Override
    public Resource loadAsResource(Image image) {
        Path file = Paths.get(storageRootPath, image.getPath(), image.getStoredFilename());
        Resource resource = new FileSystemResource(file.toString());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new StorageFileNotFoundException("Could not read file: " + image.getOriginalFilename());
        }
    }
}
