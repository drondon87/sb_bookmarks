package com.bookmark.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmark.entities.Capitulo;

public interface ICapituloRepository extends JpaRepository<Capitulo, Long> {

	@Query("select c from Capitulo c where c.libro.id = ?1")
	List<Capitulo> findAllByLibro(Long id);

}
