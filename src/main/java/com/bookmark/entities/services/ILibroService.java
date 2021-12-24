package com.bookmark.entities.services;

import java.util.List;

import com.bookmark.entities.Libro;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateLibroRest;

public interface ILibroService {

	public Libro guardar(CreateLibroRest createLibroRest) throws BookMarkException;

	public List<Libro> listarLibrosByCategoria(Long idCategoria) throws BookMarkException;

	public void borrarLibro(Long id) throws BookMarkException;

	public Libro findById(Long id) throws BookMarkException;

	public Libro findByNombre(String nombre) throws BookMarkException;

	public List<Libro> listarLibros() throws BookMarkException;

}
