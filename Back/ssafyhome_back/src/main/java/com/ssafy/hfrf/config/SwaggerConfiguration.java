package com.ssafy.hfrf.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(
	        title = "홈파룸파 명세서",
	        description = "<h3>HFRF API Reference for Developers</h3>Swagger를 이용한 HFRF API",
	        version = "v1"
	    )
	)
@Configuration
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("All").pathsToMatch("/**").build();
	}

	@Bean
	public GroupedOpenApi boardApi() {
		return GroupedOpenApi.builder().group("Board").pathsToMatch("/board/**").build();
	}
	
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("User").pathsToMatch("/user/**").build();
	}
	
	@Bean
	public GroupedOpenApi mapApi() {
		return GroupedOpenApi.builder().group("Map").pathsToMatch("/map/**").build();
	}
	
}
