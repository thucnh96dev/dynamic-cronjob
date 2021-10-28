package com.thucnh.cronjob;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dynamic cronjob API", version = "2.0", description = "Dynamic cronjob source"))
public class DynamicCronjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicCronjobApplication.class, args);
    }

}
