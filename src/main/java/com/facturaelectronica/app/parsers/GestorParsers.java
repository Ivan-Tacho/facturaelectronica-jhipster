package com.facturaelectronica.app.parsers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class GestorParsers {
	
	public static Map<String, Parser> parsers = new HashMap<>();
	
	public static Factura obtenerDatos(String compania, MultipartFile pdfFileDTO) throws Exception {
		Parser parser = GestorParsers.parsers.get(compania);
		Factura factura = parser.obtenerDatos(pdfFileDTO);
		return factura;
	}
	
	public static void inicializar() {
		GestorParsers.registrarParser("AEQ", new ParserAEQ());
		GestorParsers.registrarParser("ELEIA", new ParserEleia());
		GestorParsers.registrarParser("ENDESA", new ParserEndesa());
		GestorParsers.registrarParser("IBERDROLA", new ParserIberdrola());
		GestorParsers.registrarParser("PLENITUDE", new ParserPlenitude());
		GestorParsers.registrarParser("TOTALENERGIE", new ParserTotalEnergies());
	}
	
	public static void registrarParser(String compania, Parser parser) {
        parsers.put(compania, parser);
    }
	
	public static Factura quitarNulos(Factura factura) throws Exception {
		for(Field field : factura.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if(field.get(factura) == null) {
				if(field.getType().equals(String.class)) {
					field.set(factura, "");
				}
				else if(field.getType().equals(Double.class)) {
					field.set(factura, 0.0);
				}
			}
		}
		return factura;
	}
}
