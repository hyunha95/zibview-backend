package com.view.zib.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS) // 직렬화 오류 방지
                .build();
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("some-header", "on every request");

        try {
            return ClientConfiguration.builder()
                    .connectedTo("182.213.255.106:9200")
                    .usingSsl(createSSLContext("certs/http_ca.crt"), (hostname, session) -> true) // 비활성화
                    .withBasicAuth("elastic", "IqGLPGLL0+xSJxqG=LrR")
                    .withConnectTimeout(Duration.ofSeconds(5))
                    .withSocketTimeout(Duration.ofSeconds(3))
                    .withDefaultHeaders(httpHeaders)
                    .withHeaders(() -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        return headers;
                    })
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // SSLContext 생성
    public static SSLContext createSSLContext(String crtFilePath) throws Exception {
        // 1. Load the certificate
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Resource resource = new ClassPathResource(crtFilePath);
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(resource.getInputStream());

        // 2. Create a KeyStore and load the certificate
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null); // Initialize empty KeyStore
        keyStore.setCertificateEntry("ca-cert", caCert);

        // 3. Create a TrustManagerFactory and initialize it with the KeyStore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // 4. Create the SSLContext and initialize it with the TrustManagers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }
}
