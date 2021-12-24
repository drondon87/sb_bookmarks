package com.bookmark.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmark.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNombre(String nombre);

}
