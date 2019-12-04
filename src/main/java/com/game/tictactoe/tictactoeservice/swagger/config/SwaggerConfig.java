package com.game.tictactoe.tictactoeservice.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration

public class SwaggerConfig extends WebMvcConfigurationSupport  {

	
	@Bean
	public Docket api() {		
		return new Docket(DocumentationType.SWAGGER_2).
		select().apis(RequestHandlerSelectors.basePackage("com.game.tictactoe.tictactoeservice.controller")).
		paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointInfo());
		
	}
	
	private ApiInfo apiEndPointInfo() {
		return new ApiInfoBuilder().title("Tic Tac Toe Game Rest API")
	            .description("Rest API to play Tic Tac Toe Game ")
	            .build();
	}
	
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
