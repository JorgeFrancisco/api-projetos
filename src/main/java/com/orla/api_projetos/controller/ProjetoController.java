package com.orla.api_projetos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orla.api_projetos.dto.ProjetoComFuncionariosDTO;
import com.orla.api_projetos.dto.ProjetoDTO;
import com.orla.api_projetos.service.ProjetoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/projetos")
@Tag(description = "Conjunto de endpoints para recuperação e criação de projetos.", name = "Projetos")
public class ProjetoController {

	private final ProjetoService projetoService;

	@Autowired
	public ProjetoController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	@Operation(summary = "Cria um projeto", tags = "Projetos", responses = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content),
			@ApiResponse(responseCode = "401", description = "Uso do token obrigatório.", content = @Content),
			@ApiResponse(responseCode = "403", description = "Proibido acessar este recurso.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = @Content),
			@ApiResponse(responseCode = "405", description = "Método HTTP não permitido.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro no servidor.", content = @Content) })
	@PostMapping
	public ResponseEntity<?> criarProjeto(@RequestBody ProjetoDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.criaProjeto(dto));
	}

	@Operation(summary = "Retorna uma lista de projetos", tags = "Projetos", responses = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content),
			@ApiResponse(responseCode = "401", description = "Uso do token obrigatório.", content = @Content),
			@ApiResponse(responseCode = "403", description = "Proibido acessar este recurso.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = @Content),
			@ApiResponse(responseCode = "405", description = "Método HTTP não permitido.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro no servidor.", content = @Content) })
	@GetMapping
	public ResponseEntity<List<ProjetoComFuncionariosDTO>> listarProjetos() {
		return ResponseEntity.ok(projetoService.listarTodos());
	}
}