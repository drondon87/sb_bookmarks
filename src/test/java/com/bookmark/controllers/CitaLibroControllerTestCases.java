package com.bookmark.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bookmark.entities.Categoria;
import com.bookmark.entities.CitaLibro;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ICitaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCitaLibroRest;
import com.bookmark.response.BookmarkResponse;

public class CitaLibroControllerTestCases {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	public static final String MENSAJE_DELETE = "Cita Eliminada Exitosamente!!!";

	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";

	public static final Libro LIBRO = new Libro();
	public static final Long LIBRO_ID = 1L;
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final String FECHA_LIBRO = "14/12/2021";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";

	public static final CitaLibro CITA_LIBRO = new CitaLibro();
	public static final Long CITA_LIBRO_ID = 1L;
	public static final String AUTOR_CITA = "William Shakespear";
	public static final Date DATE_CITA = new Date();
	public static final String DESCRIPCION_CITA = "Un grande puede hacerse mas pequeño";
	public static final String FECHA_CITA_LIBRO = "14/12/2021";
	public static final CreateCitaLibroRest CREATE_CITA_LIBRO_REST = new CreateCitaLibroRest();
	public static final List<CitaLibro> CITA_LIBRO_LIST = new ArrayList<CitaLibro>();

	@Mock
	ICitaLibroService citalibroServices;

	@InjectMocks
	CitaLibroController underTest;

	@Before
	public void init() throws BookMarkException {

		MockitoAnnotations.initMocks(this);

		// Asigna datos categoria
		CATEGORIA.setId(CATEGORIA_ID);
		CATEGORIA.setNombre(NOMBRE);
		CATEGORIA.setDescripcion(DESCRIPCION);

		// Asigna datos libro
		LIBRO.setId(LIBRO_ID);
		LIBRO.setNombre(NOMBRE_LIBRO);
		LIBRO.setCreateAt(DATE_LIBRO);
		LIBRO.setDescripcion(DESCRIPCION_LIBRO);
		LIBRO.setCategoria(CATEGORIA);

		// Datos create cita libro
		CREATE_CITA_LIBRO_REST.setAutor(AUTOR_CITA);
		CREATE_CITA_LIBRO_REST.setFechaCita(FECHA_CITA_LIBRO);
		CREATE_CITA_LIBRO_REST.setDescripcion(DESCRIPCION_CITA);
		CREATE_CITA_LIBRO_REST.setLibroId(LIBRO_ID);

		// Datos de la Cita del Libro
		CITA_LIBRO.setId(CITA_LIBRO_ID);
		CITA_LIBRO.setAutor(AUTOR_CITA);
		CITA_LIBRO.setCreateAt(DATE_CITA);
		CITA_LIBRO.setDescripcion(DESCRIPCION_CITA);
		CITA_LIBRO.setLibro(LIBRO);

		CITA_LIBRO_LIST.add(CITA_LIBRO);

		// Se disparan los mockitos
		Mockito.when(citalibroServices.guardar(CREATE_CITA_LIBRO_REST)).thenReturn(CITA_LIBRO);

		Mockito.when(citalibroServices.listarCitasByLibro(LIBRO_ID)).thenReturn(CITA_LIBRO_LIST);

		Mockito.when(citalibroServices.findById(CITA_LIBRO_ID)).thenReturn(CITA_LIBRO);

		doNothing().when(citalibroServices).borrarCitaLibro(CITA_LIBRO_ID);

	}

	@Test
	public void crearCitaLibro() throws BookMarkException {
		final BookmarkResponse<CitaLibro> response = underTest.crearCitaLibro(CREATE_CITA_LIBRO_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CITA_LIBRO);
	}

	@Test
	public void listarCitasLibrosByLibro() throws BookMarkException {
		final BookmarkResponse<List<CitaLibro>> response = underTest.listarCitasPorLibro(LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CITA_LIBRO_LIST);
	}

	@Test
	public void listarCitaLibroById() throws BookMarkException {
		final BookmarkResponse<CitaLibro> response = underTest.buscarCitaLibroPorId(CITA_LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CITA_LIBRO);
	}

	@Test
	public void borrarCitaLibro() throws BookMarkException {
		final BookmarkResponse<?> response = underTest.borrarCitaLibro(CITA_LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), MENSAJE_DELETE);
	}

}
