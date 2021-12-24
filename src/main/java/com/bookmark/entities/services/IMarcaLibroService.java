package com.bookmark.entities.services;

import java.util.List;

import com.bookmark.entities.MarcaLibro;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateMarcaLibroRest;

public interface IMarcaLibroService {

	public MarcaLibro guardar(CreateMarcaLibroRest createCitaLibroRest) throws BookMarkException;

	public List<MarcaLibro> listarMarcasByLibro(Long idLibro) throws BookMarkException;

	public void borrarMarcaLibro(Long id) throws BookMarkException;

	public MarcaLibro findById(Long id) throws BookMarkException;
	
	public List<MarcaLibro> listarMarcasByCapitulo(Long idLibro) throws BookMarkException;
}
