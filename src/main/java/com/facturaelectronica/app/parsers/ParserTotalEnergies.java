package com.facturaelectronica.app.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class ParserTotalEnergies implements Parser{

	@Override
	public Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception {
		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		Factura factura = new Factura();
		
		String patron = "";
        Pattern pattern = null;
        Matcher matcher = null;
        boolean valleEncontrado = true;
        int direccionEncontrada = 0;
        List<String> palabras = new ArrayList<>();
        
		for(String linea : stripper.getText(document).split("\n")) {
			if(linea.length() > 5) {
				
				//Nº Factura
				if(linea.toLowerCase().contains("factura nº")) {
					factura.setNumFactura(linea.substring(linea.lastIndexOf("º") + 1).trim());
				}
				
				//Periodo facturacion
				else if(linea.toLowerCase().contains("período")) {
					
					patron = "\\d{2}.\\d{2}.\\d{4}";
			        pattern = Pattern.compile(patron);
			        matcher = pattern.matcher(linea.toLowerCase());

			        if (matcher.find()) {
						factura.setInicioPeriodoFacturacion(matcher.group().replace(".", "/"));
					}

					if (matcher.find()) {
						factura.setFinPeriodoFacturacion(matcher.group().replace(".", "/"));
					}
				}
				
				//Potencia contratada y gasto potencia
				else if (linea.toLowerCase().contains("día(s)")) {
					if (factura.getPotenciaContratada() == null) {
						patron = "\\d{1,5},\\d{1,5}\\s+kW";
						pattern = Pattern.compile(patron);
						matcher = pattern.matcher(linea);

						if (matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setPotenciaContratada(Double.parseDouble(palabras.get(0).replace(",", ".")));
						}
					}
					if (factura.getGastoPotencia() == null) {
						patron = "\\d{1,5},\\d{1,5}\\s+€";
						pattern = Pattern.compile(patron);
						matcher = pattern.matcher(linea);

						if (matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setGastoPotencia(Double.parseDouble(palabras.get(0).replace(",", ".")));
						}
					}
				}
				
				//Peaje de acceso
				else if(linea.toLowerCase().contains("peaje de acceso")) {
                    patron = "\\d{1,5}\\.\\d{1,5}";
                    pattern = Pattern.compile(patron);
                    matcher = pattern.matcher(linea);

                    if (matcher.find()) {
                        factura.setPeajeAcceso(Double.parseDouble(matcher.group(0).replace(",", ".")));
                    }
				}
				
				//Gasto Energia y Consumo total
				else if(linea.toLowerCase().contains("€/kwh")) {
					if(factura.getGastoEnergia() == null) {
						pattern = Pattern.compile("\\d{1,5},\\d{1,5}\\s+€");
						matcher = pattern.matcher(linea);
						
						if(matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setGastoEnergia(Double.parseDouble(palabras.get(0).replace(",", ".")));
						}
					}
					if(factura.getConsumoTotal() == null) {
						pattern = Pattern.compile("\\d{1,5}\\s+kwh");
						matcher = pattern.matcher(linea.toLowerCase());
						
						if(matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setConsumoTotal(Double.parseDouble(palabras.get(0)));
						}
					}
				}		
				
				//Gasto Alquiler Equipos
				else if(linea.toLowerCase().contains("alquiler")) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}\\s+€");
					matcher = pattern.matcher(linea);
					
					if(matcher.find()) {
						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
						factura.setGastoAlquierEquipos(Double.parseDouble(palabras.get(0).replace(",", ".")));
					}
				}
				
				//Gasto Impuestos
				else if(linea.toLowerCase().contains("total tasas e impuestos")) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}\\s+€");
					matcher = pattern.matcher(linea);
					
					if(matcher.find()) {
						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
						factura.setGastoImpuestos(Double.parseDouble(palabras.get(0).replace(",", ".")));
					}
				}
				
				//Total factura
				else if(linea.toLowerCase().contains("importe total")) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}\\s+€");
					matcher = pattern.matcher(linea);
					
					if(matcher.find()) {
						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
						factura.setGastoTotal(Double.parseDouble(palabras.get(0).replace(",", ".")));
					}
				}
				
				//Potencia maxima punta, llano y valle
				else if(linea.toLowerCase().contains("potencias máximas demandadas")){
					pattern = Pattern.compile("(\\d{1,5},\\d{1,5})\\s+kw[\\w\\s]*\\(punta\\)");;
					matcher = pattern.matcher(linea.toLowerCase());
					
					if (matcher.find()) {
						factura.setPotenciaMaximaP1(Double.parseDouble(matcher.group(1).replace(",", ".")));
					}
					
					pattern = Pattern.compile("(\\d{1,5},\\d{1,5})\\s+kw[\\w\\s]*\\(llano\\)");;
					matcher = pattern.matcher(linea.toLowerCase());
					
					if (matcher.find()) {
						factura.setPotenciaMaximaP2(Double.parseDouble(matcher.group(1).replace(",", ".")));
					}
					
					pattern = Pattern.compile("(\\d{1,5},\\d{1,5})\\s+kw[\\w\\s]*\\(valle\\)");
					matcher = pattern.matcher(linea.toLowerCase());
					
					if (matcher.find()) {
						factura.setPotenciaMaximaP3(Double.parseDouble(matcher.group(1).replace(",", ".")));
					}
				}
				
				//Consumo punta, llano y valle
				else if(linea.toLowerCase().contains("punta:")) {
					if(factura.getConsumoP1() == null) {
						pattern = Pattern.compile("punta:\\s+\\d{1,5},\\d{1,5}\\s+kwh");
						matcher = pattern.matcher(linea.toLowerCase());
						
						if(matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setConsumoP1(Double.parseDouble(palabras.get(1).replace(",", ".")));
						}
					}
					if (factura.getConsumoP2() == null) {
						pattern = Pattern.compile("llano:\\s+\\d{1,5},\\d{1,5}\\s+kwh");
						matcher = pattern.matcher(linea.toLowerCase());

						if (matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setConsumoP2(Double.parseDouble(palabras.get(1).replace(",", ".")));
						}
					}
					if (factura.getConsumoP3() == null) {
						pattern = Pattern.compile("valle:\\s+\\d{1,5},\\d{1,5}\\s+kwh");
						matcher = pattern.matcher(linea.toLowerCase());

						if (matcher.find()) {
							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
							factura.setConsumoP3(Double.parseDouble(palabras.get(1).replace(",", ".")));
						}
						else {
							valleEncontrado = false;
						}
					}
				}
				else if(!valleEncontrado) {
					pattern = Pattern.compile("\\d{1,5},\\d{1,5}\\s+kwh");
					matcher = pattern.matcher(linea.toLowerCase());
					
					if(matcher.find()) {
						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
						factura.setConsumoP3(Double.parseDouble(palabras.get(0).replace(",", ".")));
						valleEncontrado = true;
					}
				}
				
                //Titular
				else if(linea.toLowerCase().startsWith("cliente:")) {
					factura.setTitular(linea.substring(linea.indexOf(":") + 1).trim());
				}
				
				//NIF
				else if(linea.toLowerCase().contains("nif")) {
					pattern = Pattern.compile("NIF: \\w+");
					matcher = pattern.matcher(linea);
					
					if(matcher.find()) {
						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
						factura.setNif(palabras.get(1));
					}
				}
				//Direccion
				else if((linea.toLowerCase().contains("dirección del suministro"))) {
					direccionEncontrada = 1;
				}
				else if(direccionEncontrada > 0 && direccionEncontrada < 3){
					if(factura.getDireccion() == null) {
						factura.setDireccion(linea.replace("\n", "").replace("\r", ""));
						direccionEncontrada++;
					}
					else {
						factura.setDireccion(factura.getDireccion() + " " + linea.replace("\n", "").replace("\r", ""));
						direccionEncontrada++;
					}
				}
			}
		}
		
		factura.setCompaniaElec("TotalEnergies");
		//System.out.println(factura.toString());
		GestorParsers.quitarNulos(factura);
		
        return factura;
	}
	
}
