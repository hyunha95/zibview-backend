package com.view.zib.domain.api.kako.service.impl;

import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.api.kako.domain.KakaoMapRequest;
import com.view.zib.domain.api.kako.domain.KakaoMapResponse;
import com.view.zib.domain.api.kako.service.KakaoMapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class KakaoMapServiceImpl implements KakaoMapService {

    @Value("${api.kakao.key}")
    private String kakaoApiKey;

    /**
     * 카카오 맵 API를 이용하여 주소 검색을 수행히여 좌표값을 얻기 위함
     */
    @Override
    public Document searchAddress(KakaoMapRequest request) {
        log.info("KaKao address search API has been called. query: {}", request.query());
        RestClient restClient = RestClient.create("https://dapi.kakao.com/v2/local/search/address.json");
        return restClient.get()
                .uri(buildUri(request))
                .header(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", kakaoApiKey))
                .exchange((clientRequest, clientResponse) -> {
                    if (clientResponse.getStatusCode().isError()) {
                        // TODO: 관리자가 알 수 있도록 슬랙이나 이메일 보내기
                        log.error("Failed to search address. status: {}, body: {}", clientResponse.getStatusCode(), clientResponse.getBody());
                    }

                    KakaoMapResponse kakaoMapResponse = clientResponse.bodyTo(KakaoMapResponse.class);
                    if (kakaoMapResponse == null) {
                        return new Document();
                    }

                    List<Document> documents = kakaoMapResponse.getDocuments();
                    if (documents != null && !documents.isEmpty()) {
                        return documents.getFirst();
                    } else {
                        return new Document();
                    }
                });
    }

    private Function<UriBuilder, URI> buildUri(KakaoMapRequest request) {
        return uriBuilder -> uriBuilder
                .queryParam("query", request.query())
                .queryParam("analyze_type", request.analyzeType())
                .queryParam("page", request.page())
                .queryParam("size", request.size())
                .build();

    }

    public static void main(String[] args) {
        KakaoMapServiceImpl kakaoMapService = new KakaoMapServiceImpl();
        KakaoMapRequest kakaoMapRequest = new KakaoMapRequest("서울특별시 강남구 역삼동 736-1", "", 1, 10);
        Document document = kakaoMapService.searchAddress(kakaoMapRequest);
        System.out.println(document);
    }
}
