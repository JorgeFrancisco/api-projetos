package com.orla.api_projetos.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Funcionario {

	@Id
	@Column(name = "funcionario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long funcionarioId;

	@Column(nullable = false)
	private String nome;

	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private BigDecimal salario;

	@ManyToMany(mappedBy = "funcionarios")
	@ToString.Exclude
	private Set<Projeto> projetos;
}