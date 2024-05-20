package com.view.zib.domain.post.controller.request;

import com.view.zib.domain.building.enums.BuildingType;

import java.util.List;

public record GetPostsRequest (
        String longitude,
        String latitude,
        int maxDistance, // in meters
        List<Long> postIds
//        String address,
//        BuildingType buildingType,
//        String buildingName
) {
}
