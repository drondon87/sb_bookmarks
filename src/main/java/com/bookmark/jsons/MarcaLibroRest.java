package com.bookmark.jsons;

import com.bookmark.entities.Libro;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarcaLibroRest {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("capitulo")
	private String capitulo;

	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("paginas")
	private String paginas;

	@JsonProperty("resumen")
	private String resumen;

	@JsonProperty("libro")
	private Libro libro;

}
