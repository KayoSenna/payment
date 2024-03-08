package com.br.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	private String version = "1.0.0";
	
	  @Bean
	  public OpenAPI openApi() {
	      return new OpenAPI()
	              .info(new Info().title("Api Pagamento - API")
	              .description("API Pagamento Api")
	              .version(version)
	              .license(new License().name("Apache 2.0").url("http://springdoc.org")));

	  }
	
}
