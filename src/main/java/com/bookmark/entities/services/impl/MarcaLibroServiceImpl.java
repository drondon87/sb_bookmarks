package com.bookmark.entities.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.Capitulo;
import com.bookmark.entities.MarcaLibro;
import com.bookmark.entities.services.IMarcaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.jsons.CreateMarcaLibroRest;
import com.bookmark.repositories.ICapituloRepository;
import com.bookmark.repositories.IMarcaLibroRepository;

@Service
public class MarcaLibroServiceImpl implements IMarcaLibroService {

	private static final Logger log = LoggerFactory.getLogger(MarcaLibroServiceImpl.class);

	@Autowired
	private IMarcaLibroRepository marcaLibroRepository;

	@Autowired
	private ICapituloRepository capituloRepository;

	@Override
	public MarcaLibro guardar(CreateMarcaLibroRest createMarcaLibroRest) throws BookMarkException {

		Capitulo capitulo = capituloRepository.findById(createMarcaLibroRest.getCapituloId())
				.orElseThrow(() -> new NotFoundException(ResponseConstants.CAPITULO_NOT_FOUND,
						ResponseConstants.CAPITULO_NOT_FOUND));

		MarcaLibro marcaLibro = new MarcaLibro();
		marcaLibro.setCapitulo(capitulo);
		marcaLibro.setDescripcion(createMarcaLibroRest.getDescripcion());
		marcaLibro.setPaginas(createMarcaLibroRest.getPaginas());
		marcaLibro.setResumen(createMarcaLibroRest.getResumen());

		try {

			marcaLibro = marcaLibroRepository.save(marcaLibro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

		return marcaLibro;
	}

	@Override
	public List<MarcaLibro> listarMarcasByLibro(Long idLibro) throws BookMarkException {
		return marcaLibroRepository.findAllMarcasByLibro(idLibro);
	}

	@Override
	public void borrarMarcaLibro(Long id) throws BookMarkException {
		MarcaLibro marcalibro = marcaLibroRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.MARCA_LIBRO_NOT_FOUND,
						ResponseConstants.MARCA_LIBRO_NOT_FOUND));
		try {

			marcaLibroRepository.delete(marcalibro);

		} catch (final Exception e) {
			log.error(ResponseConstants.INTERNAL_SERVER_ERROR);
			throw new InternalServerException(ResponseConstants.INTERNAL_SERVER_ERROR,
					ResponseConstants.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public MarcaLibro findById(Long id) throws BookMarkException {
		return marcaLibroRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseConstants.MARCA_LIBRO_NOT_FOUND,
						ResponseConstants.MARCA_LIBRO_NOT_FOUND));
	}

	@Override
	public List<MarcaLibro> listarMarcasByCapitulo(Long idLibro) throws BookMarkException {
		return marcaLibroRepository.findAllMarcasByCapitulo(idLibro);
	}

}
