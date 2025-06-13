package com.orla.api_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orla.api_projetos.entity.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}