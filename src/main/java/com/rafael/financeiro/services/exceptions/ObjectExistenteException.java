package com.rafael.financeiro.services.exceptions;

public class ObjectExistenteException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ObjectExistenteException(String msg) {
		super(msg);
	}
	
	public ObjectExistenteException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
