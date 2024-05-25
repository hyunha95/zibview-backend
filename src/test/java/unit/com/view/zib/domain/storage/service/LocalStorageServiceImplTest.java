package com.view.zib.domain.storage.service;

import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.utils.NumberUtils;
import com.view.zib.mock.TestClockHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocalStorageServiceImplTest {

    private StorageService storageService;
    private NumberUtils numberUtils = new NumberUtils();

    @BeforeEach
    void init() {
        storageService = LocalStorageServiceImpl.builder()
                .numberUtils(numberUtils)
                .build();
    }

    @Test
    void store_ShouldSuccess() {
        // Given
        TestClockHolder clockHolder = new TestClockHolder(LocalDateTime.of(2000, 2, 2, 0, 0, 0));
        MultipartFile multipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        // When
        Storage storage = storageService.store(multipartFile, "", clockHolder);

        // Then
        assertThat(storage.originalFilename()).isEqualTo("test.jpg");
        assertThat(storage.extension()).isEqualTo("jpg");
        assertThat(storage.path()).isEqualTo("20000202");
    }

    @Test
    void createPath_ShouldSuccess() {
        // Given
        TestClockHolder testClockHolder = new TestClockHolder(LocalDateTime.of(2000, 2, 2, 0, 0, 0));

        // When
        String path = storageService.createPath(testClockHolder, numberUtils);

        // Then
        assertThat(path).isEqualTo("20000202");
    }

    @Test
    void testGetExtension_ShouldSuccess() {
        // Given
        String originalFilename = "test.jpg";

        // When
        String extension = storageService.getExtension(originalFilename);

        // Then
        assertEquals("jpg", extension);
    }

    @Test
    void whenGetExtensionWithEmptyString_thenReturnEmptyString() {
        // Given
        String originalFilename = "";

        // When
        String extension = storageService.getExtension(originalFilename);

        // Then
        assertEquals("", extension);
    }

    @Test
    void whenGetExtensionWithNullString_thenReturnEmptyString() {
        // Given
        String originalFilename = null;

        // When
        String extension = storageService.getExtension(originalFilename);

        // Then
        assertEquals("", extension);
    }

    @Test
    void whenGetExtensionWithNoExtension_thenReturnEmptyString() {
        // Given
        String originalFilename = "test";

        // When
        String extension = storageService.getExtension(originalFilename);

        // Then
        assertEquals("", extension);
    }
}