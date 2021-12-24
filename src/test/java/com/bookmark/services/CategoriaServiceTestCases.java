package com.bookmark.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bookmark.entities.Categoria;
import com.bookmark.entities.services.impl.CategoriaServiceImpl;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CategoriaRest;
import com.bookmark.jsons.CreateCategoriaRest;
import com.bookmark.repositories.ICategoriaRepository;

public class CategoriaServiceTestCases {

	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";
	public static final Categoria CATEGORIA = new Categoria();
	public static final CreateCategoriaRest CREATE_CATEGORIA_REST = new CreateCategoriaRest();
	public static final List<Categoria> CATEGORIA_LIST = new ArrayList<>();
	private static final Optional<Categoria> OPTIONAL_CATEGORIA = Optional.of(CATEGORIA);
	private static final Optional<Categoria> OPTIONAL_CATEGORIA_EMPTY = Optional.empty();
	public static final List<CategoriaRest> CATEGORIA_REST_LIST = new ArrayList<>();
	public static final CategoriaRest CATEGORIA_REST = new CategoriaRest();

	@Mock
	ICategoriaRepository categoriaRepository;

	@InjectMocks
	CategoriaServiceImpl categoriaService;

	@Before
	public void init() throws BookMarkException {
		MockitoAnnotations.initMocks(this);

		CATEGORIA.setId(CATEGORIA_ID);
		CATEGORIA.setNombre(NOMBRE);
		CATEGORIA.setDescripcion(DESCRIPCION);

		CREATE_CATEGORIA_REST.setNombre(NOMBRE);
		CREATE_CATEGORIA_REST.setDescripcion(DESCRIPCION);
		
		CATEGORIA_REST.setId(CATEGORIA_ID);
		CATEGORIA_REST.setNombre(NOMBRE);
		CATEGORIA_REST.setDescripcion(DESCRIPCION);
		
		CATEGORIA_LIST.add(CATEGORIA);
		CATEGORIA_REST_LIST.add(CATEGORIA_REST);
	}

	@Test
	public void crearCategoriaTest() throws BookMarkException {

		Mockito.when(categoriaRepository.findByNombre(NOMBRE)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);
		Mockito.when(categoriaRepository.save(Mockito.any(Categoria.class))).thenReturn(new Categoria());

		Categoria categoriaRespuesta = categoriaService.guardar(CREATE_CATEGORIA_REST);
		assertNotNull(categoriaRespuesta);
	}

	@Test(expected = BookMarkException.class)
	public void crearCategoriaRegistroExistenteTest() throws BookMarkException {

		Mockito.when(categoriaRepository.findByNombre(NOMBRE)).thenReturn(OPTIONAL_CATEGORIA);
		categoriaService.guardar(CREATE_CATEGORIA_REST);
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void crearCategoriaInternalErrorTest() throws BookMarkException {

		Mockito.when(categoriaRepository.findByNombre(NOMBRE)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);
		Mockito.doThrow(Exception.class).when(categoriaRepository).save(Mockito.any(Categoria.class));
		categoriaService.guardar(CREATE_CATEGORIA_REST);
		fail();
	}
	
	@Test
	public void listarCategoriasTest() throws BookMarkException {
		Mockito.when(categoriaRepository.findAll()).thenReturn(CATEGORIA_LIST);
		List<CategoriaRest> response =  categoriaService.listarCategorias();
		assertNotNull(response);
		assertEquals(response.get(0).getId(), CATEGORIA_LIST.get(0).getId());
	}
	
	@Test
	public void listCategoriaByNombreTest() throws BookMarkException {
		
		Mockito.when(categoriaRepository.findByNombre(NOMBRE)).thenReturn(OPTIONAL_CATEGORIA);
		Categoria categoria = categoriaService.findByNombre(NOMBRE);
		assertNotNull(categoria);
		assertEquals(categoria, OPTIONAL_CATEGORIA.get());
	}
	
	@Test(expected = BookMarkException.class)
	public void listCategoriaByNombreNotFoundTest() throws BookMarkException {
		
		Mockito.when(categoriaRepository.findByNombre(NOMBRE)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);
		categoriaService.findByNombre(NOMBRE);
		fail();
	}
	
	@Test
	public void listCategoriaByIdTest() throws BookMarkException {
		
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA);
		CategoriaRest categoria = categoriaService.findById(CATEGORIA_ID);
		assertNotNull(categoria);
	}
	
	@Test(expected = BookMarkException.class)
	public void listCategoriaByIdNotFoundTest() throws BookMarkException {
		
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);
		categoriaService.findById(CATEGORIA_ID);
		fail();
	}
	
	@Test
	public void borrarCategoriaTest() throws BookMarkException {
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA);
		doNothing().when(categoriaRepository).delete(CATEGORIA);
		
		categoriaService.borrarCategoria(CATEGORIA_ID);
	}
	
	@Test(expected = BookMarkException.class)
	public void borrarCategoriaNotFoundCategoriaTest() throws BookMarkException {
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);
		categoriaService.borrarCategoria(CATEGORIA_ID);
		fail();
	}
	
	@Test(expected = BookMarkException.class)
	public void borrarCategoriaInternalErrorCategoriaTest() throws BookMarkException {

		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA);
		Mockito.doThrow(Exception.class).when(categoriaRepository).delete(Mockito.any(Categoria.class));
		categoriaService.borrarCategoria(CATEGORIA_ID);
		fail();
	}
	
}
