package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.address.entity.Address;

public record GetPostResponse (
        String latitude,
        String longitude
) {

    public static GetPostResponse of(Address address) {
        return new GetPostResponse(address.getLatitude(), address.getLongitude());
    }
}
