package com.bookmark.entities.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.entities.Categoria;
import com.bookmark.entities.services.ICategoriaService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.exceptions.InternalServerException;
import com.bookmark.exceptions.NotFoundException;
import com.bookmark.exceptions.RegistroExistenteException;
import com.bookmark.jsons.CategoriaRest;
import com.bookmark.jsons.CreateCategoriaRest;
import com.bookmark.repositories.ICategoriaRepository;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private ICategoriaRepository categoriaRepository;

	@Override
	public Categoria guardar(CreateCategoriaRest createCategoriaRest) throws BookMarkException {
		
		Optional<Categoria> categoriaFind = categoriaRepository.findByNombre(createCategoriaRest.getNombre());

		if (categoriaFind.isPresent()) {
			throw new RegistroExistenteException("CATEGORIA_ALREADY_EXIST", "CATEGORIA_ALREADY_EXIST");
		}

		Categoria categoria = new Categoria();
		categoria.setNombre(createCategoriaRest.getNombre().toUpperCase());
		categoria.setDescripcion(createCategoriaRest.getDescripcion());

		try {

			categoria = categoriaRepository.save(categoria);

		} catch (final Exception e) {
			log.error("INTERNAL SERVER ERROR");
			throw new InternalServerException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		return categoria;
	}

	@Override
	public List<CategoriaRest> listarCategorias() throws BookMarkException {

		final List<Categoria> categorias = categoriaRepository.findAll();

		return categorias.stream().map(service -> modelMapper.map(service, CategoriaRest.class))
				.collect(Collectors.toList());
	}

	@Override
	public Categoria findByNombre(String nombre) throws BookMarkException {
		return categoriaRepository.findByNombre(nombre)
				.orElseThrow(() -> new NotFoundException("CATEGORIA_NOT_FOUND", "CATEGORIA_NOT_FOUND"));
	}

	@Override
	public void borrarCategoria(Long id) throws BookMarkException {

		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("CATEGORIA_NOT_FOUND", "CATEGORIA_NOT_FOUND"));

		try {

			categoriaRepository.delete(categoria);

		} catch (final Exception e) {
			log.error("INTERNAL SERVER ERROR");
			throw new InternalServerException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

	}

	@Override
	public CategoriaRest findById(Long id) throws BookMarkException {

		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("CATEGORIA_NOT_FOUND", "CATEGORIA_NOT_FOUND"));

		return modelMapper.map(categoria, CategoriaRest.class);
	}

}
