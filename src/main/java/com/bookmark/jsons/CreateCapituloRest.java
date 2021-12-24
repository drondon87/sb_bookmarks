package com.bookmark.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCapituloRest {

	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("numero")
	private String numero;

	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("libroId")
	private Long libroId;
}
