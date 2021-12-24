package com.bookmark.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMarcaLibroRest {

	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("paginas")
	private String paginas;

	@JsonProperty("resumen")
	private String resumen;

	@JsonProperty("capituloId")
	private Long capituloId;

}
