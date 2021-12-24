package com.bookmark.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmark.constanst.ResponseConstants;
import com.bookmark.entities.Categoria;
import com.bookmark.entities.services.ICategoriaService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CategoriaRest;
import com.bookmark.jsons.CreateCategoriaRest;
import com.bookmark.response.BookmarkResponse;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class CategoriaController {

	@Autowired
	private ICategoriaService categoriaService;

	@PostMapping("/categoria")
	public BookmarkResponse<Categoria> crearCategoria(@RequestBody CreateCategoriaRest crearCategoriaRest)
			throws BookMarkException {
		return new BookmarkResponse<Categoria>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, categoriaService.guardar(crearCategoriaRest));
	}

	@GetMapping("/categoria")
	public BookmarkResponse<List<CategoriaRest>> listarCategorias() throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				categoriaService.listarCategorias());
	}

	@GetMapping("/categoria/nombre/{nombre}")
	public BookmarkResponse<Categoria> buscarPorNombre(@PathVariable String nombre) throws BookMarkException {
		return new BookmarkResponse<Categoria>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, categoriaService.findByNombre(nombre));
	}

	@GetMapping("/categoria/{id}")
	public BookmarkResponse<CategoriaRest> buscarPorId(@PathVariable Long id) throws BookMarkException {
		return new BookmarkResponse<CategoriaRest>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, categoriaService.findById(id));
	}

	@DeleteMapping("/categoria/{id}")
	public BookmarkResponse<?> borrarCategoria(@PathVariable Long id) throws BookMarkException {

		categoriaService.borrarCategoria(id);

		BookmarkResponse<?> mensaje = new BookmarkResponse(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				"Categoria Eliminada Exitosamente!!!");

		return mensaje;

	}

}
