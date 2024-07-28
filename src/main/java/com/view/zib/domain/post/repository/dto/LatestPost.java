package com.view.zib.domain.post.repository.dto;

import com.view.zib.domain.elasticsearch.document.PostDocument;
import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LatestPost {
    private Long postId;
    private String roadNameAddress;
    private String jibunAddress;
    private String buildingName;
    private String sigunguBuildingName;
    private Long likeCount;
    private Long commentCount;
    private Long viewCount;
    private String imagePath;
    private String imageStoredFilename;

    public PostDocument toPostDocument() {
        return PostDocument.builder()
                .id(postId)
                .roadNameAddress(roadNameAddress)
                .jibunAddress(jibunAddress)
                .buildingName(buildingName)
                .sigunguBuildingName(sigunguBuildingName)
                .build();
    }

    public PostSearchAsYouType toPostSearchAsYouType() {
        return PostSearchAsYouType.builder()
                .id(postId)
                .roadNameAddress(roadNameAddress)
                .jibunAddress(jibunAddress)
                .buildingName(buildingName)
                .sigunguBuildingName(sigunguBuildingName)
                .build();
    }
}
