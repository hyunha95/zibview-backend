package com.view.zib.domain.buildingSearch.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.view.zib.domain.buildingSearch.document.BuildingSearch;
import com.view.zib.domain.buildingSearch.service.BuildingSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BuildingSearchServiceImpl implements BuildingSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchHits<BuildingSearch> searchAutocomplete(String query) {
        MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                .fields("addressSearchAsYouType", "buildingNameSearchAsYouType")
                .query(query)
                .type(TextQueryType.BoolPrefix)
                .build();

        Query withQuery = new Query.Builder()
                .multiMatch(multiMatchQuery)
                .build();

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(withQuery)
                .build();

        return elasticsearchOperations.search(searchQuery, BuildingSearch.class);
    }

    public SearchHits<BuildingSearch> searchWithEdgeNgram(String query) {
        MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                .fields("addressNgrams", "buildingNameNgrams")
                .query(query)
                .build();

        Query withQuery = new Query.Builder()
                .multiMatch(multiMatchQuery)
                .build();

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(withQuery)
                .build();

        return elasticsearchOperations.search(nativeQuery, BuildingSearch.class);
    }
}
