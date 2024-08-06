package com.facturaelectronica.app.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class ParserEleia implements Parser{

	@Override
	public Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception {
		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setSortByPosition(true);
		Factura factura = new Factura();
		
		String valor = "";
        Pattern pattern = null;
        Matcher matcher = null;
        
        boolean periodoEncontrado = false;
        boolean potenciaContratadaEncontrada = false;
        boolean tarifaAccesoEncontrado = false;
        boolean potenciaEncontrada = false;
        boolean energiaEncontrada = false;
        boolean potenciaMaximaEncontrada = false;
        boolean titularEncontrado = false;
        boolean direccionEncontrada = false;
        
		for(String linea : stripper.getText(document).split("\n")) {
			//Nº Factura
			if(linea.toLowerCase().contains("nº de factura:")) {
				valor = linea.substring(linea.indexOf(":") + 2, linea.indexOf("\r"));
				factura.setNumFactura(valor);
			}
			
			//Periodo factura y total factura
			else if(linea.toLowerCase().contains("periodo de facturación")){
				periodoEncontrado = true;
			}
			else if(periodoEncontrado) {
				
		        pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
		        matcher = pattern.matcher(linea.toLowerCase());
		        
		        List<String> fechas = new ArrayList<>();
		        while(matcher.find()) {
		        	valor = matcher.group();
		        	fechas.add(valor);
		        }
		        
		        if(fechas.size() == 2) {
		        	valor = fechas.get(0);
					factura.setInicioPeriodoFacturacion(valor);
					valor = fechas.get(1);
					factura.setFinPeriodoFacturacion(valor);
		        }
		        
		        pattern = Pattern.compile("\\d{1,5},\\d{1,5}");
		        matcher = pattern.matcher(linea.toLowerCase());
		        
		        if (matcher.find()) {
		        	valor = matcher.group(0).replace(",", ".");
		        	factura.setGastoTotal(Double.parseDouble(valor));
		        }
		        
		        periodoEncontrado = false;
			}
			
			//Potencia contratada
			else if (linea.toLowerCase().contains("potencia contratada")) {
				potenciaContratadaEncontrada = true;
			}
			else if(potenciaContratadaEncontrada) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}");
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					valor = matcher.group(0).replace(",", ".");
					factura.setPotenciaContratada(Double.parseDouble(valor));
				}
				
				potenciaContratadaEncontrada = false;
			}
			
			//Peaje de acceso
			else if (linea.toLowerCase().contains("tarifa acceso:")) {
				tarifaAccesoEncontrado = true;
			}
			else if(tarifaAccesoEncontrado) {
				pattern = Pattern.compile("\\d{1,5}.\\d{1,5}");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setPeajeAcceso(Double.parseDouble(valor));
				}
				tarifaAccesoEncontrado = false;
			}
			
			// Potencia
			else if((linea.toLowerCase().contains("término potencia"))) {
				potenciaEncontrada = true;
				
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\skw)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setPotenciaP1(Double.parseDouble(valor.replace(",", ".")));
				}
				
			}
			
			else if(potenciaEncontrada) {

				if (linea.toLowerCase().contains("p2")) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\skw)");
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setPotenciaP2(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				if (linea.toLowerCase().contains("p3")) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\skw)");
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setPotenciaP3(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				if(linea.toLowerCase().contains("total")) {
					factura.setGastoPotencia(Double.parseDouble(linea.substring(linea.indexOf(":") + 2, linea.indexOf(" €")).replace(",", ".")));
					potenciaEncontrada = false;
				}
			}
			
			//Gasto enegia
			else if (linea.toLowerCase().contains("término energía")) {
				energiaEncontrada = true;
			}
			
			else if(energiaEncontrada) {
				pattern = Pattern.compile("(?<=total: )\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoEnergia(Double.parseDouble(valor.replace(",", ".")));
					energiaEncontrada = false;
				}
			}
			
			//Consumo energia
			else if((linea.toLowerCase().contains("consumo(kwh)"))) {
				
				pattern = Pattern.compile("\\d{1,5},*\\d{0,10}");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setConsumoTotal(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setConsumoP1(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setConsumoP2(Double.parseDouble(valor.replace(",", ".")));
				}

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setConsumoP3(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Gasto Alquiler Equipos
			else if (linea.toLowerCase().contains("alquiler equipo")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoAlquierEquipos(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Gasto Otros
			else if (linea.toLowerCase().contains("productos financiación")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoOtros(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Impuestos
			else if(factura.getGastoImpuestos() == null && linea.toLowerCase().contains("impuesto electricidad")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€\\r)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group();
				}
				factura.setGastoImpuestos(Double.parseDouble(valor.replace(",", ".")));
			}
			
			//Total factura
			else if(linea.toLowerCase().contains("total factura")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoTotal(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Potencia maxima demandada
			else if(linea.toLowerCase().contains("potencia máxima demandada")){
				potenciaMaximaEncontrada = true;
			}
			
			else if(potenciaMaximaEncontrada) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP1(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP2(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP3(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP4(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP5(Double.parseDouble(valor.replace(",", ".")));
				}
				
				if(matcher.find()) {
					valor = matcher.group();
					factura.setPotenciaMaximaP6(Double.parseDouble(valor.replace(",", ".")));
					potenciaMaximaEncontrada = false;
				}
			}
			
			//Titular
			else if (linea.toLowerCase().contains("autónoma")) {
				titularEncontrado = true;
			}
			
			else if(titularEncontrado) {
				factura.setTitular(linea.substring(0, linea.indexOf("\r")));
				titularEncontrado = false;
			}
			
			//CIF o NIF titular
			else if(linea.toLowerCase().contains("nif:")) {
				pattern = Pattern.compile("nif:\\s*(.*)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					factura.setNif(matcher.group(1));
				}
				direccionEncontrada = true;
			}
			
			else if(direccionEncontrada) {
				factura.setDireccion(linea.substring(0, linea.indexOf("\r")));
				direccionEncontrada = false;
			}
		}
		
		factura.setCompaniaElec("Eleia");
		
		GestorParsers.quitarNulos(factura);
		
        return factura;
	}
}
