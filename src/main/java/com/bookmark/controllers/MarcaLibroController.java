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
import com.bookmark.entities.MarcaLibro;
import com.bookmark.entities.services.IMarcaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateMarcaLibroRest;
import com.bookmark.response.BookmarkResponse;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class MarcaLibroController {

	@Autowired
	private IMarcaLibroService marcaLibroService;

	@PostMapping("/marcaLibro")
	public BookmarkResponse<MarcaLibro> crearMarcaLibro(@RequestBody CreateMarcaLibroRest crearMarcaLibroRest)
			throws BookMarkException {

		return new BookmarkResponse<MarcaLibro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, marcaLibroService.guardar(crearMarcaLibroRest));
	}

	@GetMapping("/marcaLibro/libro/{idLibro}")
	public BookmarkResponse<List<MarcaLibro>> listarMarcasPorLibro(@PathVariable Long idLibro)
			throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				marcaLibroService.listarMarcasByLibro(idLibro));
	}

	@GetMapping("/marcaLibro/capitulo/{idCapitulo}")
	public BookmarkResponse<List<MarcaLibro>> listarMarcasPorCapitulo(@PathVariable Long idCapitulo)
			throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				marcaLibroService.listarMarcasByCapitulo(idCapitulo));
	}

	@DeleteMapping("/marcaLibro/{id}")
	public BookmarkResponse<?> borrarMarcaLibro(@PathVariable Long id) throws BookMarkException {

		marcaLibroService.borrarMarcaLibro(id);

		BookmarkResponse<?> mensaje = new BookmarkResponse(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				"Marca Eliminada Exitosamente!!!");

		return mensaje;

	}

	@GetMapping("/marcaLibro/{id}")
	public BookmarkResponse<MarcaLibro> buscarMarcaLibroPorId(@PathVariable Long id) throws BookMarkException {
		return new BookmarkResponse<MarcaLibro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, marcaLibroService.findById(id));
	}

}
