package com.facturaelectronica.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facturaelectronica.app.domain.Usuario;
import com.facturaelectronica.app.repository.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
    	Usuario usuario = usuarioRepository.findByUsuario(username).orElse(null);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // Construir el objeto UserDetails
        return User.withUsername(usuario.getUsuario())
                   .password(usuario.getPassword())
                   //.roles(usuario.getRoles())
                   .build();
    }
}
