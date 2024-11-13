package com.view.zib.domain.client.naver.client;

import com.view.zib.domain.client.naver.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class NaverSearchClientImpl implements NaverSearchClient {

    private final RestClient naverSearchRestClient;

    public NaverSearchClientImpl(RestClient naverSearchRestClient) {
        this.naverSearchRestClient = naverSearchRestClient;
    }

    @Override
    public void searchNews(String query, int display, int start, Sort sort) {
        RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse exchange = naverSearchRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query) // StandardCharsets.UTF_8.encode(query)
                        .queryParam("display", String.valueOf(display))
                        .queryParam("start", String.valueOf(start))
                        .queryParam("sort", sort.toString().toLowerCase())
                        .build()
                )
                .exchange(((clientRequest, clientResponse) -> {

//                    if (clientResponse.getStatusCode() != HttpStatus.OK) {
//                        throw new RuntimeException();
//                    }

                    String s = clientResponse.bodyTo(String.class);
                    log.info("result: {}", s);

                    return clientResponse;
                }));


    }
}
