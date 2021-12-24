package com.bookmark.entities.services;

import java.util.List;

import com.bookmark.entities.Categoria;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CategoriaRest;
import com.bookmark.jsons.CreateCategoriaRest;

public interface ICategoriaService {
	
	public Categoria guardar(CreateCategoriaRest createCategoriaRest) throws BookMarkException;
	
	public List<CategoriaRest> listarCategorias() throws BookMarkException;
	
	public Categoria findByNombre(String nombre) throws BookMarkException;
	
	public void borrarCategoria(Long id) throws BookMarkException;
	
	public CategoriaRest findById(Long id) throws BookMarkException;

}
