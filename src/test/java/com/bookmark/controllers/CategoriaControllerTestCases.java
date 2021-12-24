package com.bookmark.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bookmark.entities.Categoria;
import com.bookmark.entities.services.ICategoriaService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CategoriaRest;
import com.bookmark.jsons.CreateCategoriaRest;
import com.bookmark.response.BookmarkResponse;

public class CategoriaControllerTestCases {

	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE_CATEGORIA = "HISTORIA";
	public static final String DESCRIPCION_CATEGORIA = "Libros de Historia";

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	public static final CreateCategoriaRest CREATE_CATEGORIA_REST = new CreateCategoriaRest();
	public static final Categoria CATEGORIA = new Categoria();
	public static final List<CategoriaRest> LISTA_CATEGORIA_REST = new ArrayList<CategoriaRest>();
	public static final CategoriaRest CATEGORIA_REST = new CategoriaRest();
	public static final String MENSAJE_DELETE = "Categoria Eliminada Exitosamente!!!";

	@Mock
	ICategoriaService categoriaService;

	@InjectMocks
	CategoriaController categoriaController;

	@Before
	public void init() throws BookMarkException {

		MockitoAnnotations.initMocks(this);

		CREATE_CATEGORIA_REST.setDescripcion(DESCRIPCION_CATEGORIA);
		CREATE_CATEGORIA_REST.setNombre(NOMBRE_CATEGORIA);

		Mockito.when(categoriaService.guardar(CREATE_CATEGORIA_REST)).thenReturn(CATEGORIA);
		
		CATEGORIA_REST.setId(CATEGORIA_ID);
		CATEGORIA_REST.setDescripcion(DESCRIPCION_CATEGORIA);
		CATEGORIA_REST.setNombre(NOMBRE_CATEGORIA);
		LISTA_CATEGORIA_REST.add(CATEGORIA_REST);
		
		Mockito.when(categoriaService.listarCategorias()).thenReturn(LISTA_CATEGORIA_REST);
		
		Mockito.when(categoriaService.findByNombre(NOMBRE_CATEGORIA)).thenReturn(CATEGORIA);
		
		Mockito.when(categoriaService.findById(CATEGORIA_ID)).thenReturn(CATEGORIA_REST);
		
		doNothing().when(categoriaService).borrarCategoria(CATEGORIA_ID);
	}

	@Test
	public void crearCategoria() throws BookMarkException {
		final BookmarkResponse<Categoria> response = categoriaController.crearCategoria(CREATE_CATEGORIA_REST);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CATEGORIA);
	}
	
	@Test
	public void listarCategorias() throws BookMarkException {
		final BookmarkResponse<List<CategoriaRest>> response = categoriaController.listarCategorias();
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LISTA_CATEGORIA_REST);
	}
	
	@Test
	public void listarCategoriaByNombre() throws BookMarkException {
		final BookmarkResponse<Categoria> response = categoriaController.buscarPorNombre(NOMBRE_CATEGORIA);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CATEGORIA);
	}
	
	@Test
	public void listarCategoriaById() throws BookMarkException {
		final BookmarkResponse<CategoriaRest> response = categoriaController.buscarPorId(CATEGORIA_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), CATEGORIA_REST);
	}
	
	@Test
	public void borrarCategoria() throws BookMarkException {
		final BookmarkResponse<?> response = categoriaController.borrarCategoria(CATEGORIA_ID);
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), MENSAJE_DELETE);
	}



}
