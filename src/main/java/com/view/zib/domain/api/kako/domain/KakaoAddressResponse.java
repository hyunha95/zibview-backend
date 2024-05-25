package com.view.zib.domain.api.kako.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoAddressResponse {
    private Meta meta;
    private List<Document> documents;

}
