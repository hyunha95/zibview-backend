package com.view.zib.domain.buildingSearch.document;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@Document(indexName = "building_search")
public class BuildingSearch {
    @Id
    private Long additionalInfoId;

    @Field(type = FieldType.Keyword)
    private String managementNo;

    @Field(type = FieldType.Keyword)
    private String buildingNameKeyword;

    @Field(type = FieldType.Text, analyzer = "nori_edge_ngram_analyzer")
    private String buildingNameNgrams;

    @Field(type = FieldType.Search_As_You_Type)
    private String buildingNameSearchAsYouType;

    @Field(type = FieldType.Keyword)
    private String dongNameWithBuildingName;

    @Field(type = FieldType.Keyword)
    private BigDecimal xCoordinate;

    @Field(type = FieldType.Keyword)
    private BigDecimal yCoordinate;
}
