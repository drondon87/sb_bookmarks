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
import com.bookmark.entities.CitaLibro;
import com.bookmark.entities.services.ICitaLibroService;
import com.bookmark.exceptions.BookMarkException;
import com.bookmark.jsons.CreateCitaLibroRest;
import com.bookmark.response.BookmarkResponse;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class CitaLibroController {

	@Autowired
	private ICitaLibroService citaLibroService;

	@PostMapping("/citalibro")
	public BookmarkResponse<CitaLibro> crearCitaLibro(@RequestBody CreateCitaLibroRest crearCitaLibroRest)
			throws BookMarkException {

		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date dateFormateada = sourceFormat.parse(crearCitaLibroRest.getFechaCita());
			crearCitaLibroRest.setCreateAt(dateFormateada);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new BookmarkResponse<CitaLibro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, citaLibroService.guardar(crearCitaLibroRest));
	}

	@GetMapping("/citalibro/libro/{idLibro}")
	public BookmarkResponse<List<CitaLibro>> listarCitasPorLibro(@PathVariable Long idLibro) throws BookMarkException {
		return new BookmarkResponse<>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK), ResponseConstants.OK,
				citaLibroService.listarCitasByLibro(idLibro));
	}

	@DeleteMapping("/citalibro/{id}")
	public BookmarkResponse<?> borrarCitaLibro(@PathVariable Long id) throws BookMarkException {

		citaLibroService.borrarCitaLibro(id);

		BookmarkResponse<?> mensaje = new BookmarkResponse(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				"Cita Eliminada Exitosamente!!!");

		return mensaje;

	}

	@GetMapping("/citalibro/{id}")
	public BookmarkResponse<CitaLibro> buscarCitaLibroPorId(@PathVariable Long id) throws BookMarkException {
		return new BookmarkResponse<CitaLibro>(ResponseConstants.SUCCESS, String.valueOf(HttpStatus.OK),
				ResponseConstants.OK, citaLibroService.findById(id));
	}

}
