package com.bookmark.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "marca_libro")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaLibro implements Serializable {

	private static final long serialVersionUID = -2103619148766608374L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String descripcion;

	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String paginas;

	private String resumen;

	@NotNull(message = "no puede estar vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capitulo")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Capitulo capitulo;

}
