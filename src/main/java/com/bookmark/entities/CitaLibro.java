package com.bookmark.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cita_libro")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaLibro implements Serializable{

	private static final long serialVersionUID = 8236718124470393975L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 100, message = "el tamaño tiene que estar entre 4 y 100 caracteres")
	@Column(nullable = false)
	private String autor;
	
	@NotEmpty(message = "no puede estar vacio")
	@Column(nullable = false)
	private String descripcion;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	private String pagina;
	
	@NotNull(message = "no puede estar vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "libro_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Libro libro;
	

}
