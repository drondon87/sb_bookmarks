package com.bookmark.jsons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCategoriaRest {

	@JsonProperty("nombre")
	private String nombre;

	@JsonProperty("descripcion")
	private String descripcion;

}
