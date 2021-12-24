package com.bookmark.entities.services;

import java.util.List;

import com.bookmark.entities.CitaLibro;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCitaLibroRest;

public interface ICitaLibroService {

	public CitaLibro guardar(CreateCitaLibroRest createCitaLibroRest) throws BookMarkException;

	public List<CitaLibro> listarCitasByLibro(Long idLibro) throws BookMarkException;

	public void borrarCitaLibro(Long id) throws BookMarkException;

	public CitaLibro findById(Long id) throws BookMarkException;

}
