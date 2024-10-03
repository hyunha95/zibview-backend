package com.view.zib.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyLogMessageFormatConfiguration {

    @PostConstruct
    public void setLogMessageFormat() {
//        if ("prod".equalsIgnoreCase(springProfile)) {
            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfig.class.getName());
//        } else {
//            P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatDevConfig.class.getName());
//        }
    }
}