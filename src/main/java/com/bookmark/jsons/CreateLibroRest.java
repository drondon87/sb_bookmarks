package com.bookmark.jsons;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateLibroRest {

	@JsonProperty("nombre")
	private String nombre;

	@JsonProperty("autor")
	private String autor;
	
	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("portada")
	private String portada;

	@JsonProperty("createAt")
	private Date createAt;

	@JsonProperty("categoriaId")
	private Long categoriaId;
	
	@JsonProperty("fechaLibro")
	private String fechaLibro;
}
