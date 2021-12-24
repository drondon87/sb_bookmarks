package com.bookmark.jsons;

import java.util.Date;

import com.bookmark.entities.Libro;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CitaLibroRest {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("autor")
	private String autor;

	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("createAt")
	private Date createAt;

	@JsonProperty("libro")
	private Libro libro;

}
