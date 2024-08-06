package com.facturaelectronica.app.parsers;

import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public interface Parser {
	
	Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception;

}
