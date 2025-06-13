package com.orla.api_projetos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orla.api_projetos.dto.FuncionarioDTO;
import com.orla.api_projetos.dto.ProjetoComFuncionariosDTO;
import com.orla.api_projetos.dto.ProjetoDTO;
import com.orla.api_projetos.entity.Funcionario;
import com.orla.api_projetos.entity.Projeto;
import com.orla.api_projetos.repository.FuncionarioRepository;
import com.orla.api_projetos.repository.ProjetoRepository;

@Service
public class ProjetoService {

	private final ProjetoRepository projetoRepository;

	private final FuncionarioRepository funcionarioRepository;

	@Autowired
	public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
		this.projetoRepository = projetoRepository;
		this.funcionarioRepository = funcionarioRepository;
	}

	public ProjetoComFuncionariosDTO criaProjeto(ProjetoDTO dto) {
		Projeto projeto = Projeto.builder().nome(dto.getNome()).dataCriacao(dto.getDataCriacao()).build();

		if (dto.getFuncionarios() != null && !dto.getFuncionarios().isEmpty()) {
			var funcionarios = dto.getFuncionarios().stream()
					.map(f -> funcionarioRepository.findByCpf(f.getCpf())
							.orElseGet(() -> funcionarioRepository.save(Funcionario.builder().nome(f.getNome())
									.cpf(f.getCpf()).email(f.getEmail()).salario(f.getSalario()).build())))
					.collect(Collectors.toSet());

			projeto.setFuncionarios(funcionarios);
		}

		Projeto salvo = projetoRepository.save(projeto);

		return mapToDTO(salvo);
	}

	public List<ProjetoComFuncionariosDTO> listarTodos() {
		return projetoRepository.findAll().stream().map(this::mapToDTO).toList();
	}

	private ProjetoComFuncionariosDTO mapToDTO(Projeto projeto) {
		List<FuncionarioDTO> funcionariosDTO = projeto.getFuncionarios().stream().map(this::mapToFuncionarioDTO)
				.toList();

		return new ProjetoComFuncionariosDTO(projeto.getProjetoId(), projeto.getNome(), projeto.getDataCriacao(),
				funcionariosDTO);
	}

	private FuncionarioDTO mapToFuncionarioDTO(Funcionario f) {
		return new FuncionarioDTO(f.getNome(), f.getCpf(), f.getEmail(), f.getSalario());
	}
}