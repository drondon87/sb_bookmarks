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

import com.bookmark.entities.Capitulo;
import com.bookmark.entities.Categoria;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ICapituloService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCapituloRest;
import com.bookmark.response.BookmarkResponse;

public class CapituloControllerTestCases {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	public static final String MENSAJE_DELETE = "Capitulo Eliminado Exitosamente!!!";

	public static final Libro LIBRO = new Libro();
	public static final Long LIBRO_ID = 1L;
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final String FECHA_LIBRO = "14/12/2021";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";

	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";

	public static final Capitulo CAPITULO = new Capitulo();
	public static final CreateCapituloRest CREATE_CAPITULO_REST = new CreateCapituloRest();
	public static final Long CAPITULO_ID = 1L;
	public static final String NUMERO_CAPITULO = "1";
	public static final String DESCRIPCION_CAPITULO = "Capitulo de Prueba";
	public static final List<Capitulo> CAPITULO_LIST = new ArrayList<Capitulo>();

	@Mock
	ICapituloService capituloService;

	@InjectMocks
	CapituloController underTest;

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

		// Asigna datos al capitulo
		CAPITULO.setId(CAPITULO_ID);
		CAPITULO.setLibro(LIBRO);
		CAPITULO.setNombre(DESCRIPCION_CAPITULO);
		CAPITULO.setNumero(NUMERO_CAPITULO);

		CREATE_CAPITULO_REST.setDescripcion(DESCRIPCION_CAPITULO);
		CREATE_CAPITULO_REST.setLibroId(LIBRO_ID);
		CREATE_CAPITULO_REST.setNombre(NOMBRE);
		CREATE_CAPITULO_REST.setNumero(NUMERO_CAPITULO);

		CAPITULO_LIST.add(CAPITULO);

		// Se disparan los mockitos
		Mockito.when(capituloService.guardar(CREATE_CAPITULO_REST)).thenReturn(CAPITULO);

		Mockito.when(capituloService.listarCapitulosByLibro(LIBRO_ID)).thenReturn(CAPITULO_LIST);
		
		Mockito.when(capituloService.findById(CAPITULO_ID)).thenReturn(CAPITULO);

		doNothing().when(capituloService).borrarCapitulo(CAPITULO_ID);
	}

	@Test
	public void crearCapitulo() throws BookMarkException {
		final BookmarkResponse<Capitulo> response = underTest.crearCapitulo(CREATE_CAPITULO_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CAPITULO);
	}

	@Test
	public void listarCapitulosByLibro() throws BookMarkException {
		final BookmarkResponse<List<Capitulo>> response = underTest.listarCapitulosPorLibro(LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CAPITULO_LIST);
	}
	
	@Test
	public void listarCapituloById() throws BookMarkException {
		final BookmarkResponse<Capitulo> response = underTest.buscarCapituloPorId(CAPITULO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CAPITULO);
	}

	@Test
	public void borrarCapituloLibro() throws BookMarkException {
		final BookmarkResponse<?> response = underTest.borrarCapitulo(CAPITULO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), MENSAJE_DELETE);
	}

}
