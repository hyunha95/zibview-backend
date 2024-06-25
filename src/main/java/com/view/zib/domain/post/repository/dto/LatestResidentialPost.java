package com.view.zib.domain.post.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LatestResidentialPost {
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
}
