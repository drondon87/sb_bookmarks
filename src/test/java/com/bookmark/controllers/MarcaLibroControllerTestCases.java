package com.bookmark.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bookmark.entities.Capitulo;
import com.bookmark.entities.Categoria;
import com.bookmark.entities.Libro;
import com.bookmark.entities.MarcaLibro;
import com.bookmark.entities.services.IMarcaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateMarcaLibroRest;
import com.bookmark.response.BookmarkResponse;

public class MarcaLibroControllerTestCases {

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	public static final String MENSAJE_DELETE = "Marca Eliminada Exitosamente!!!";

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
	public static final Long CAPITULO_ID = 1L;
	public static final String NUMERO_CAPITULO = "1";
	

	public static final MarcaLibro MARCA_LIBRO = new MarcaLibro();
	public static final CreateMarcaLibroRest CREATE_MARCA_LIBRO_REST = new CreateMarcaLibroRest();
	public static final String DESCRIPCION_CAPITULO = "Capitulo de Prueba";
	public static final String PAGINAS = "37,38,95,84";
	public static final String RESUMEN = "Capitulo sobre las pruebas";
	public static final Long MARCA_LIBRO_ID = 1L;
	public static final List<MarcaLibro> MARCA_LIBRO_LIST = new ArrayList<MarcaLibro>();
	public static final Optional<MarcaLibro> OPTIONAL_MARCA_LIBRO_EMPTY = Optional.empty();
	public static final Optional<MarcaLibro> OPTIONAL_MARCA_LIBRO = Optional.of(MARCA_LIBRO);

	@Mock
	IMarcaLibroService marcalibroServices;

	@InjectMocks
	MarcaLibroController underTest;

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
		
		CREATE_MARCA_LIBRO_REST.setCapituloId(CAPITULO_ID);
		CREATE_MARCA_LIBRO_REST.setDescripcion(DESCRIPCION_CAPITULO);
		CREATE_MARCA_LIBRO_REST.setPaginas(PAGINAS);
		CREATE_MARCA_LIBRO_REST.setResumen(RESUMEN);

		// Asigna datos de Marca Libro
		MARCA_LIBRO.setCapitulo(CAPITULO);
		MARCA_LIBRO.setDescripcion(DESCRIPCION_CAPITULO);
		MARCA_LIBRO.setPaginas(PAGINAS);
		MARCA_LIBRO.setResumen(RESUMEN);
		MARCA_LIBRO.setId(MARCA_LIBRO_ID);

		MARCA_LIBRO_LIST.add(MARCA_LIBRO);

		// Se disparan los mockitos
		Mockito.when(marcalibroServices.guardar(CREATE_MARCA_LIBRO_REST)).thenReturn(MARCA_LIBRO);

		Mockito.when(marcalibroServices.listarMarcasByLibro(LIBRO_ID)).thenReturn(MARCA_LIBRO_LIST);

		Mockito.when(marcalibroServices.findById(MARCA_LIBRO_ID)).thenReturn(MARCA_LIBRO);

		doNothing().when(marcalibroServices).borrarMarcaLibro(MARCA_LIBRO_ID);
		
		Mockito.when(marcalibroServices.listarMarcasByCapitulo(CAPITULO_ID)).thenReturn(MARCA_LIBRO_LIST);

	}

	@Test
	public void crearMarcaLibro() throws BookMarkException {
		final BookmarkResponse<MarcaLibro> response = underTest.crearMarcaLibro(CREATE_MARCA_LIBRO_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), MARCA_LIBRO);
	}

	@Test
	public void listarMarcasLibrosByLibro() throws BookMarkException {
		final BookmarkResponse<List<MarcaLibro>> response = underTest.listarMarcasPorLibro(LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), MARCA_LIBRO_LIST);
	}

	@Test
	public void listarMarcaLibroById() throws BookMarkException {
		final BookmarkResponse<MarcaLibro> response = underTest.buscarMarcaLibroPorId(MARCA_LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), MARCA_LIBRO);
	}

	@Test
	public void borrarMarcaLibro() throws BookMarkException {
		final BookmarkResponse<?> response = underTest.borrarMarcaLibro(MARCA_LIBRO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), MENSAJE_DELETE);
	}
	
	@Test
	public void listarMarcasLibrosByCapitulo() throws BookMarkException {
		final BookmarkResponse<List<MarcaLibro>> response = underTest.listarMarcasPorCapitulo(CAPITULO_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), MARCA_LIBRO_LIST);
	}

}
