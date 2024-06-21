package com.view.zib.global.config;

import com.view.zib.domain.api.kako.client.KakaoAddressClient;
import com.view.zib.domain.api.vworld.client.AddressSearchClient;
import com.view.zib.global.properties.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@Configuration
public class HttpInterfaceConfig {

    private final ApiProperties apiProperties;

    @Bean
    public KakaoAddressClient kakaoAddressClient(RestClient.Builder builder) {
        RestClient restClient =  builder
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", apiProperties.getKakaoAddress().getKey()))
                .baseUrl(apiProperties.getKakaoAddress().getUrl())
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(KakaoAddressClient.class);
    }

    @Bean
    public AddressSearchClient jusoSearchClient(RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl(apiProperties.getVWorld().getSearchUrl())
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(AddressSearchClient.class);
    }
}
