package com.bookmark.entities.services;

import java.util.List;

import com.bookmark.entities.Capitulo;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCapituloRest;

public interface ICapituloService {

	public Capitulo guardar(CreateCapituloRest createCapituloRest) throws BookMarkException;

	public List<Capitulo> listarCapitulosByLibro(Long idLibro) throws BookMarkException;

	public void borrarCapitulo(Long id) throws BookMarkException;

	public Capitulo findById(Long id) throws BookMarkException;

}
