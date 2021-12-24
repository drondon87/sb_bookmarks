package com.bookmark.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.Capitulo;
import com.bookmark.entities.Categoria;
import com.bookmark.entities.Libro;
import com.bookmark.entities.MarcaLibro;
import com.bookmark.entities.services.impl.MarcaLibroServiceImpl;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateCapituloRest;
import com.bookmark.jsons.CreateMarcaLibroRest;
import com.bookmark.repositories.ICapituloRepository;
import com.bookmark.repositories.IMarcaLibroRepository;

public class MarcaLibroTestCases {

	// Libro
	public static final Long LIBRO_ID = 1L;
	public static final Libro LIBRO = new Libro();
	public static final Optional<Libro> OPTIONAL_LIBRO_EMPTY = Optional.empty();
	public static final Optional<Libro> OPTIONAL_LIBRO = Optional.of(LIBRO);
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";

	// Categoria
	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";

	// Marca Libro
	public static final MarcaLibro MARCA_LIBRO = new MarcaLibro();
	public static final CreateMarcaLibroRest CREATE_MARCA_LIBRO_REST = new CreateMarcaLibroRest();
	public static final String PAGINAS = "37,38,95,84";
	public static final String RESUMEN = "Capitulo sobre las pruebas";
	public static final Long MARCA_LIBRO_ID = 1L;
	public static final List<MarcaLibro> MARCA_LIBRO_LIST = new ArrayList<MarcaLibro>();
	public static final Optional<MarcaLibro> OPTIONAL_MARCA_LIBRO_EMPTY = Optional.empty();
	public static final Optional<MarcaLibro> OPTIONAL_MARCA_LIBRO = Optional.of(MARCA_LIBRO);

	// Capitulo
	public static final Capitulo CAPITULO = new Capitulo();
	public static final CreateCapituloRest CREATE_CAPITULO_REST = new CreateCapituloRest();
	public static final Long CAPITULO_ID = 1L;
	public static final String NOMBRE_CAPITULO = "El Principio";
	public static final String NUMERO_CAPITULO = "1";
	public static final String DESCRIPCION_CAPITULO = "Capitulo de Prueba";
	public static final Optional<Capitulo> OPTIONAL_CAPITULO = Optional.of(CAPITULO);
	public static final Optional<Capitulo> OPTIONAL_CAPITULO_EMPTY = Optional.empty();

	@Mock
	IMarcaLibroRepository marcaLibroRepository;

	@Mock
	ICapituloRepository capituloRepository;

	@InjectMocks
	MarcaLibroServiceImpl underTest;

	@Before
	public void init() throws BookMarkException {
		MockitoAnnotations.initMocks(this);

		// Asigna datos categoria
		CATEGORIA.setId(CATEGORIA_ID);
		CATEGORIA.setNombre(NOMBRE);
		CATEGORIA.setDescripcion(DESCRIPCION);

		// Asignan los datos del libro
		LIBRO.setCategoria(CATEGORIA);
		LIBRO.setCreateAt(DATE_LIBRO);
		LIBRO.setDescripcion(DESCRIPCION_LIBRO);
		LIBRO.setId(LIBRO_ID);
		LIBRO.setNombre(NOMBRE_LIBRO);

		// Asigna los valores del capitulo
		CAPITULO.setId(CAPITULO_ID);
		CAPITULO.setLibro(LIBRO);
		CAPITULO.setDescripcion(DESCRIPCION_CAPITULO);
		CAPITULO.setNombre(NOMBRE_CAPITULO);
		CAPITULO.setNumero(NUMERO_CAPITULO);

		CREATE_MARCA_LIBRO_REST.setCapituloId(CAPITULO_ID);
		CREATE_MARCA_LIBRO_REST.setDescripcion(DESCRIPCION_CAPITULO);
		CREATE_MARCA_LIBRO_REST.setPaginas(PAGINAS);
		CREATE_MARCA_LIBRO_REST.setResumen(RESUMEN);

		// Asigna datos de Marca Libro
		MARCA_LIBRO.setCapitulo(CAPITULO);
		MARCA_LIBRO.setDescripcion(DESCRIPCION_CAPITULO);
		MARCA_LIBRO.setCapitulo(CAPITULO);
		MARCA_LIBRO.setPaginas(PAGINAS);
		MARCA_LIBRO.setResumen(RESUMEN);
		MARCA_LIBRO.setId(MARCA_LIBRO_ID);

		MARCA_LIBRO_LIST.add(MARCA_LIBRO);
	}

	@Test
	public void guardarMarcaLibroTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO);
		Mockito.when(marcaLibroRepository.save(Mockito.any(MarcaLibro.class))).thenReturn(MARCA_LIBRO);

		// when
		MarcaLibro result = underTest.guardar(CREATE_MARCA_LIBRO_REST);

		// then
		assertNotNull(result);
		assertEquals(CREATE_MARCA_LIBRO_REST.getCapituloId(), result.getCapitulo().getId());

	}

	@Test(expected = BookMarkException.class)
	public void guardarMarcaLibroNoExisteTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO_EMPTY);

		// when
		underTest.guardar(CREATE_MARCA_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_MARCA_LIBRO_REST)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.MARCA_LIBRO_NOT_FOUND);

		verify(marcaLibroRepository, never()).save(any());
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void guardarMarcaLibroInternalServer() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO);
		Mockito.doThrow(Exception.class).when(marcaLibroRepository).save(Mockito.any(MarcaLibro.class));

		underTest.guardar(CREATE_MARCA_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_MARCA_LIBRO_REST)).isInstanceOf(Exception.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);

		fail();

	}

	@Test
	public void listarMarcasByLibroTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findAllMarcasByLibro(LIBRO_ID)).thenReturn(MARCA_LIBRO_LIST);

		// when
		List<MarcaLibro> response = underTest.listarMarcasByLibro(LIBRO_ID);

		// then
		assertNotNull(response);
		assertEquals(response.get(0).getId(), MARCA_LIBRO_LIST.get(0).getId());
	}

	@Test
	public void listarMarcasLibroByIdTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findById(MARCA_LIBRO_ID)).thenReturn(OPTIONAL_MARCA_LIBRO);

		// when
		MarcaLibro response = underTest.findById(MARCA_LIBRO_ID);

		// then
		assertNotNull(response);
	}

	@Test(expected = BookMarkException.class)
	public void listMarcaLibroByIdNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findById(MARCA_LIBRO_ID)).thenReturn(OPTIONAL_MARCA_LIBRO_EMPTY);

		// when
		underTest.findById(MARCA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.findById(MARCA_LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.MARCA_LIBRO_NOT_FOUND);

		fail();
	}

	@Test
	public void borrarMarcaLibroTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findById(MARCA_LIBRO_ID)).thenReturn(OPTIONAL_MARCA_LIBRO);
		doNothing().when(marcaLibroRepository).delete(MARCA_LIBRO);

		// when
		underTest.borrarMarcaLibro(MARCA_LIBRO_ID);
	}

	@Test(expected = BookMarkException.class)
	public void borrarMarcaLibroNotFoundLibroTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findById(MARCA_LIBRO_ID)).thenReturn(OPTIONAL_MARCA_LIBRO_EMPTY);

		// when
		underTest.borrarMarcaLibro(MARCA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarMarcaLibro(MARCA_LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.MARCA_LIBRO_NOT_FOUND);

		verify(marcaLibroRepository, never()).delete(any());
		fail();
	}

	@Test(expected = BookMarkException.class)
	public void borrarMarcaLibroInternalErrorTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findById(MARCA_LIBRO_ID)).thenReturn(OPTIONAL_MARCA_LIBRO);
		Mockito.doThrow(Exception.class).when(marcaLibroRepository).delete(Mockito.any(MarcaLibro.class));

		// when
		underTest.borrarMarcaLibro(MARCA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarMarcaLibro(MARCA_LIBRO_ID)).isInstanceOf(InternalServerException.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);
		fail();
	}
	
	@Test
	public void listarMarcasByCapituloTest() throws BookMarkException {

		// given
		Mockito.when(marcaLibroRepository.findAllMarcasByCapitulo(CAPITULO_ID)).thenReturn(MARCA_LIBRO_LIST);

		// when
		List<MarcaLibro> response = underTest.listarMarcasByCapitulo(CAPITULO_ID);

		// then
		assertNotNull(response);
		assertEquals(response.get(0).getId(), MARCA_LIBRO_LIST.get(0).getId());
	}
	
	
}
