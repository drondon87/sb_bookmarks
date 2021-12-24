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

@Table(name = "capitulo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo implements Serializable {

	private static final long serialVersionUID = 5948042174630936034L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String numero;

	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String nombre;

	private String descripcion;

	@NotNull(message = "no puede estar vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "libro_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Libro libro;
}
