package com.view.zib.domain.image.repository;

import com.view.zib.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUuidIn(List<String> imageUuids);

    void deleteByUuid(String imageUuid);

    Optional<Image> findByUuid(String imageUuid);

    @Query("SELECT i FROM Image i " +
            "WHERE i.subPost.post.id = :postId AND i.representative = true " +
            "ORDER BY i.createdAt DESC " +
            "LIMIT 1")
    Image findLatestRepresentativeImage(@Param("postId") Long postId);

    Optional<Image> findByStoredFilename(String storedFileName);

    @Query("SELECT i FROM Image i " +
                "WHERE i.subPost.post.id = :postId " +
                "ORDER BY i.createdAt DESC " +
                "LIMIT 1")
    Optional<Image> findMostRecentImageByPostId(Long postId);

    @Query("SELECT i FROM Image i WHERE i.post.id IN :postIds ORDER BY i.createdAt")
    List<Image> findByPostIdInOrderByCreatedAtDesc(List<Long> postIds);

    @Query("SELECT i FROM Image i WHERE i.user.id = :currentUserId AND i.subPost IS NULL AND i.deleted = false")
    List<Image> findByUserIdAndSubPostIdIsNullAndDeletedFalse(Long currentUserId);
}
