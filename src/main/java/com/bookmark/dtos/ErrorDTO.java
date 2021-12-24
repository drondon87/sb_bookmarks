package com.bookmark.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ErrorDTO implements Serializable{

	private static final long serialVersionUID = 7030167194881502485L;
	
	private String name;
	
	private String value;

}
