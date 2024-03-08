package com.br.payment.execption;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	HttpStatus status;
	
	public BaseException(HttpStatus status,String mensagem) {
		super(mensagem);
		this.status = status;
	}
	
	public BaseException(String message){
	    super(message);
	}
	
}
