package com.XP_Shop.Bloque_Boleta.config;

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
                        .title("XP Shop - Bloque Boleta API")
                        .version("1.0")
                        .description("API para la gestión de boletas en XP Shop"));
    }

    //link del swagger http://localhost:8081/doc/swagger-ui/index.html
}
