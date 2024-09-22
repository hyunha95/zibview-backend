package com.view.zib.domain.client.kako.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
@AllArgsConstructor
@Getter
public class KakaoAddressResponse {
    private Meta meta;
    private List<Document> documents;

    public Coordinate getCoordinate() {
        if (documents.isEmpty()) {
            return new Coordinate(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        Document document = documents.getFirst();
        return new Coordinate(BigDecimal.valueOf(document.getY()), BigDecimal.valueOf(document.getX()));
    }
}
