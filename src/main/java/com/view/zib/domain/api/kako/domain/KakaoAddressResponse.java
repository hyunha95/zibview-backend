package com.view.zib.domain.api.kako.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
public class KakaoAddressResponse {
    private Meta meta;
    private List<Document> documents;

    public Coordinate getCoordinate() {
        if (documents.isEmpty()) {
            return new Coordinate(0, 0);
        }

        Document document = documents.getFirst();
        return new Coordinate(document.getY(), document.getX());
    }
}
