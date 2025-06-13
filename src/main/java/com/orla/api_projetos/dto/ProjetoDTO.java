package com.orla.api_projetos.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProjetoDTO {

	@Schema(description = "Nome do projeto", example = "Sistema de Gestão de RH")
	@NotBlank(message = "O nome do projeto é obrigatório.")
	@JsonProperty("nome")
	private String nome;

	@Schema(description = "Data de criação do projeto", example = "2025-06-13")
	@NotNull(message = "A data de criação é obrigatória.")
	@JsonProperty("dataCriacao")
	private LocalDate dataCriacao;

	@Schema(description = "Lista de funcionários vinculados ao projeto")
	@JsonProperty("funcionarios")
	private List<FuncionarioDTO> funcionarios;
}