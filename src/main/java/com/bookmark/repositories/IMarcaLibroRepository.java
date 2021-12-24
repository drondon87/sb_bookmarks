package com.bookmark.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmark.entities.MarcaLibro;

public interface IMarcaLibroRepository extends JpaRepository<MarcaLibro, Long> {

	@Query("select ml from MarcaLibro ml where ml.capitulo.libro.id = ?1")
	List<MarcaLibro> findAllMarcasByLibro(Long id);
	
	@Query("select ml from MarcaLibro ml where ml.capitulo.id = ?1")
	List<MarcaLibro> findAllMarcasByCapitulo(Long id);
}
