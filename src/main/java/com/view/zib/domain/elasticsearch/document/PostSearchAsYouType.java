package com.view.zib.domain.elasticsearch.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(indexName = "post-search-as-you-type")
public class PostSearchAsYouType {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String roadNameAddress;

    @Field(type = FieldType.Text)
    private String jibunAddress;

    @Field(type = FieldType.Text)
    private String buildingName;

    @Field(type = FieldType.Text)
    private String sigunguBuildingName;
}
