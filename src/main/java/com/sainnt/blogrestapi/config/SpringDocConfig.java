package com.sainnt.blogrestapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public OpenAPI openApiInfo(){
        return new OpenAPI()
                .info(new Info()
                        .title("Blog REST API")
                        .description("Spring boot  REST API's learning project")
                        .version("1")
                        .termsOfService("Terms of service")
                        .contact(new Contact().name("Nikitin Mikhail").email("nitinin.miha@icloud.com"))
                )
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
                        ));
    }
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }
}
