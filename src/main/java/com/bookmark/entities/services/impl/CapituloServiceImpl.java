package com.bookmark.entities.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.Capitulo;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ICapituloService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateCapituloRest;
import com.bookmark.repositories.ICapituloRepository;
import com.bookmark.repositories.ILibroRepository;

@Service
public class CapituloServiceImpl implements ICapituloService {

	private static final Logger log = LoggerFactory.getLogger(CapituloServiceImpl.class);

	@Autowired
	private ILibroRepository libroRepository;

	@Autowired
	private ICapituloRepository capituloRepository;

	@Override
	public Capitulo guardar(CreateCapituloRest createCapituloRest) throws BookMarkException {

		Libro libro = libroRepository.findById(createCapituloRest.getLibroId()).orElseThrow(
				() -> new NotFoundException(ResponseConstants.LIBRO_NOT_FOUND, ResponseConstants.LIBRO_NOT_FOUND));

		Capitulo capitulo = new Capitulo();
		capitulo.setDescripcion(createCapituloRest.getDescripcion());
		capitulo.setNombre(createCapituloRest.getNombre());
		capitulo.setNumero(createCapituloRest.getNumero());
		capitulo.setLibro(libro);

		try {

			capitulo = capituloRepository.save(capitulo);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

		return capitulo;
	}

	@Override
	public List<Capitulo> listarCapitulosByLibro(Long idLibro) throws BookMarkException {
		return capituloRepository.findAllByLibro(idLibro);
	}

	@Override
	public void borrarCapitulo(Long id) throws BookMarkException {
		Capitulo capitulo = capituloRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CAPITULO_NOT_FOUND,
						ResponseConstants.CAPITULO_NOT_FOUND));

		try {

			capituloRepository.delete(capitulo);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public Capitulo findById(Long id) throws BookMarkException {
		return capituloRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CAPITULO_NOT_FOUND,
						ResponseConstants.CAPITULO_NOT_FOUND));
	}

}
