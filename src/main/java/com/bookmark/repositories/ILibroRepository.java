package com.bookmark.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmark.entities.Libro;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

	@Query("select l from Libro l where l.categoria.id = ?1")
	List<Libro> findAllByCategoria(Long id);

	Optional<Libro> findByNombre(String nombre);
}
