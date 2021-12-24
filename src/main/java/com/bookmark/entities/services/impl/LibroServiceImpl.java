package com.bookmark.entities.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.Categoria;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ILibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.exceptions.RegistroExistenteException;
import com.bookmark.jsons.CreateLibroRest;
import com.bookmark.repositories.ICategoriaRepository;
import com.bookmark.repositories.ILibroRepository;

@Service
public class LibroServiceImpl implements ILibroService {

	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

	@Autowired
	private ILibroRepository libroRepository;

	@Autowired
	private ICategoriaRepository categoriaRepository;

	@Override
	public Libro guardar(CreateLibroRest createLibroRest) throws BookMarkException {
		Optional<Libro> libroFound = libroRepository.findByNombre(createLibroRest.getNombre());

		if (libroFound.isPresent()) {
			throw new RegistroExistenteException(ResponseConstants.LIBRO_ALREADY_EXIST,
					ResponseConstants.LIBRO_ALREADY_EXIST);
		}

		Categoria categoria = categoriaRepository.findById(createLibroRest.getCategoriaId())
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CATEGORIA_NOT_FOUND,
						ResponseConstants.CATEGORIA_NOT_FOUND));

		Libro libro = new Libro();
		libro.setNombre(createLibroRest.getNombre().toUpperCase());
		libro.setDescripcion(createLibroRest.getDescripcion());
		libro.setCreateAt(createLibroRest.getCreateAt());
		libro.setCategoria(categoria);
		libro.setAutor(createLibroRest.getAutor());

		try {

			libro = libroRepository.save(libro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

		return libro;
	}

	@Override
	public List<Libro> listarLibrosByCategoria(Long idCategoria) throws BookMarkException {
		return libroRepository.findAllByCategoria(idCategoria);
	}

	@Override
	public void borrarLibro(Long id) throws BookMarkException {
		Libro libro = libroRepository.findById(id).orElseThrow(
				() -> new NotFoundException(ResponseConstants.LIBRO_NOT_FOUND, ResponseConstants.LIBRO_NOT_FOUND));

		try {

			libroRepository.delete(libro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Libro findById(Long id) throws BookMarkException {
		return libroRepository.findById(id).orElseThrow(
				() -> new NotFoundException(ResponseConstants.LIBRO_NOT_FOUND, ResponseConstants.LIBRO_NOT_FOUND));
	}

	@Override
	public Libro findByNombre(String nombre) throws BookMarkException {
		return libroRepository.findByNombre(nombre).orElseThrow(
				() -> new NotFoundException(ResponseConstants.LIBRO_NOT_FOUND, ResponseConstants.LIBRO_NOT_FOUND));
	}

	@Override
	public List<Libro> listarLibros() throws BookMarkException {
		return libroRepository.findAll();
	}

}
