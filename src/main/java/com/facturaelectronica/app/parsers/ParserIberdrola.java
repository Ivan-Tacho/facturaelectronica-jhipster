package com.facturaelectronica.app.parsers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.domain.Factura;

public class ParserIberdrola implements Parser{

	@Override
	public Factura obtenerDatos(MultipartFile pdfFileDTO) throws Exception {
		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
		PDFTextStripper stripper = new PDFTextStripper();
		Factura factura = new Factura();
		
		String patron = "";
		String valor = "";
		Double suma = 0.0;
        Pattern pattern = null;
        Matcher matcher = null;
        
        boolean numFacturaEncontrado = false;
        boolean periodoEncontrado = false;
        boolean potenciaEncontrada = false;
        boolean consumoEncontrado = false;
        int direccionEncontrada = 0;
        
		for(String linea : stripper.getText(document).split("\n")) {
			//Nº Factura
			if(linea.toLowerCase().contains("nº factura")) {
				numFacturaEncontrado = true;
			}
			else if(numFacturaEncontrado) {
				factura.setNumFactura(linea.substring(0, linea.indexOf("\r")));
				numFacturaEncontrado = false;
			}
			
			//Periodo factura
			else if(linea.toLowerCase().contains("periodo de facturación")){
				periodoEncontrado = true;
			}
			else if(periodoEncontrado) {
				
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
		        periodoEncontrado = false;
			}
			
			//Titular y Potencia contratada
			else if (linea.toLowerCase().contains("titular potencia")) {
				potenciaEncontrada = true;
			}
			else if(potenciaEncontrada) {
				patron = "\\d{1,5},\\d{1,5}";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setPotenciaContratada(Double.parseDouble(matcher.group().replace(",", ".")));
				}
				
				patron = "^(.*?)\\s+Potencia";
				pattern = Pattern.compile(patron);
				matcher = pattern.matcher(linea);

				if (matcher.find()) {
					factura.setTitular(matcher.group(1));
				}
				potenciaEncontrada = false;
			}
			
			//Peaje de acceso
			else if (linea.toLowerCase().contains("peaje de acceso")) {
				pattern = Pattern.compile("\\d{1,5}.\\d{1,5}");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setPeajeAcceso(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Gasto Potencia
			else if((linea.toLowerCase().contains("total importe potencia"))) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					if(factura.getGastoPotencia() == null) {
						factura.setGastoPotencia(Double.parseDouble(valor.replace(",", ".")));
					}
					else {
				        DecimalFormat df = new DecimalFormat("#,##");
						suma = factura.getGastoPotencia() + Double.parseDouble(valor.replace(",", "."));
						factura.setGastoPotencia(Double.parseDouble(df.format(suma)));
						suma = 0.0;
					}
				}
			}
			
			//Gasto Energia
			else if(linea.toLowerCase().contains("energía consumida")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					if(factura.getGastoEnergia() == null) {
						factura.setGastoEnergia(Double.parseDouble(valor.replace(",", ".")));
					}
					else {
				        DecimalFormat df = new DecimalFormat("#,##");
						suma = factura.getGastoPotencia() + Double.parseDouble(valor.replace(",", "."));
						factura.setGastoEnergia(Double.parseDouble(df.format(suma)));
						suma = 0.0;
					}
				}
			}
			//Descuentos
			else if(factura.getDescuentos() == null && linea.toLowerCase().contains("reg. orden")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setDescuentos(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Gasto Alquiler Equipos
			else if (linea.toLowerCase().contains("alquiler equipos")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());

				if (matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoAlquierEquipos(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Gasto Otros
			else if(linea.toLowerCase().contains("reg. fnee")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoOtros(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Impuestos
			else if(factura.getGastoImpuestos() == null && linea.toLowerCase().contains("impuesto sobre electricidad")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				while(matcher.find()) {
					valor = matcher.group();
				}
				factura.setGastoImpuestos(Double.parseDouble(valor.replace(",", ".")));
			}
			
			//Total factura
			else if(linea.toLowerCase().contains("total importe factura")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setGastoTotal(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Consumo Punta, Llano y Valle
			else if(linea.toLowerCase().contains("consumos desagregados")) {
				pattern = Pattern.compile("punta:\\s+(\\d{1,5},\\d{1,5})\\s*kwh");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(1);
					factura.setConsumoP1(Double.parseDouble(valor.replace(",", ".")));
				}
				
				pattern = Pattern.compile("llano:\\s+(\\d{1,5},\\d{1,5})\\s*kwh");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(1);
					factura.setConsumoP3(Double.parseDouble(valor.replace(",", ".")));
				}
				
				pattern = Pattern.compile("valle\\s+(\\d{1,5},\\d{1,5})\\s*kwh");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(1);
					factura.setConsumoP2(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Potencia maxima demandada Punta y Valle
			else if(linea.toLowerCase().contains("potencias máximas demandadas")) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\skw en p1)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setPotenciaMaximaPunta(Double.parseDouble(valor.replace(",", ".")));
				}
				
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\skw en p2)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setPotenciaMaximaValle(Double.parseDouble(valor.replace(",", ".")));
				}
			}
			
			//Consumo total
			else if (linea.toLowerCase().contains("consumo total")) {
				consumoEncontrado = true;
			}
			
			else if(consumoEncontrado) {
				pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s+kwh)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					valor = matcher.group(0);
					factura.setConsumoTotal(Double.parseDouble(valor.replace(",", ".")));
					consumoEncontrado = false;
				}
				
			}
			
			//CIF o NIF titular
			else if(linea.toLowerCase().contains("nif titular del contrato")) {
				pattern = Pattern.compile("nif titular del contrato:\\s*(.*)");
				matcher = pattern.matcher(linea.toLowerCase());
				
				if(matcher.find()) {
					factura.setNif(matcher.group(1));
				}
			}
			else if(linea.toLowerCase().contains("dirección de suministro")){
					direccionEncontrada = 1;
			}
			else if(direccionEncontrada > 0) {
				if(factura.getDireccion() == null) {
					factura.setDireccion(linea.substring(0, linea.indexOf("\r")));
				}
				else {
					factura.setDireccion(factura.getDireccion() + " " + linea.substring(0, linea.indexOf("\r")));
				}
				
				direccionEncontrada++;
				if(direccionEncontrada == 3) {
                    direccionEncontrada = 0;
                }
			}
		}
		
		factura.setCompaniaElec("Iberdrola");
		GestorParsers.quitarNulos(factura);

        return factura;
	}
}
