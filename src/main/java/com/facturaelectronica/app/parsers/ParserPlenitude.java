package com.facturaelectronica.app.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class ParserPlenitude implements Parser{

	@Override
	public Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception {
		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		Factura factura = new Factura();
		
		String patron = "";
		String valor = "";
        Pattern pattern = null;
        Matcher matcher = null;
        
        boolean consumoEncontrado = false;
        
		for(String linea : stripper.getText(document).split("\n")) {
			//Nº Factura
			if(linea.toLowerCase().contains("nº de factura")) {
				factura.setNumFactura(linea.substring(linea.indexOf(":") + 1, linea.indexOf("\r")));
			}
			
			//Periodo factura
			else if(linea.toLowerCase().contains("periodo de consumo")){
				patron = "\\d{2}/\\d{2}/\\d{4}";
		        pattern = Pattern.compile(patron);
		        matcher = pattern.matcher(linea.toLowerCase());
		        
		        List<String> fechas = new ArrayList<>();
		        while(matcher.find()) {
		        	fechas.add(matcher.group());
		        }
		        
		        if(fechas.size() == 2) {
					factura.setInicioPeriodoFacturacion(fechas.get(0));
					factura.setFinPeriodoFacturacion(fechas.get(1));
		        }
			}
			
			//Gasto Potencia
			else if (linea.toLowerCase().contains("por potencia contratada")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoPotencia(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			// Gasto energia
			else if (linea.toLowerCase().contains("por energía consumida")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoEnergia(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			// Gasto Otros
			else if (linea.toLowerCase().contains("gasto de gestión")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoOtros(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			else if (linea.toLowerCase().contains("servicio exprés")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoOtros(factura.getGastoOtros() + Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			else if (linea.toLowerCase().contains("gasto de gestión")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoOtros(factura.getGastoOtros() + Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			else if (linea.toLowerCase().contains("impuesto aplicado:")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoOtros(factura.getGastoOtros() + Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			
			// Impuesto electricidad
			else if (linea.toLowerCase().contains("impuesto electricidad:")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoImpuestos(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			// Alquiler equipos
			else if (linea.toLowerCase().contains("alquiler equipos")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoAlquierEquipos(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			// Gasto Total
			else if (linea.toLowerCase().contains("total importe factura")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setGastoTotal(Double.parseDouble(matcher.group().replace(",", ".")));
				}
			}
			
			// Desscuentos
			else if (linea.toLowerCase().contains("descuento sobre")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group().replace(",", ".");
					factura.setDescuentos(Double.parseDouble(valor));
				}
			}
			
			// Titular y NIF
			else if (linea.toLowerCase().contains("titular:")) {
				patron = "(?<=d/dna\\s)(.*)(?=\\snif)";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(1).trim();
					factura.setTitular(valor);
				}
				
				patron = "(?<=nif:\\s)(.*)";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group().trim();
					factura.setNif(valor);
				}
			}
			
			//Peaje acceso y potencia contratada
			else if (linea.toLowerCase().contains("peaje de acceso:")) {
				patron = "\\d{1,5}.\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group();
					factura.setPeajeAcceso(Double.parseDouble(valor.replace(",", ".")));
				}
				
				patron = "\\d{1,5},\\d{1,5}(?=\\skw)";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaContratada(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			// Consumo energia
			else if (linea.toLowerCase().trim().equals("en el periodo")) {
				consumoEncontrado = true;
			}
			
			else if (consumoEncontrado) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group();
					factura.setConsumoP1(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if (matcher.find()) {
					valor = matcher.group();
					factura.setConsumoP2(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if (matcher.find()) {
					valor = matcher.group();
					factura.setConsumoP3(Double.parseDouble(valor.replace(",", ".")));
					consumoEncontrado = false;
				}
			}
			
			else if (linea.toLowerCase().contains("consumo fácil")) {
				patron = "\\d{1,5},\\d{1,5}(?=\\skwh)";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group();
					factura.setConsumoTotal(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			// Potencia
			else if (linea.toLowerCase().contains("desglose del consumo facturado por periodo:")) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaP1(Double.parseDouble(valor.replace(",", ".")));
				}
				if (matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaP2(Double.parseDouble(valor.replace(",", ".")));
				}
				if (matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaP3(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			// Direccion
			else if (linea.toLowerCase().contains("dirección de suministro:")) {
				factura.setDireccion(linea.substring(linea.indexOf(":") + 1, linea.indexOf("\r")).trim());
			}
			
			// Descuento energia
			else if (linea.toLowerCase().contains("descuento fácil")) {
				patron = "\\d{1,5},\\d{1,5}(?=\\s%)";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group().replace(",", ".");
					factura.setPorcentajeDescuentoEnergia(Double.parseDouble(valor));
				}
			}
		}
		
		factura.setCompaniaElec("Plenitude");
		GestorParsers.quitarNulos(factura);

		System.out.println(stripper.getText(document));
		System.out.println(factura);
		

        return factura;
	}
}
