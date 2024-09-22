package com.view.zib.domain.post.repository.dto;

import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LatestPost {
    private Long postId;
    private String sggName;
    private String emdName;
    private String buildingName;
    private String sggBuildingName;
    private Long likeCount;
    private Long commentCount;
    private Long viewCount;
    private String imagePath;
    private String imageStoredFilename;
    private LocalDateTime updatedAt;

    public PostSearchAsYouType toPostSearchAsYouType() {
        return PostSearchAsYouType.builder()
                .id(postId)
//                .roadNameAddress(roadNameAddress)
//                .jibunAddress(jibunAddress)
//                .buildingName(buildingName)
//                .sigunguBuildingName(sigunguBuildingName)
                .build();
    }
}
