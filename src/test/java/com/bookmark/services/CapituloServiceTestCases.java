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
import com.bookmark.entities.services.impl.CapituloServiceImpl;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateCapituloRest;
import com.bookmark.repositories.ICapituloRepository;
import com.bookmark.repositories.ILibroRepository;

public class CapituloServiceTestCases {

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

	// Capitulo
	public static final Capitulo CAPITULO = new Capitulo();
	public static final Long CAPITULO_ID = 1L;
	public static final String NUMERO_CAPITULO = "1";
	public static final CreateCapituloRest CREATE_CAPITULO_REST = new CreateCapituloRest();
	public static final String DESCRIPCION_CAPITULO = "Capitulo de Prueba";
	public static final List<Capitulo> CAPITULO_LIST = new ArrayList<Capitulo>();
	public static final String NOMBRE_CAPITULO = "Capitulo de Prueba";
	public static final Optional<Capitulo> OPTIONAL_CAPITULO = Optional.of(CAPITULO);
	public static final Optional<Capitulo> OPTIONAL_CAPITULO_EMPTY = Optional.empty();

	@Mock
	ICapituloRepository capituloRepository;

	@Mock
	ILibroRepository libroRepository;

	@InjectMocks
	CapituloServiceImpl underTest;

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
		CAPITULO.setNombre(NOMBRE_CAPITULO);
		CAPITULO.setNumero(NUMERO_CAPITULO);

		CREATE_CAPITULO_REST.setDescripcion(DESCRIPCION_CAPITULO);
		CREATE_CAPITULO_REST.setLibroId(LIBRO_ID);
		CREATE_CAPITULO_REST.setNombre(NOMBRE_CAPITULO);
		CREATE_CAPITULO_REST.setNumero(NUMERO_CAPITULO);

		CAPITULO_LIST.add(CAPITULO);
	}

	@Test
	public void guardarCapituloTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		Mockito.when(capituloRepository.save(Mockito.any(Capitulo.class))).thenReturn(CAPITULO);

		// when
		Capitulo result = underTest.guardar(CREATE_CAPITULO_REST);

		// then
		assertNotNull(result);
		assertEquals(CREATE_CAPITULO_REST.getNombre(), result.getNombre());

	}

	@Test(expected = BookMarkException.class)
	public void guardarCapituloLibroNoExisteTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO_EMPTY);

		// when
		underTest.guardar(CREATE_CAPITULO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_CAPITULO_REST)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CAPITULO_NOT_FOUND);

		verify(capituloRepository, never()).save(any());
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void guardarCapituloInternalServer() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		Mockito.doThrow(Exception.class).when(capituloRepository).save(Mockito.any(Capitulo.class));

		underTest.guardar(CREATE_CAPITULO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_CAPITULO_REST)).isInstanceOf(Exception.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);

		fail();

	}

	@Test
	public void listarCapitulosByLibroTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findAllByLibro(LIBRO_ID)).thenReturn(CAPITULO_LIST);

		// when
		List<Capitulo> response = underTest.listarCapitulosByLibro(LIBRO_ID);

		// then
		assertNotNull(response);
		assertEquals(response.get(0).getId(), CAPITULO_LIST.get(0).getId());
	}

	@Test
	public void borrarCapituloTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO);
		doNothing().when(capituloRepository).delete(CAPITULO);

		// when
		underTest.borrarCapitulo(CAPITULO_ID);
	}

	@Test(expected = BookMarkException.class)
	public void borrarCapituloNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO_EMPTY);

		// when
		underTest.borrarCapitulo(CAPITULO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarCapitulo(CAPITULO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CAPITULO_NOT_FOUND);

		verify(capituloRepository, never()).delete(any());
		fail();
	}

	@Test(expected = BookMarkException.class)
	public void borrarCapituloInternalErrorTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO);
		Mockito.doThrow(Exception.class).when(capituloRepository).delete(Mockito.any(Capitulo.class));

		// when
		underTest.borrarCapitulo(CAPITULO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarCapitulo(CAPITULO_ID)).isInstanceOf(InternalServerException.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);
		fail();
	}

	@Test
	public void listarCapituloByIdTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO);

		// when
		Capitulo response = underTest.findById(CAPITULO_ID);

		// then
		assertNotNull(response);
	}

	@Test(expected = BookMarkException.class)
	public void listCapituloByIdNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(capituloRepository.findById(CAPITULO_ID)).thenReturn(OPTIONAL_CAPITULO_EMPTY);

		// when
		underTest.findById(CAPITULO_ID);

		// then
		assertThatThrownBy(() -> underTest.findById(CAPITULO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CAPITULO_NOT_FOUND);

		fail();
	}
}
