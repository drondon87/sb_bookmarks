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
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ILibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.BusquedaRest;
import com.bookmark.jsons.CreateLibroRest;
import com.bookmark.response.BookmarkResponse;

public class LibroControllerTestCases {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";

	public static final Libro LIBRO = new Libro();
	public static final Long LIBRO_ID = 1L;
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final String FECHA_LIBRO = "14/12/2021";
	public static final String AUTOR_LIBRO = "Domingo Rondon";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";
	public static final List<Libro> LISTA_LIBRO = new ArrayList<Libro>();
	public static final CreateLibroRest CREATE_LIBRO_REST = new CreateLibroRest();
	public static final BusquedaRest BUSQUEDA_REST = new BusquedaRest();
	public static final String MENSAJE_DELETE = "Libro Eliminado Exitosamente!!!";

	@Mock
	ILibroService libroServices;

	@InjectMocks
	LibroController underTest;

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
		LIBRO.setAutor(AUTOR_LIBRO);

		LISTA_LIBRO.add(LIBRO);

		BUSQUEDA_REST.setNombre(NOMBRE_LIBRO);

		// Asigna datos del create libro para el guardado
		CREATE_LIBRO_REST.setNombre(NOMBRE_LIBRO);
		CREATE_LIBRO_REST.setCategoriaId(CATEGORIA_ID);
		CREATE_LIBRO_REST.setFechaLibro(FECHA_LIBRO);
		CREATE_LIBRO_REST.setDescripcion(DESCRIPCION_LIBRO);
		CREATE_LIBRO_REST.setAutor(AUTOR_LIBRO);

		// Se disparan los Mockitos
		Mockito.when(libroServices.listarLibros()).thenReturn(LISTA_LIBRO);

		Mockito.when(libroServices.guardar(CREATE_LIBRO_REST)).thenReturn(LIBRO);

		Mockito.when(libroServices.findByNombre(NOMBRE_LIBRO)).thenReturn(LIBRO);

		Mockito.when(libroServices.listarLibrosByCategoria(CATEGORIA_ID)).thenReturn(LISTA_LIBRO);

		Mockito.when(libroServices.findById(LIBRO_ID)).thenReturn(LIBRO);

		doNothing().when(libroServices).borrarLibro(LIBRO_ID);

	}

	@Test
	public void crearLibro() throws BookMarkException {
		final BookmarkResponse<Libro> response = underTest.crearLibro(CREATE_LIBRO_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LIBRO);
	}

	@Test
	public void listarLibros() throws BookMarkException {
		final BookmarkResponse<List<Libro>> response = underTest.listarLibros();
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LISTA_LIBRO);
	}

	@Test
	public void listarLibrosByNombre() throws BookMarkException {
		final BookmarkResponse<Libro> response = underTest.buscarLibroPorNombre(BUSQUEDA_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LIBRO);
	}

	@Test
	public void listarLibrosByCategoria() throws BookMarkException {
		final BookmarkResponse<List<Libro>> response = underTest.listarLibrosPorCategoria(CATEGORIA_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LISTA_LIBRO);
	}

	@Test
	public void listarLibroById() throws BookMarkException {
		final BookmarkResponse<Libro> response = underTest.buscarLibroPorId(LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LIBRO);
	}

	@Test
	public void borrarLibro() throws BookMarkException {
		final BookmarkResponse<?> response = underTest.borrarLibro(LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), MENSAJE_DELETE);
	}

}
