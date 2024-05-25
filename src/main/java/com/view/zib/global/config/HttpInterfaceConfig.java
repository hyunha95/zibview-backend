package com.view.zib.global.config;

import com.view.zib.domain.api.kako.service.KakaoAddressClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

    private final String kakaoAddressUrl;
    private final String kakaoAddressApiKey;

    public HttpInterfaceConfig(
            @Value("${api.kakao-address.url}") String kakaoAddressUrl,
            @Value("${api.kakao-address.key}") String kakaoAddressApiKey
    ) {
        this.kakaoAddressUrl = kakaoAddressUrl;
        this.kakaoAddressApiKey = kakaoAddressApiKey;
    }

    @Bean
    public KakaoAddressClient kakaoAddressClient(RestClient.Builder builder) {
        RestClient restClient =  builder
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", kakaoAddressApiKey))
                .baseUrl(kakaoAddressUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(KakaoAddressClient.class);
    }
}
