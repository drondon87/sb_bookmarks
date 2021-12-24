package com.bookmark.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "categoria")
@Entity
@Data  @NoArgsConstructor @AllArgsConstructor
public class Categoria implements Serializable{

	private static final long serialVersionUID = 9114707090564350846L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 30, message = "el tamaño tiene que estar entre 4 y 30 caracteres")
	@Column(nullable = false)
	private String nombre;
	
	private String descripcion;

}
