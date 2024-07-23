package com.view.zib.domain.elasticsearch.service;

import com.view.zib.domain.elasticsearch.controller.response.AddressDocumentResponse;
import com.view.zib.domain.elasticsearch.document.PostDocument;
import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostElasticSearchService {

    void indexPost();

    Page<PostDocument> multiMatchAddressAndBuildingName(String query, Pageable pageable);

    List<PostSearchAsYouType> searchAsYouTypeAddressAndBuildingName(String query);

    List<AddressDocumentResponse> matchPhrasePrefix(String query);

}
