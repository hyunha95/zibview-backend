package com.view.zib.domain.buildingSearch.document;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "building_search")
public class BuildingSearch {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer", searchAnalyzer = "nori_analyzer")
    private String address;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer", searchAnalyzer = "nori_analyzer")
    private String buildingName;

    @Field(type = FieldType.Keyword)
    private String addressKeyword;

    @Field(type = FieldType.Text, analyzer = "nori_edge_ngram_analyzer")
    private String addressNgrams;

    @Field(type = FieldType.Search_As_You_Type)
    private String addressSearchAsYouType;

    @Field(type = FieldType.Keyword)
    private String buildingNameKeyword;

    @Field(type = FieldType.Text, analyzer = "nori_edge_ngram_analyzer")
    private String buildingNameNgrams;

    @Field(type = FieldType.Search_As_You_Type)
    private String buildingNameSearchAsYouType;




}
