package com.orla.api_projetos.handler;

import java.util.Locale;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.orla.api_projetos.dto.ErroDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@Autowired
	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex, Locale locale) {
		String detalhe = getFriendlyMessage(ex, locale);

		ErroDTO erroDTO = new ErroDTO("Violação de integridade", detalhe);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erroDTO);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroDTO> handleConstraintViolation(ConstraintViolationException ex, Locale locale) {
		String detalhe = getFriendlyMessage(ex, locale);

		ErroDTO erroDTO = new ErroDTO("Violação de restrição do banco de dados", detalhe);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erroDTO);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroDTO> handleOtherExceptions(Exception ex) {
		ErroDTO erroDTO = new ErroDTO("Erro interno", ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDTO);
	}

	private String getFriendlyMessage(Throwable ex, Locale locale) {
		Throwable root = ex;

		while (root.getCause() != null) {
			root = root.getCause();
		}

		String msg = root.getMessage();

		if (msg != null) {
			if (msg.contains("funcionario.cpf")) {
				return messageSource.getMessage("error.unique.cpf", null, locale);
			}

			if (msg.contains("funcionario.email")) {
				return messageSource.getMessage("error.unique.email", null, locale);
			}
		}

		return messageSource.getMessage("error.unique.default", null, locale);
	}
}