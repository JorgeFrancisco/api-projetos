package com.orla.api_projetos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orla.api_projetos.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Optional<Funcionario> findByCpf(String cpf);
}