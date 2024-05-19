package com.view.zib.domain.post.controller.request;

import com.view.zib.domain.building.enums.BuildingType;

public record GetPostsRequest (
        String longitude,
        String latitude,
        int maxDistance // in meters
//        String address,
//        BuildingType buildingType,
//        String buildingName
) {
}
