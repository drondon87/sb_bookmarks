package com.bookmark.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.bookmark.dtos.ErrorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookMarkException extends Exception {

	private static final long serialVersionUID = -4831551293070830447L;

	private final String code;

	private final int responseCode;

	private final List<ErrorDTO> errorList = new ArrayList<ErrorDTO>();

	public BookMarkException(String code, int responseCode, String message) {
		super(message);
		this.code = code;
		this.responseCode = responseCode;
	}

}
