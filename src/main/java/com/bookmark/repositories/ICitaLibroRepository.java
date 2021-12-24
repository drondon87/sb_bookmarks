package com.bookmark.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmark.entities.CitaLibro;

public interface ICitaLibroRepository extends JpaRepository<CitaLibro, Long>{

	@Query("select c from CitaLibro c where c.libro.id = ?1")
	List<CitaLibro> findAllCitasByLibro(Long id);

}
