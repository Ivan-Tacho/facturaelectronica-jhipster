package com.facturaelectronica.app.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.facturaelectronica.app.domain.Usuario;
import com.facturaelectronica.app.service.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper{
	
	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	Usuario toEntity(UsuarioDTO userDTO);
	
	UsuarioDTO toDto(Usuario userDTO);
}
