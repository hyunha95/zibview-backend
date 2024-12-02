package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.dto.NaverNewsResponse;
import com.view.zib.domain.client.naver.enums.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
public class NaverSearchClientImpl implements NaverSearchClient {

    private final RestClient naverSearchRestClient;

    public NaverSearchClientImpl(RestClient naverSearchRestClient) {
        this.naverSearchRestClient = naverSearchRestClient;
    }

    @Override
    public NaverNewsResponse searchNews(String query, int display, int start, Sort sort) {
        return naverSearchRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", URLEncoder.encode(query, StandardCharsets.UTF_8))
                        .queryParam("display", String.valueOf(display))
                        .queryParam("start", String.valueOf(start))
                        .queryParam("sort", sort.toString().toLowerCase())
                        .build()
                )
                .exchange(((clientRequest, clientResponse) -> {

                    HttpStatusCode statusCode = clientResponse.getStatusCode();
                    if (statusCode != HttpStatus.OK) {
                        log.error("naver news search api responded with status code: {}", statusCode);
                        throw new RuntimeException();
                    }

                    NaverNewsResponse naverNewsResponse = clientResponse.bodyTo(NaverNewsResponse.class);

                    if (naverNewsResponse == null) {
                        log.error("naver news search api responded with empty body");
                        throw new RuntimeException();
                    }
                    return naverNewsResponse;
                }));
    }
}
