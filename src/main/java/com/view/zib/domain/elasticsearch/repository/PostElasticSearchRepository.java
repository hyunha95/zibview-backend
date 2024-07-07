package com.view.zib.domain.elasticsearch.repository;

import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostElasticSearchRepository extends ElasticsearchRepository<PostSearchAsYouType, Long> {

    @Query("""
            {
              "query": {
                "multi_match": {
                  "query": "?0",
                  "type": "bool_prefix",
                  "fields": [
                    "address",
                    "address._2gram",
                    "address._3gram",
                    "buildingName",
                    "buildingName._2gram",
                    "buildingName._3gram"
                  ]
                }
              },
              "size": 10,
            }
            """)
    List<PostSearchAsYouType> searchAsYouTypeAddressAndBuildingName(String query);
}
