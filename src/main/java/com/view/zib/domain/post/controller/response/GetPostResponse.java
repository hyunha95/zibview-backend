package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.address.entity.Address;

public record GetPostResponse (
        double latitude,
        double longitude
) {

    public static GetPostResponse from(Address address) {
        return new GetPostResponse(address.getLatitude(), address.getLongitude());
    }
}
