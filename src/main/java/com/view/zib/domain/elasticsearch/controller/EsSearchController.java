package com.view.zib.domain.elasticsearch.controller;

import com.view.zib.domain.elasticsearch.controller.response.AddressDocumentResponse;
import com.view.zib.domain.elasticsearch.document.AddressDocument;
import com.view.zib.domain.elasticsearch.document.PostDocument;
import com.view.zib.domain.elasticsearch.service.PostElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/es")
@RequiredArgsConstructor
@RestController
public class EsSearchController {

    private final PostElasticSearchService postElasticSearchService;

    @GetMapping("/posts")
    public Page<PostDocument> searchPosts(String query, Pageable pageable) {
        log.info("searchPosts query: {}", query);
        return postElasticSearchService.multiMatchAddressAndBuildingName(query, pageable);
    }

    @GetMapping("/match-phrase-prefix")
    public List<AddressDocumentResponse> matchPhrasePrefix(String query) {
        log.info("matchPhrasePrefix query: {}", query);
        return postElasticSearchService.matchPhrasePrefix(query);
    }
}
