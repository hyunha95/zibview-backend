package com.view.zib.domain.storage.service;

import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.global.common.ClockHolder;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    Storage store(MultipartFile file, ClockHolder clockHolder);

    String createPath(ClockHolder clockHolder);
    String getExtension(String originalFilename);
    String getExtension(MultipartFile multipartFile);

}
