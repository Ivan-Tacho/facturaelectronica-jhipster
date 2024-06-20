package com.facturaelectronica.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturaelectronica.app.domain.Usuario;
import com.facturaelectronica.app.repository.UsuarioRepository;
import com.facturaelectronica.app.service.dto.UsuarioDTO;
import com.facturaelectronica.app.service.mapper.UsuarioMapper;

@Service
@Transactional
public class UsuarioService{

	private final UsuarioRepository usuarioRepository;
	
	private final UsuarioMapper usuarioMapper;
	
	public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioMapper = usuarioMapper;
	}
	
	public boolean registrarUsuario(UsuarioDTO usuario) {
		usuario.setEmail(usuario.getEmail().toLowerCase());
		usuario.setUsuario(usuario.getUsuario().toLowerCase());
		return usuarioRepository.save(usuarioMapper.toEntity(usuario)) != null;
	}
	
	public List<String> obtenerTodosUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<String> result = new ArrayList<>();
		usuarios.forEach(usuario -> result.add(usuario.getUsuario()));
		return result;
	}
	
	public List<String> obtenerTodosEmails() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<String> result = new ArrayList<>();
		usuarios.forEach(usuario -> result.add(usuario.getEmail()));
		return result;
	}
	
	public boolean existeEmail(String email) {
		return usuarioRepository.findByEmail(email.toLowerCase()).isPresent();
	}
	
	public boolean existeUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario.toLowerCase()).isPresent();
	}
	
	public boolean login(UsuarioDTO usuario) {
		return usuarioRepository
				.findByUsuarioAndPassword(usuario.getUsuario().toLowerCase(), usuario.getPassword())
				.isPresent();
	}
}
