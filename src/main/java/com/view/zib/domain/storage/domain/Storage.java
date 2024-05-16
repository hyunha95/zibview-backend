package com.view.zib.domain.storage.domain;

import lombok.Builder;

public record Storage(
        String mimeType,
        long fileSize,
        String originalFilename,
        String extension,
        String path
) {

    @Builder
    public Storage {
    }
}
