package com.view.zib.domain.post.controller.request;

import java.util.List;

public record GetPostsRequest (
        String longitude,
        String latitude,
        Integer maxDistance, // in meters
        List<Long> postIds
//        String address,
//        BuildingType buildingType,
//        String buildingName
) {
}
