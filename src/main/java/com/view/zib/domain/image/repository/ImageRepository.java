package com.view.zib.domain.image.repository;

import com.view.zib.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUuidIn(List<String> imageUuids);
}
