package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Coche;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {

		List<Usuario> usuarios = usuarioService.getAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id) {

		Usuario usuario = usuarioService.getUsuarioById(id);

		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);

		return ResponseEntity.ok(nuevoUsuario);

	}

	@CircuitBreaker(name = "cochesCB", fallbackMethod = "fallBackGetCoches")
	@GetMapping("/coches/{usuarioId}")
	public ResponseEntity<List<Coche>> listarCoches(@PathVariable("usuarioId") int id) {

		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Coche> coches = usuarioService.getCoches(id);

		return ResponseEntity.ok(coches);

	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id) {

		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Moto> motos = usuarioService.getMotos(id);

		return ResponseEntity.ok(motos);

	}

	@CircuitBreaker(name = "cochesCB", fallbackMethod = "fallBackSaveCoche")
	@PostMapping("/coche/{usuarioId}")
	public ResponseEntity<Coche> guardarCoche(@PathVariable int usuarioId, @RequestBody Coche coche) {

		Coche nuevoCoche = usuarioService.saveCoche(usuarioId, coche);

		return ResponseEntity.ok(nuevoCoche);

	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable int usuarioId, @RequestBody Moto moto) {

		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);

		return ResponseEntity.ok(nuevaMoto);

	}

	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable int usuarioId) {
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehculos(usuarioId);

		return ResponseEntity.ok(resultado);

	}

	private ResponseEntity<List<Coche>> fallBackGetCoches(@PathVariable("usuarioId") int id,
			RuntimeException exception) {
		return new ResponseEntity("El usuario: " + id + " tiene los coches en el taller.", HttpStatus.OK);
	}

	private ResponseEntity<List<Coche>> fallBackSaveCoche(@PathVariable("usuarioId") int id, @RequestBody Coche coche,
			RuntimeException exception) {
		return new ResponseEntity("El usuario: " + id + " no tiene dinero para los coches.", HttpStatus.OK);
	}

	private ResponseEntity<List<Coche>> fallBackGetMotos(@PathVariable("usuarioId") int id,
			RuntimeException exception) {
		return new ResponseEntity("El usuario: " + id + " tiene las motos en el taller.", HttpStatus.OK);
	}

	private ResponseEntity<List<Coche>> fallBackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto,
			RuntimeException exception) {
		return new ResponseEntity("El usuario: " + id + " no tiene dinero para las motos.", HttpStatus.OK);
	}

	private ResponseEntity<List<Coche>> fallBackGetTodos(@PathVariable("usuarioId") int id,
			RuntimeException exception) {
		return new ResponseEntity("El usuario: " + id + " tiene los vehiculos en el taller.", HttpStatus.OK);
	}

}
