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
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.impl.LibroServiceImpl;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.exceptions.RegistroExistenteException;
import com.bookmark.jsons.CreateLibroRest;
import com.bookmark.repositories.ICategoriaRepository;
import com.bookmark.repositories.ILibroRepository;

public class LibroServiceTestCases {

	public static final Optional<Libro> OPTIONAL_LIBRO_EMPTY = Optional.empty();
	public static final Libro LIBRO = new Libro();
	public static final Long LIBRO_ID = 1L;
	public static final String NOMBRE_LIBRO = "El Traidor";
	public static final Date DATE_LIBRO = new Date();
	public static final String DESCRIPCION_LIBRO = "Libro de crimen y politica mexicana";
	public static final CreateLibroRest CREATE_LIBRO_REST = new CreateLibroRest();
	public static final String AUTOR_LIBRO = "Domingo Rondon";
	public static final Optional<Libro> OPTIONAL_LIBRO = Optional.of(LIBRO);
	public static final List<Libro> LIBRO_LIST = new ArrayList<>();

	public static final Categoria CATEGORIA = new Categoria();
	public static final Long CATEGORIA_ID = 1L;
	public static final String NOMBRE = "Historia";
	public static final String DESCRIPCION = "Libro de Historia";
	public static final Optional<Categoria> OPTIONAL_CATEGORIA = Optional.of(CATEGORIA);
	public static final Optional<Categoria> OPTIONAL_CATEGORIA_EMPTY = Optional.empty();

	@Mock
	ILibroRepository libroRepository;

	@Mock
	ICategoriaRepository categoriaRepository;

	@InjectMocks
	LibroServiceImpl underTest;

	@Before
	public void init() throws BookMarkException {
		MockitoAnnotations.initMocks(this);

		// Asigna datos categoria
		CATEGORIA.setId(CATEGORIA_ID);
		CATEGORIA.setNombre(NOMBRE);
		CATEGORIA.setDescripcion(DESCRIPCION);

		// Asigna datos del create libro para el guardado
		CREATE_LIBRO_REST.setNombre(NOMBRE_LIBRO);
		CREATE_LIBRO_REST.setCategoriaId(CATEGORIA_ID);
		CREATE_LIBRO_REST.setCreateAt(DATE_LIBRO);
		CREATE_LIBRO_REST.setDescripcion(DESCRIPCION_LIBRO);
		CREATE_LIBRO_REST.setAutor(AUTOR_LIBRO);

		// Asignan los datos del libro
		LIBRO.setCategoria(CATEGORIA);
		LIBRO.setCreateAt(DATE_LIBRO);
		LIBRO.setDescripcion(DESCRIPCION_LIBRO);
		LIBRO.setId(LIBRO_ID);
		LIBRO.setNombre(NOMBRE_LIBRO);
		LIBRO.setAutor(AUTOR_LIBRO);

		LIBRO_LIST.add(LIBRO);
	}

	@Test
	public void guardarLibroTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO_EMPTY);
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA);
		Mockito.when(libroRepository.save(Mockito.any(Libro.class))).thenReturn(LIBRO);

		// when
		Libro result = underTest.guardar(CREATE_LIBRO_REST);

		// then
		assertNotNull(result);
		assertEquals(CREATE_LIBRO_REST.getNombre(), result.getNombre());

	}

	@Test(expected = BookMarkException.class)
	public void guardarLibroNombreExistenteTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO);

		// when
		underTest.guardar(CREATE_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_LIBRO_REST)).isInstanceOf(RegistroExistenteException.class)
				.hasMessageContaining(ResponseConstants.LIBRO_ALREADY_EXIST);

		verify(libroRepository, never()).save(any());
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void guardarLibroCategoriaNoExisteTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO_EMPTY);
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA_EMPTY);

		// when
		underTest.guardar(CREATE_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_LIBRO_REST)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.CATEGORIA_NOT_FOUND);

		verify(libroRepository, never()).save(any());
		fail();

	}

	@Test(expected = BookMarkException.class)
	public void guardarLibroCategoriaInternalServer() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO_EMPTY);
		Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(OPTIONAL_CATEGORIA);
		Mockito.doThrow(Exception.class).when(libroRepository).save(Mockito.any(Libro.class));

		underTest.guardar(CREATE_LIBRO_REST);

		// then
		assertThatThrownBy(() -> underTest.guardar(CREATE_LIBRO_REST)).isInstanceOf(Exception.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);

		fail();

	}

	@Test
	public void listarLibrosByCategoriaTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findAllByCategoria(CATEGORIA_ID)).thenReturn(LIBRO_LIST);

		// when
		List<Libro> response = underTest.listarLibrosByCategoria(CATEGORIA_ID);

		// then
		assertNotNull(response);
		assertEquals(response.get(0).getId(), LIBRO_LIST.get(0).getId());
	}

	@Test
	public void listarLibroByIdTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);

		// when
		Libro response = underTest.findById(LIBRO_ID);

		// then
		assertNotNull(response);
	}

	@Test(expected = BookMarkException.class)
	public void listLibroByIdNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO_EMPTY);

		// when
		underTest.findById(LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.findById(LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.LIBRO_NOT_FOUND);

		fail();
	}

	@Test
	public void listarLibroByNombreTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO);

		// when
		Libro response = underTest.findByNombre(NOMBRE_LIBRO);

		// then
		assertNotNull(response);
	}

	@Test(expected = BookMarkException.class)
	public void listLibroByNombreNotFoundTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findByNombre(NOMBRE_LIBRO)).thenReturn(OPTIONAL_LIBRO_EMPTY);

		// when
		underTest.findByNombre(NOMBRE_LIBRO);

		// then
		assertThatThrownBy(() -> underTest.findByNombre(NOMBRE_LIBRO)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.LIBRO_NOT_FOUND);

		fail();
	}

	@Test
	public void listarLibrosTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findAll()).thenReturn(LIBRO_LIST);

		// when
		List<Libro> response = underTest.listarLibros();

		// then
		assertNotNull(response);
	}

	@Test
	public void borrarLibroTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		doNothing().when(libroRepository).delete(LIBRO);

		// when
		underTest.borrarLibro(LIBRO_ID);
	}

	@Test(expected = BookMarkException.class)
	public void borrarLibroNotFoundLibroTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO_EMPTY);

		// when
		underTest.borrarLibro(LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarLibro(LIBRO_ID)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining(ResponseConstants.LIBRO_NOT_FOUND);

		verify(libroRepository, never()).delete(any());
		fail();
	}

	@Test(expected = BookMarkException.class)
	public void borrarLibroInternalErrorCategoriaTest() throws BookMarkException {

		// given
		Mockito.when(libroRepository.findById(LIBRO_ID)).thenReturn(OPTIONAL_LIBRO);
		Mockito.doThrow(Exception.class).when(libroRepository).delete(Mockito.any(Libro.class));

		// when
		underTest.borrarLibro(LIBRO_ID);

		// then
		assertThatThrownBy(() -> underTest.borrarLibro(LIBRO_ID)).isInstanceOf(InternalServerException.class)
				.hasMessageContaining(ResponseConstants.INTERNAL_SERVER_ERROR);
		fail();
	}
}
