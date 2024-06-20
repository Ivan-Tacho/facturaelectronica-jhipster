package com.facturaelectronica.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturaelectronica.app.domain.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, String>{

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByUsuario(String usuario);

	Optional<Usuario> findByUsuarioAndPassword(String usuario, String password);
}
