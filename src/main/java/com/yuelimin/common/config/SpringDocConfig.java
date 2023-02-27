package com.yuelimin.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuelimin
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "minio-service", description = "minio-service", version = "1.0.0"))
public class SpringDocConfig {
    // todo: open-api configuration
}

