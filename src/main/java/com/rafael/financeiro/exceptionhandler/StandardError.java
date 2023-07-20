package com.rafael.financeiro.exceptionhandler;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	private Integer status;
	private String path;
	
	public StandardError(String mensagemUsuario, String mensagemDesenvolvedor, Integer status, String path) {
		super();
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		this.status = status;
		this.path = path;
	}


	public String getMensagemUsuario() {
		return mensagemUsuario;
	}


	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}


	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}


	public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

}
