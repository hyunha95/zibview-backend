package com.view.zib.global.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
@Data
public class ApiProperties {

    private Zibview zibview;
    private VWorld vWorld;
    private Juso juso;
    private KakaoAddress kakaoAddress;
    private KakaoMap kakaoMap;

    @Data
    public static class Zibview {
        @NotEmpty
        private String url;
    }

    @Data
    public static class VWorld {
        @NotEmpty
        private String searchUrl;
        @NotEmpty
        private String key;
    }

    @Data
    public static class Juso {
        @NotEmpty
        private String key;
    }

    @Data
    public static class KakaoAddress {
        @NotEmpty
        private String key;
        @NotEmpty
        private String url;
    }

    @Data
    public static class KakaoMap {
        @NotEmpty
        private String key;
    }
}
