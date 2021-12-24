package com.bookmark.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerException extends BookMarkException{

	private static final long serialVersionUID = 6310923496941434711L;

	public InternalServerException(String code, String message) {
		super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}
	
	public InternalServerException(String code, int responseCode, String message) {
		super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}

}
