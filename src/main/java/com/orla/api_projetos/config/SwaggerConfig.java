package com.orla.api_projetos.config;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	private final BuildProperties buildProperties;

	@Value("${server.servlet.context-path}")
	String contextPath;

	@Autowired
	public SwaggerConfig(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@Bean
	public GroupedOpenApi projetosGroupedOpenApi() {
		return GroupedOpenApi.builder().group("Projetos").pathsToMatch("/projetos/**")
				.addOpenApiCustomizer(projetoOpenApiCustomizer()).packagesToScan("com.orla.api_projetos.controller")
				.build();
	}

	public OpenApiCustomizer projetoOpenApiCustomizer() {
		return openApi -> openApi.info(projetosInfo()).servers(Arrays.asList(new Server().url(contextPath)));
	}

	private Info projetosInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss.SSS");
		String formattedString = buildProperties.getTime().atZone(ZoneId.of("America/Sao_Paulo")).format(formatter);

		return new Info().title("API - Projetos").description("Documentação da API de Projetos.")
				.version(buildProperties.getVersion() + "-" + formattedString)
				.license(new License().name("Apache License Version 2.0")
						.url("https://www.apache.org/licenses/LICENSE-2.0\""))
				.contact(new Contact().name("Orla").url("http://www.orla.br").email("contato@orla.br"));
	}
}