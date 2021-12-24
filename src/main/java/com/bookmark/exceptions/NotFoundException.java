package com.bookmark.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BookMarkException {

	private static final long serialVersionUID = 1551090623435216145L;

	public NotFoundException(String code, String message) {
		super(code, HttpStatus.NOT_FOUND.value(), message);
	}

}
