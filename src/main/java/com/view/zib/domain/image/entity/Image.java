package com.view.zib.domain.image.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.image.controller.request.SaveImageRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.storage.domain.Storage;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String uuid;
    private String mimeType;
    private long fileSize;
    private String storedFilename;
    private String originalFilename;
    private String extension;
    private String path;

    private String dateTimeOriginal; // TODO: LocalDateTime

    @Column(name = "latitude_gps")
    private String latitudeGPS;

    @Column(name = "longitude_gps")
    private String longitudeGPS;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean representative;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean deleted;

    public static Image from(SaveImageRequest.Image image, Storage storage, User currentUser) {
        return Image.builder()
                .user(currentUser)
                .uuid(image.getUuid())
                .mimeType(storage.mimeType())
                .fileSize(storage.fileSize())
                .storedFilename(image.getUuid() + "." + storage.extension())
                .originalFilename(storage.originalFilename())
                .extension(storage.extension())
                .path(storage.path())
                .dateTimeOriginal(image.getDateTimeOriginal())
                .latitudeGPS(image.getLatitudeGPS())
                .longitudeGPS(image.getLongitudeGPS())
                .representative(image.isRepresentative())
                .build();
    }

    public static Optional<Image> getLatestRepresentativeImage(List<Image> images) {
        return images.stream()
                .filter(Image::isRepresentative)
                .findFirst();
    }

    public void addEntity(SubPost subPost) {
        this.subPost = subPost;
    }

    public void addEntity(Address address) {
        this.address = address;
    }

    public void updateRepresentative(boolean bool) {
        this.representative = bool;
    }

    public void addEntity(Post post) {
        this.post = post;
    }

    public void delete() {
        this.deleted = true;
    }
}
