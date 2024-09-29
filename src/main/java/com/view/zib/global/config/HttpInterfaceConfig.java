package com.view.zib.global.config;

import com.view.zib.domain.client.kako.client.KakaoAddressClient;
import com.view.zib.global.properties.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

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

    @Bean("vworldRestClient")
    public RestClient vworldRestClient(RestClient.Builder builder) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory("http://apis.data.go.kr/1613000");
            uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return builder
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("officetelTransactionRestClient")
    public RestClient officetelRentRestClient(RestClient.Builder builder) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory("http://apis.data.go.kr/1613000/RTMSDataSvcOffiRent/getRTMSDataSvcOffiRent");
            uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return builder
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
