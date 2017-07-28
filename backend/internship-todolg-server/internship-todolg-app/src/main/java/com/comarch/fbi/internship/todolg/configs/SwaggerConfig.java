package com.comarch.fbi.internship.todolg.configs;

import static springfox.documentation.builders.PathSelectors.ant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Klasa odpowiedzialna za konfigurację Swaggera.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${api.path}")
    private String apiPath;

    /**
     * Konfiguracja Swaggera.
     *
     * @return Budowniczy konfiguracji swaggerowej.
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("server-api").apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.any()).paths(getSwaggerPaths()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Internship seed server with Swagger").version("1.0.0")
                .build();
    }

    private Predicate<String> getSwaggerPaths() {
        return ant(apiPath + "/**");
    }
}