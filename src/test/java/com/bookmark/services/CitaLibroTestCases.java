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
import com.bookmark.entities.Categoria;
import com.bookmark.entities.CitaLibro;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.impl.CitaLibroServiceImpl;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateCitaLibroRest;
import com.bookmark.repositories.ICitaLibroRepository;
import com.bookmark.repositories.ILibroRepository;

public class CitaLibroTestCases {

	// Libro
	public static final Long LIBRO_ID = 1L;
	public static final Libro LIBRO = new Libro();
	public static final Optional<Libro> OPTIONAL_LIBRO_EMPTY = Optional.empty();
	public static final Optional<Libro> OPTIONAL_LIBRO = Optional.of(LIBRO);
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";

	// Cita Libro
	public static final CitaLibro CITA_LIBRO = new CitaLibro();
	public static final Long CITA_LIBRO_ID = 1L;
	public static final String AUTOR_CITA = "William Shakespear";
	public static final Date DATE_CITA = new Date();
	public static final String DESCRIPCION_CITA = "Un grande puede hacerse mas pequeño";
	public static final CreateCitaLibroRest CREATE_CITA_LIBRO_REST = new CreateCitaLibroRest();
	public static final List<CitaLibro> CITA_LIBRO_LIST = new ArrayList<>();
	public static final Optional<CitaLibro> OPTIONAL_CITA_LIBRO = Optional.of(CITA_LIBRO);
	public static final Optional<CitaLibro> OPTIONAL_CITA_LIBRO_EMPTY = Optional.empty();

	// Categoria
	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";

	@Mock
	ICitaLibroRepository citalibroRepository;

	@Mock
	ILibroRepository libroRepository;

	@InjectMocks
	CitaLibroServiceImpl underTest;

	@Before
	public void init() throws BookMarkException {
		MockitoAnnotations.initMocks(this);

		// Datos create cita libro
		CREATE_CITA_LIBRO_REST.setAutor(AUTOR_CITA);
		CREATE_CITA_LIBRO_REST.setCreateAt(DATE_CITA);
		CREATE_CITA_LIBRO_REST.setDescripcion(DESCRIPCION_CITA);
		CREATE_CITA_LIBRO_REST.setLibroId(LIBRO_ID);

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

		// Datos de la Cita del Libro
		CITA_LIBRO.setId(CITA_LIBRO_ID);
		CITA_LIBRO.setAutor(AUTOR_CITA);
		CITA_LIBRO.setCreateAt(DATE_CITA);
		CITA_LIBRO.setDescripcion(DESCRIPCION_CITA);
		CITA_LIBRO.setLibro(LIBRO);

		CITA_LIBRO_LIST.add(CITA_LIBRO);

	}

	@Test
	public void guardarCitaLibroTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		Mockito.when(citalibroRepository.save(Mockito.any(CitaLibro.class))).thenReturn(CITA_LIBRO);

		// when
		CitaLibro result = underTest.guardar(CREATE_CITA_LIBRO_REST);

		// then
		assertNotNull(result);
		assertEquals(CREATE_CITA_LIBRO_REST.getAutor(), result.getAutor());

	}

	@Test(expected = BookMarkException.class)
	public void guardarCitaLibroNoExisteTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO_EMPTY);

		// when
		underTest.guardar(CREATE_CITA_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_CITA_LIBRO_REST)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.LIBRO_NOT_FOUND);

		verify(citalibroRepository, never()).save(any());
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void guardarCitaLibroInternalServer() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		Mockito.doThrow(Exception.class).when(citalibroRepository).save(Mockito.any(CitaLibro.class));

		underTest.guardar(CREATE_CITA_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_CITA_LIBRO_REST)).isInstanceOf(Exception.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);

		fail();

	}

	@Test
	public void listarCitasByLibroTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findAllCitasByLibro(LIBRO_ID)).thenReturn(CITA_LIBRO_LIST);

		// when
		List<CitaLibro> response = underTest.listarCitasByLibro(LIBRO_ID);

		// then
		assertNotNull(response);
		assertEquals(response.get(0).getId(), CITA_LIBRO_LIST.get(0).getId());
	}

	@Test
	public void listarCitasLibroByIdTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findById(CITA_LIBRO_ID)).thenReturn(OPTIONAL_CITA_LIBRO);

		// when
		CitaLibro response = underTest.findById(CITA_LIBRO_ID);

		// then
		assertNotNull(response);
	}

	@Test(expected = BookMarkException.class)
	public void listCitaLibroByIdNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findById(CITA_LIBRO_ID)).thenReturn(OPTIONAL_CITA_LIBRO_EMPTY);

		// when
		underTest.findById(CITA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.findById(CITA_LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CITA_LIBRO_NOT_FOUND);

		fail();
	}

	@Test
	public void borrarCitaLibroTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findById(CITA_LIBRO_ID)).thenReturn(OPTIONAL_CITA_LIBRO);
		doNothing().when(citalibroRepository).delete(CITA_LIBRO);

		// when
		underTest.borrarCitaLibro(CITA_LIBRO_ID);
	}

	@Test(expected = BookMarkException.class)
	public void borrarCitaLibroNotFoundLibroTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findById(CITA_LIBRO_ID)).thenReturn(OPTIONAL_CITA_LIBRO_EMPTY);

		// when
		underTest.borrarCitaLibro(CITA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarCitaLibro(CITA_LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CITA_LIBRO_NOT_FOUND);

		verify(citalibroRepository, never()).delete(any());
		fail();
	}

	@Test(expected = BookMarkException.class)
	public void borrarCitaLibroInternalErrorTest() throws BookMarkException {

		// given
		Mockito.when(citalibroRepository.findById(CITA_LIBRO_ID)).thenReturn(OPTIONAL_CITA_LIBRO);
		Mockito.doThrow(Exception.class).when(citalibroRepository).delete(Mockito.any(CitaLibro.class));

		// when
		underTest.borrarCitaLibro(CITA_LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarCitaLibro(CITA_LIBRO_ID)).isInstanceOf(InternalServerException.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);
		fail();
	}

}
