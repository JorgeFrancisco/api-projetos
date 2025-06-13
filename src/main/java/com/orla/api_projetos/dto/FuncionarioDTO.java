package com.orla.api_projetos.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
public class FuncionarioDTO {

	@Schema(description = "Nome do funcionário", example = "João da Silva")
	@NotBlank(message = "O nome é obrigatório.")
	@JsonProperty("nome")
	private String nome;

	@Schema(description = "CPF do funcionário", example = "12345678901")
	@NotBlank(message = "O CPF é obrigatório.")
	@JsonProperty("cpf")
	private String cpf;

	@Schema(description = "Email do funcionário", example = "joao.silva@email.com")
	@Email(message = "Email inválido.")
	@NotBlank(message = "O email é obrigatório.")
	@JsonProperty("email")
	private String email;

	@Schema(description = "Salário do funcionário", type = "number", format = "decimal", example = "4500.00")
	@NotNull(message = "O salário é obrigatório.")
	@JsonProperty("salario")
	private BigDecimal salario;
}