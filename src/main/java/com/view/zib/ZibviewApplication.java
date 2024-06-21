package com.view.zib;

import com.view.zib.global.properties.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {ApiProperties.class})
@SpringBootApplication
public class ZibviewApplication {

	public static void main(String[] args) {
		// Check if the environment is production and ddl-auto is not none
		String profile = System.getenv("SPRING_PROFILES_ACTIVE");
		String ddlType = System.getenv("DDL_AUTO");
		if ("prod".equalsIgnoreCase(profile) && !"none".equalsIgnoreCase(ddlType)) {
			throw new RuntimeException("You can't use ddl-auto other than none in production environment.");
		}

		SpringApplication.run(ZibviewApplication.class, args);
	}
}
