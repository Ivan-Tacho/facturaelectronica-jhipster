package com.facturaelectronica.app.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Usuarios")
public class Usuario {

	@Id
	@Column
	private String email;

	@Column
	private String usuario;

	@Column
	private String password;

	public Usuario() {
		super();
	}

	public Usuario(String email, String usuario, String password) {
		super();
		this.email = email;
		this.usuario = usuario;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", usuario=" + usuario + ", password=" + password + "]";
	}
}

