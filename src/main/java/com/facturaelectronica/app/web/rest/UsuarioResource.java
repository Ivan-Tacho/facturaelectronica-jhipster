package com.facturaelectronica.app.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturaelectronica.app.service.UsuarioService;
import com.facturaelectronica.app.service.dto.UsuarioDTO;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	private final UsuarioService usuarioService;
	
	public UsuarioResource(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping("/registro")
	public ResponseEntity<Boolean> registrarUsuario(@RequestBody UsuarioDTO usuario) {
		Boolean result = usuarioService.registrarUsuario(usuario);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<String>> obtenerTodosUsuarios() {
		List<String> result = usuarioService.obtenerTodosUsuarios();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/emails")
	public ResponseEntity<List<String>> obtenerTodosEmails() {
		List<String> result = usuarioService.obtenerTodosEmails();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/existe-email/{email}")
	public ResponseEntity<Boolean> existeEmail(@PathVariable String email) {
		Boolean result = usuarioService.existeEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/existe-usuario/{usuario}")
	public ResponseEntity<Boolean> existeUsuario(@PathVariable String usuario) {
		Boolean result = usuarioService.existeUsuario(usuario);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody UsuarioDTO usuario) {
		Boolean result = usuarioService.login(usuario);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
