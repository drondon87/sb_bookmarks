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
import com.bookmark.entities.Capitulo;
import com.bookmark.entities.services.ICapituloService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCapituloRest;
import com.bookmark.response.BookmarkResponse;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class CapituloController {

	@Autowired
	private ICapituloService capituloService;

	@PostMapping("/capitulo")
	public BookmarkResponse<Capitulo> crearCapitulo(@RequestBody CreateCapituloRest crearCapituloRest)
			throws BookMarkException {

		return new BookmarkResponse<Capitulo>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, capituloService.guardar(crearCapituloRest));
	}

	@GetMapping("/capitulo/libro/{idLibro}")
	public BookmarkResponse<List<Capitulo>> listarCapitulosPorLibro(@PathVariable Long idLibro)
			throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				capituloService.listarCapitulosByLibro(idLibro));
	}

	@GetMapping("/capitulo/{id}")
	public BookmarkResponse<Capitulo> buscarCapituloPorId(@PathVariable Long id) throws BookMarkException {
		return new BookmarkResponse<Capitulo>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, capituloService.findById(id));
	}

	@DeleteMapping("/capitulo/{id}")
	public BookmarkResponse<?> borrarCapitulo(@PathVariable Long id) throws BookMarkException {

		capituloService.borrarCapitulo(id);

		BookmarkResponse<?> mensaje = new BookmarkResponse(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				"Capitulo Eliminado Exitosamente!!!");

		return mensaje;

	}

}
