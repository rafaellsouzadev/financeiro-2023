package com.rafael.financeiro.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rafael.financeiro.services.exceptions.AuthorizationException;
import com.rafael.financeiro.services.exceptions.DataIntegrityException;
import com.rafael.financeiro.services.exceptions.FileException;
import com.rafael.financeiro.services.exceptions.ObjectExistenteException;
import com.rafael.financeiro.services.exceptions.ObjectNotFoundException;
import com.rafael.financeiro.services.exceptions.UsernameNotFoundException;


@ControllerAdvice
public class FinanceiroExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("recurso.nao-encontrado", null, null),
				ex.toString(), HttpStatus.NOT_FOUND.value(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}	

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("integridade-de-dados", null, null),
				ex.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("acesso-negado", null, null), ex.getMessage(),
				HttpStatus.FORBIDDEN.value(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> authorizationErroLogin(UsernameNotFoundException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("acesso-nao-encontrado", null, null),
				ex.toString(), HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("erro-de-arquivo", null, null),
				ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<StandardError> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("recurso.nao-encontrado", null, null),
				ex.toString(), HttpStatus.NOT_FOUND.value(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("mensagem.invalida", null, null),
				ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getContextPath());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/* Captura argumentos de metodos que não são validos */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("metodo-invalido", null, null),
				ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getContextPath());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(ObjectExistenteException.class)
	public ResponseEntity<StandardError> nomeRepetido(ObjectExistenteException ex, HttpServletRequest request) {

		StandardError error = new StandardError(messageSource.getMessage("nome-repetido", null, null), ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

//	/* Captura erro na parte de banco */
//	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class,
//			InternalServerError.class, Throwable.class })
//	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex, HttpServletRequest request) {
//
//		StandardError error;
//
//		if (ex instanceof DataIntegrityViolationException) {
//
//			error = new StandardError(messageSource.getMessage("integridade-banco", null, null), ex.getMessage(),
//					HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
//
//		} else if (ex instanceof ConstraintViolationException) {
//
//			error = new StandardError(messageSource.getMessage("chave-estrangeira", null, null), ex.getMessage(),
//					HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
//
//		} else if (ex instanceof SQLException) {
//
//			error = new StandardError(messageSource.getMessage("sql-banco", null, null), ex.getMessage(),
//					HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
//
//		} else if (ex instanceof InvalidDataAccessResourceUsageException) {
//
//			error = new StandardError(messageSource.getMessage("sql-banco", null, null), ex.toString(),
//					HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
//
//		} else {
//
//			error = new StandardError(messageSource.getMessage("recurso.operacao-nao-permitida", null, null),
//					ex.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
//		}
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {

		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {

			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();

			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}


}
