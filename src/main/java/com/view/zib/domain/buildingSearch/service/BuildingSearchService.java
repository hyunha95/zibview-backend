package com.view.zib.domain.buildingSearch.service;

import com.view.zib.domain.buildingSearch.document.BuildingSearch;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface BuildingSearchService {

    SearchHits<BuildingSearch> searchAutocomplete(String query);
    SearchHits<BuildingSearch> searchWithEdgeNgram(String query);
}
