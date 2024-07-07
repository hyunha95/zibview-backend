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
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String buildingNum;
    private String buildingSubNum;
    private String buildingName;
    private int likeCount;
    private int commentCount;
    private String imagePath;
    private String imageStoredFilename;

    public PostDocument toPostDocument() {
        return PostDocument.builder()
                .id(postId)
                .address(sidoName + " " + sigunguName + " " + roadName + " " + buildingNum + "-" + buildingSubNum)
                .buildingName(buildingName)
                .build();
    }

    public PostSearchAsYouType toPostSearchAsYouType() {
        return PostSearchAsYouType.builder()
                .id(postId)
                .address(sidoName + " " + sigunguName + " " + roadName + " " + buildingNum + "-" + buildingSubNum)
                .buildingName(buildingName)
                .build();
    }
}
