package com.view.zib.domain.elasticsearch.controller.response;

import com.view.zib.domain.elasticsearch.document.AddressDocument;

public record AddressDocumentResponse(
        Long postId,
        String roadNameAddress,
        String jibunAddress,
        String buildingName,
        String sigunguBuildingName
) {

    public AddressDocumentResponse(AddressDocument addressDocument, String highlightedAddress) {
        this(addressDocument.getPostId(), highlightedAddress, addressDocument.getJibunAddress(),
                addressDocument.getBuildingName(), addressDocument.getSigunguBuildingName());
    }
}
