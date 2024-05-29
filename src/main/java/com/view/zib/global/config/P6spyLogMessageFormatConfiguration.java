package com.view.zib.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyLogMessageFormatConfiguration {

    private final String springProfile;

    public P6spyLogMessageFormatConfiguration(@Value("${spring.config.activate.on-profile}") String springProfile) {
        this.springProfile = springProfile;
    }

    @PostConstruct
    public void setLogMessageFormat() {
        if ("prod".equalsIgnoreCase(springProfile)) {
            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfig.class.getName());
        } else {
            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatDevConfig.class.getName());
        }
    }
}