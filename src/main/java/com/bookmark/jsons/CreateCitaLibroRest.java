package com.bookmark.jsons;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCitaLibroRest {

	@JsonProperty("autor")
	private String autor;

	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("createAt")
	private Date createAt;

	@JsonProperty("libroId")
	private Long libroId;
	
	@JsonProperty("fechaCita")
	private String fechaCita;

	@JsonProperty("pagina")
	private String pagina;
}
