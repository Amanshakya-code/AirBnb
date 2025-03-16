package com.aman.AirBnb.AirBnb.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Airbnb API")
                        .version("1.0")
                        .description("API documentation for the Airbnb Backend clone application, Created By Aman Kumar"))
                        .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                        .components(new Components()
                                .addSecuritySchemes("BearerAuth",
                                        new SecurityScheme()
                                                .name("BearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")));
    }
}
