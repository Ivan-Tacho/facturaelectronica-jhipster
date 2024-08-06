package com.facturaelectronica.app.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class ParserAEQ implements Parser{

	@Override
	public Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception {
		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setSortByPosition(true);
		Factura factura = new Factura();
		
		String patron = "";
		String valor = "";
        Pattern pattern = null;
        Matcher matcher = null;
        
        for(String linea : stripper.getText(document).split("\n")) {
			if(linea.length() > 5) {
				//Nº Factura
				if(linea.toLowerCase().contains("número de factura")) {
                    patron = "\\d{1,10}";
                    pattern = Pattern.compile(patron);
			        matcher = pattern.matcher(linea.toLowerCase());
                    
                    if(matcher.find()) {
                    	valor = matcher.group();
                    	factura.setNumFactura(valor);
                    }
				}
				
				//Periodo facturacion
				else if(linea.toLowerCase().contains("periodo de consumo")) {
					patron = "\\d{2}/\\d{2}/\\d{4}";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group();
						factura.setInicioPeriodoFacturacion(valor);
					}

					if (matcher.find()) {
						valor = matcher.group();
						factura.setFinPeriodoFacturacion(valor);
					}
				}
				
				// Peaje de acceso y Potencia contratada
				else if (linea.toLowerCase().contains("tarifa de acceso")) {
					patron = "\\d{1,5}.\\d{1,5}";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group();
						factura.setPeajeAcceso(Double.parseDouble(valor));
					}

					patron = "\\d{1,5},\\d{1,5}";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());
					
					if (matcher.find()) {
						valor = matcher.group();
						factura.setPotenciaContratada(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				// Gasto Potencia
				else if (linea.toLowerCase().contains("término de potencia")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setGastoPotencia(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				// Gasto Energia
				else if (linea.toLowerCase().contains("término de energía")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						
						if (factura.getGastoEnergia() == null) {
							factura.setGastoEnergia(Double.parseDouble(valor.replace(",", ".")));
						}
						else {
							factura.setGastoEnergia(factura.getGastoEnergia() + Double.parseDouble(valor.replace(",", ".")));
						}
					}
				}
				
				//Gasto Impuestos
				else if (linea.toLowerCase().contains("impuesto electricidad")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setGastoImpuestos(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				// Gasto Otros
				else if (linea.toLowerCase().contains("otros conceptos")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setGastoOtros(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				// Gasto Alquiler
				else if (linea.toLowerCase().contains("alquiler de contador")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setGastoAlquierEquipos(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				// Gasto Total
				else if (linea.toLowerCase().contains("importe")) {
					patron = "\\d{1,5},\\d{1,5}(?=\\s+€)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group(0);
						factura.setGastoTotal(Double.parseDouble(valor.replace(",", ".")));
					}
				}
				
				//Consumos
				
				//Potencias maximas
				
				//Consumo total (calculado)
				
				//Titular y NIF/CIF
				else if (linea.toLowerCase().contains("titular")) {
					patron = "(?<=titular:\\s+)(.+)(?=cif/)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group();
						factura.setTitular(valor);
					}
					
					patron = "(?<=cif/nif:\\s+).*(?=\\r)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group();
						factura.setNif(valor);
					}
				}
				
				// Direccion
				else if (linea.toLowerCase().contains("dirección de suministro")) {
					patron = "(?<=dirección de suministro:\\s+)(.+)(?=\\r)";
					pattern = Pattern.compile(patron);
					matcher = pattern.matcher(linea.toLowerCase());

					if (matcher.find()) {
						valor = matcher.group();
						factura.setDireccion(valor);
					}
				}

			}
        }
        
        factura.setCompaniaElec("Aeq");
        factura = GestorParsers.quitarNulos(factura);
        
        System.out.println(stripper.getText(document));
        System.out.println(factura.toString());
        return factura;
	}
}
