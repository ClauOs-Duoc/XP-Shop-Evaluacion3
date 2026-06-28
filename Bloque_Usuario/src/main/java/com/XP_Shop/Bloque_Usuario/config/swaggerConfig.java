package com.XP_Shop.Bloque_Usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class swaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XP Shop - Bloque Usuario API")
                        .version("1.0")
                        .description("API para la gestión de usuarios en XP Shop"));
    }
}

// LINK DEL SWAGGER http://localhost:1837/doc/swagger-ui/index.html
// http://localhost:34931/doc/swagger-ui/index.html
