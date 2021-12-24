package com.bookmark.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class BookmarkResponse <T> implements Serializable {

	private static final long serialVersionUID = 6419894644804410433L;
	
	private String status;
	
	private String code;
	
	private String message;
	
	private T data;
	
	public BookmarkResponse(String status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
