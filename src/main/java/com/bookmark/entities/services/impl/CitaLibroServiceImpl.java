package com.bookmark.entities.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.CitaLibro;
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ICitaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateCitaLibroRest;
import com.bookmark.repositories.ICitaLibroRepository;
import com.bookmark.repositories.ILibroRepository;

@Service
public class CitaLibroServiceImpl implements ICitaLibroService {

	private static final Logger log = LoggerFactory.getLogger(CitaLibroServiceImpl.class);

	@Autowired
	private ICitaLibroRepository citalibroRepository;

	@Autowired
	private ILibroRepository libroRepository;

	@Override
	public CitaLibro guardar(CreateCitaLibroRest createCitaLibroRest) throws BookMarkException {

		Libro libro = libroRepository.findById(createCitaLibroRest.getLibroId()).orElseThrow(
				() -> new NotFoundException(ResponseConstants.LIBRO_NOT_FOUND, ResponseConstants.LIBRO_NOT_FOUND));

		CitaLibro citaLibro = new CitaLibro();
		citaLibro.setAutor(createCitaLibroRest.getAutor());
		citaLibro.setDescripcion(createCitaLibroRest.getDescripcion());
		citaLibro.setCreateAt(createCitaLibroRest.getCreateAt());
		citaLibro.setPagina(createCitaLibroRest.getPagina());
		citaLibro.setLibro(libro);

		try {

			citaLibro = citalibroRepository.save(citaLibro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

		return citaLibro;
	}

	@Override
	public List<CitaLibro> listarCitasByLibro(Long idLibro) throws BookMarkException {
		return citalibroRepository.findAllCitasByLibro(idLibro);
	}

	@Override
	public void borrarCitaLibro(Long id) throws BookMarkException {
		CitaLibro citalibro = citalibroRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CITA_LIBRO_NOT_FOUND,
						ResponseConstants.CITA_LIBRO_NOT_FOUND));
		try {
			citalibroRepository.delete(citalibro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public CitaLibro findById(Long id) throws BookMarkException {
		return citalibroRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CITA_LIBRO_NOT_FOUND,
						ResponseConstants.CITA_LIBRO_NOT_FOUND));
	}

}
