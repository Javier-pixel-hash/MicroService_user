package com.UsuarioProgramming.User_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI CustomOpenApi() {
        return new OpenAPI()
                    .info(new Info()
                        .title("API de Usuarios")
                        .version("1.0.0")
                        .description("API para gestionar usuarios en el sistema"));
                    }
}
