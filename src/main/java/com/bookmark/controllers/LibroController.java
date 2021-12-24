package com.bookmark.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.bookmark.entities.Libro;
import com.bookmark.entities.services.ILibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.BusquedaRest;
import com.bookmark.jsons.CreateLibroRest;
import com.bookmark.response.BookmarkResponse;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class LibroController {

	@Autowired
	private ILibroService libroService;

	@GetMapping("/libro")
	public BookmarkResponse<List<Libro>> listarLibros() throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				libroService.listarLibros());
	}

	@PostMapping("/libro")
	public BookmarkResponse<Libro> crearLibro(@RequestBody CreateLibroRest crearLibroRest) throws BookMarkException {

		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date dateFormateada = sourceFormat.parse(crearLibroRest.getFechaLibro());
			crearLibroRest.setCreateAt(dateFormateada);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new BookmarkResponse<Libro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, libroService.guardar(crearLibroRest));
	}

	@GetMapping("/libro/categoria/{idCategoria}")
	public BookmarkResponse<List<Libro>> listarLibrosPorCategoria(@PathVariable Long idCategoria)
			throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				libroService.listarLibrosByCategoria(idCategoria));
	}

	@GetMapping("/libro/nombre")
	public BookmarkResponse<Libro> buscarLibroPorNombre(@RequestBody BusquedaRest busquedaRest)
			throws BookMarkException {
		return new BookmarkResponse<Libro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, libroService.findByNombre(busquedaRest.getNombre()));
	}

	@GetMapping("/libro/{id}")
	public BookmarkResponse<Libro> buscarLibroPorId(@PathVariable Long id) throws BookMarkException {
		return new BookmarkResponse<Libro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, libroService.findById(id));
	}

	@DeleteMapping("/libro/{id}")
	public BookmarkResponse<?> borrarLibro(@PathVariable Long id) throws BookMarkException {

		libroService.borrarLibro(id);

		BookmarkResponse<?> mensaje = new BookmarkResponse(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				"Libro Eliminado Exitosamente!!!");

		return mensaje;

	}

}
