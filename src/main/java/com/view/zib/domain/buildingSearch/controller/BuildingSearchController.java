package com.view.zib.domain.buildingSearch.controller;

import com.view.zib.domain.buildingSearch.document.BuildingSearch;
import com.view.zib.domain.buildingSearch.service.BuildingSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/building-search")
@RequiredArgsConstructor
@RestController
public class BuildingSearchController {

    private final BuildingSearchService buildingSearchService;

    @GetMapping
    public SearchHits<BuildingSearch> search(@RequestParam String query) {
        return buildingSearchService.searchWithEdgeNgram(query);
    }

    @GetMapping("/autocomplete")
    public SearchHits<BuildingSearch> autocomplete(@RequestParam String query) {
        return buildingSearchService.searchAutocomplete(query);
    }
}