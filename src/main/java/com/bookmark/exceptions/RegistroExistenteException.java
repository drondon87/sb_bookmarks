package com.bookmark.exceptions;

import org.springframework.http.HttpStatus;

public class RegistroExistenteException extends BookMarkException {

	private static final long serialVersionUID = -5976031394501211080L;

	public RegistroExistenteException(String code, String message) {
		super(code, HttpStatus.ALREADY_REPORTED.value(), message);
	}

}
