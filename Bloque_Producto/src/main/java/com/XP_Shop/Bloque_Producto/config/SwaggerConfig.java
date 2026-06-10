package com.XP_Shop.Bloque_Producto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI{
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 Bloque Productos XP-Shop")
                        .version("1.0")
                        .description("Documentacion de la API del bloque productos de XP_Shop"));
    }

}
