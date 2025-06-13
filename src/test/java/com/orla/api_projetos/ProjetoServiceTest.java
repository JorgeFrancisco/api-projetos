package com.orla.api_projetos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.orla.api_projetos.dto.FuncionarioDTO;
import com.orla.api_projetos.dto.ProjetoComFuncionariosDTO;
import com.orla.api_projetos.dto.ProjetoDTO;
import com.orla.api_projetos.entity.Funcionario;
import com.orla.api_projetos.entity.Projeto;
import com.orla.api_projetos.repository.FuncionarioRepository;
import com.orla.api_projetos.repository.ProjetoRepository;
import com.orla.api_projetos.service.ProjetoService;

@SpringBootTest(classes = { ProjetoService.class })
@TestPropertySource("classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjetoServiceTest {

	@Autowired
	ProjetoService projetoService;

	@MockitoBean
	ProjetoRepository projetoRepository;

	@MockitoBean
	FuncionarioRepository funcionarioRepository;

	FuncionarioDTO funcionarioDTO;

	ProjetoDTO projetoDTO;

	Funcionario funcionario;

	Projeto projeto;

	@BeforeEach
	void setup() {
		funcionarioDTO = new FuncionarioDTO("João da Silva", "12345678900", "joao@email.com",
				new BigDecimal("5000.00"));

		projetoDTO = new ProjetoDTO();
		projetoDTO.setNome("Projeto A");
		projetoDTO.setDataCriacao(LocalDate.of(2025, 6, 13));
		projetoDTO.setFuncionarios(List.of(funcionarioDTO));

		funcionario = Funcionario.builder().funcionarioId(1L).nome(funcionarioDTO.getNome())
				.cpf(funcionarioDTO.getCpf()).email(funcionarioDTO.getEmail()).salario(funcionarioDTO.getSalario())
				.build();

		projeto = Projeto.builder().projetoId(1L).nome(projetoDTO.getNome()).dataCriacao(projetoDTO.getDataCriacao())
				.funcionarios(Set.of(funcionario)).build();
	}

	@Test
	void criaProjetoFuncionarioExisteRetornaProjetoComFuncionarioExistente() {
		when(funcionarioRepository.findByCpf(funcionarioDTO.getCpf())).thenReturn(Optional.of(funcionario));
		when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);

		ProjetoComFuncionariosDTO resultado = projetoService.criaProjeto(projetoDTO);

		assertNotNull(resultado);
		assertEquals(projeto.getProjetoId(), resultado.getId());
		assertEquals(projeto.getNome(), resultado.getNome());
		assertEquals(projeto.getDataCriacao(), resultado.getDataCriacao());
		assertFalse(resultado.getFuncionarios().isEmpty());
		assertEquals(funcionarioDTO.getCpf(), resultado.getFuncionarios().get(0).getCpf());

		verify(funcionarioRepository, never()).save(any());
		verify(projetoRepository, times(1)).save(any(Projeto.class));
	}

	@Test
	void criaProjetoFuncionarioNaoExisteSalvaFuncionarioENovoProjeto() {
		when(funcionarioRepository.findByCpf(funcionarioDTO.getCpf())).thenReturn(Optional.empty());
		when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);
		when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto);

		ProjetoComFuncionariosDTO resultado = projetoService.criaProjeto(projetoDTO);

		assertNotNull(resultado);
		assertEquals(projeto.getProjetoId(), resultado.getId());
		assertFalse(resultado.getFuncionarios().isEmpty());

		verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
		verify(projetoRepository, times(1)).save(any(Projeto.class));
	}

	@Test
	void criaProjetoSemFuncionariosSalvaProjetoSemFuncionarios() {
		projetoDTO.setFuncionarios(null);

		Projeto projetoSemFuncionarios = Projeto.builder().projetoId(projeto.getProjetoId()).nome(projeto.getNome())
				.dataCriacao(projeto.getDataCriacao()).funcionarios(Set.of()).build();

		when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoSemFuncionarios);

		ProjetoComFuncionariosDTO resultado = projetoService.criaProjeto(projetoDTO);

		assertNotNull(resultado);
		assertEquals(projeto.getProjetoId(), resultado.getId());
		assertTrue(resultado.getFuncionarios().isEmpty());

		verify(funcionarioRepository, never()).save(any());
		verify(projetoRepository, times(1)).save(any());
	}

	@Test
	void listarTodosRetornaListaDeProjetosDTO() {
		when(projetoRepository.findAll()).thenReturn(List.of(projeto));

		List<ProjetoComFuncionariosDTO> lista = projetoService.listarTodos();

		assertNotNull(lista);
		assertEquals(1, lista.size());

		ProjetoComFuncionariosDTO dto = lista.get(0);

		assertEquals(projeto.getProjetoId(), dto.getId());
		assertEquals(projeto.getNome(), dto.getNome());
		assertEquals(projeto.getDataCriacao(), dto.getDataCriacao());
		assertEquals(1, dto.getFuncionarios().size());
		assertEquals(funcionario.getCpf(), dto.getFuncionarios().get(0).getCpf());

		verify(projetoRepository, times(1)).findAll();
	}
}