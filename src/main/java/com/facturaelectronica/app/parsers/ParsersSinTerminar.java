package com.facturaelectronica.app.parsers;

public class ParsersSinTerminar {
//	private Factura obtenerDatosEndesa(MultipartFile pdfFileDTO) throws Exception {
//		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
//		PDFTextStripper stripper = new PDFTextStripper();
//		Factura factura = new Factura();
//		
//		String patron = "";
//        Pattern pattern = null;
//        Matcher matcher = null;
//        boolean direccionEncontrada = false;
//        List<String> palabras = new ArrayList<>();
//        
//		for(String linea : stripper.getText(document).split("\n")) {
//			if(linea.length() > 5) {
//				//Datos de la factura
//				if(factura.getNumFactura() == null && linea.toLowerCase().contains("nº factura")) {
//					factura.setNumFactura(linea.substring(linea.lastIndexOf(":") + 1).trim());
//				}
//				else if(factura.getInicioPeriodoFacturacion() == null && linea.toLowerCase().contains("periodo") &&
//				   factura.getFinPeriodoFacturacion() == null) {
//					
//					patron = "\\d{2}/\\d{2}/\\d{4}";
//			        pattern = Pattern.compile(patron);
//			        matcher = pattern.matcher(linea.toLowerCase());
//			        List<String> fechas = new ArrayList<>();
//			        
//			        while(matcher.find()) {
//			        	fechas.add(matcher.group());
//			        }
//			        
//					factura.setInicioPeriodoFacturacion(fechas.get(0));
//					factura.setFinPeriodoFacturacion(fechas.get(1));
//				}
//				else if (factura.getPotenciaContratada() == null && linea.toLowerCase().contains("potencia contratada")) {
//					patron = "\\d{1,5},\\d{1,5}";
//					pattern = Pattern.compile(patron);
//					matcher = pattern.matcher(linea);
//
//					if (matcher.find()) {
//						factura.setPotenciaContratada(Double.parseDouble(matcher.group().replace(",", "")) / 1000);
//					}
//				}
//				
//				//Datos gasto
//				else if(factura.getGastoPotencia() == null && linea.toLowerCase().contains("potencia")) {
//					pattern = Pattern.compile("Potencia\\s+\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoPotencia(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoEnergia() == null && linea.toLowerCase().contains("energía")) {
//					pattern = Pattern.compile("Energía\\s+\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoEnergia(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				else if(linea.toLowerCase().contains("descuentos") && factura.getGastoDescuentos() == null) {
//					pattern = Pattern.compile("Descuentos\\s+-?\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoDescuentos(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				else if(linea.toLowerCase().contains("descuento")) {
//
//					if (factura.getPorcentajeDescuentoEnergia() == null) {
//						pattern = Pattern.compile("x\\s+-\\d{1,5}(,\\d{1,5})*\\s+%");
//						matcher = pattern.matcher(linea);
//
//						if (matcher.find()) {
//							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//							factura.setPorcentajeDescuentoEnergia(Double.parseDouble(palabras.get(1).replace(",", ".")));
//						}
//					}
//					if (factura.getPorcentajeDescuentoPotencia() == null) {
//						pattern = Pattern.compile("Descuento\\s+-\\d{1,5}(,\\d{1,5})*\\s+%");
//						matcher = pattern.matcher(linea);
//
//						if (matcher.find()) {
//							palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//							factura.setPorcentajeDescuentoPotencia(Double.parseDouble(palabras.get(1).replace(",", ".")));
//						}
//					}
//				}
//				
//				else if(factura.getGastoOtros() == null && linea.toLowerCase().contains("otros")) {
//					pattern = Pattern.compile("Otros\\s+\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoOtros(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoImpuestos() == null && linea.toLowerCase().contains("impuestos")) {
//					pattern = Pattern.compile("Impuestos\\s+\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoImpuestos(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoTotal() == null && linea.toLowerCase().contains("total")) {
//					pattern = Pattern.compile("Total\\s+\\d{1,5},\\d{1,5}\\s+€");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setGastoTotal(Double.parseDouble(palabras.get(1).replace(",", ".")));
//					}
//				}
//				
//				//Consumo
//				else if(factura.getConsumoPunta() == null && linea.toLowerCase().contains("consumo punta")) {
//					pattern = Pattern.compile("consumo punta\\s+\\d{1,5}\\s+kwh");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setConsumoPunta(Double.parseDouble(palabras.get(2)));
//					}
//				}
//				else if(factura.getConsumoValle() == null && linea.toLowerCase().contains("consumo valle")) {
//					pattern = Pattern.compile("consumo valle\\s+\\d{1,5}\\s+kwh");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setConsumoValle(Double.parseDouble(palabras.get(2)));
//					}
//				}
//				else if(factura.getConsumoSuperValle() == null && linea.toLowerCase().contains("consumo supervalle")) {
//					pattern = Pattern.compile("consumo supervalle\\s+\\d{1,5}\\s+kwh");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setConsumoSuperValle(Double.parseDouble(palabras.get(2)));
//					}
//				}
//				else if(factura.getConsumoTotal() == null && linea.toLowerCase().contains("consumo total")) {
//					pattern = Pattern.compile("consumo total\\s+\\d{1,5}\\s+kwh");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setConsumoTotal(Double.parseDouble(palabras.get(2)));
//					}
//				}
//				else if(factura.getConsumoKwHora() == null && linea.toLowerCase().contains("salido")
//						&& linea.toLowerCase().contains("€/kwh")) {
//					pattern = Pattern.compile("salido a \\d{1,5},\\d{1,5} €/kwh");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setConsumoKwHora(Double.parseDouble(palabras.get(2).replace(",", ".")));
//					}
//				}
//				
//				//Datos titular
//
//				else if(factura.getTitular() == null && linea.toLowerCase().contains("titular del contrato")) {
//					pattern = Pattern.compile("contrato: \\w+ \\w+\\s?\\w*");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						int i = 1;
//						factura.setTitular("");
//						while(palabras.size() > i) {
//							if(factura.getTitular().isEmpty()) factura.setTitular(palabras.get(i));
//							else factura.setTitular(factura.getTitular() + " " + palabras.get(i));
//							i++;
//						}
//					}
//				}
//				else if(factura.getNif() == null && linea.toLowerCase().contains("nif")) {
//					pattern = Pattern.compile("NIF: \\w+");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						factura.setNif(palabras.get(1));
//					}
//				}
//				else if((factura.getDireccion() == null && linea.toLowerCase().contains("dirección de suministro"))
//						|| direccionEncontrada) {
//					if(!direccionEncontrada) {
//						pattern = Pattern.compile("(?<=suministro:\\s+)\\w+(?:\\s.+)*");
//						direccionEncontrada = true;
//					}
//					else {
//						pattern = Pattern.compile("(.+\\s)+");
//						direccionEncontrada = false;
//					}
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						int i = 0;
//						while(palabras.size() > i) {
//							if(factura.getDireccion() == null) factura.setDireccion(palabras.get(i));
//							else factura.setDireccion(factura.getDireccion() + " " + palabras.get(i));
//							i++;
//						}
//					}
//				}
//			}
//		}
//		
//		quitarNulos(factura);
//		//System.out.println(stripper.getText(document));
//        return factura;
//	}
//	
//	private Factura obtenerDatosRepsol(MultipartFile pdfFileDTO) throws Exception {
//		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
//		PDFTextStripper stripper = new PDFTextStripper();
//		Factura factura = new Factura();
//		
//		String patron = "";
//		String valor = "";
//		Double suma = 0.0;
//        Pattern pattern = null;
//        Matcher matcher = null;
//        boolean potenciaEncontrada = false;
//        int direccionEncontrada = 0;
//        List<String> palabras = new ArrayList<>();
//        
//		for(String linea : stripper.getText(document).split("\n")) {
//			if(linea.length() > 5) {
//				//Datos de la factura
//				if(factura.getNumFactura() == null && linea.toLowerCase().contains("nº factura")) {
//					patron = "(?:/\\s+)(.*)";
//			        pattern = Pattern.compile(patron);
//			        matcher = pattern.matcher(linea);
//			        
//			        if(matcher.find()) {
//						factura.setNumFactura(matcher.group(1));
//			        }
//				}
//				else if(factura.getInicioPeriodoFacturacion() == null && linea.toLowerCase().contains("periodo") &&
//				   factura.getFinPeriodoFacturacion() == null) {
//					
//					patron = "\\d{2}.\\d{2}.\\d{4}";
//			        pattern = Pattern.compile(patron);
//			        matcher = pattern.matcher(linea.toLowerCase());
//			        List<String> fechas = new ArrayList<>();
//			        
//			        while(matcher.find()) {
//			        	fechas.add(matcher.group());
//			        }
//			        
//			        if(fechas.size() == 2) {
//						factura.setInicioPeriodoFacturacion(fechas.get(0).replaceAll("\\.", "/"));
//						factura.setFinPeriodoFacturacion(fechas.get(1).replaceAll("\\.", "/"));
//			        }
//				}
//				else if (factura.getPotenciaContratada() == null && linea.toLowerCase().contains("potencias contratadas")) {
//					patron = "\\d{1,5},\\d{1,5}";
//					pattern = Pattern.compile(patron);
//					matcher = pattern.matcher(linea);
//
//					if (matcher.find()) {
//						factura.setPotenciaContratada(Double.parseDouble(matcher.group().replace(",", ".")));
//					}
//				}
//				
//				//Datos gasto
//				else if((linea.toLowerCase().contains("potencia") && linea.toLowerCase().contains("kwaño"))
//						|| potenciaEncontrada) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						if(factura.getGastoPotencia() == null) {
//							factura.setGastoPotencia(Double.parseDouble(valor.replace(",", ".")));
//						}
//						else {
//							suma = factura.getGastoPotencia() + Double.parseDouble(valor.replace(",", "."));
//							factura.setGastoPotencia(suma);
//							suma = 0.0;
//						}
//					}
//				}
//				else if(linea.toLowerCase().contains("consumo") && linea.toLowerCase().contains("/kwh")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoEnergia(Double.parseDouble(valor.replace(",", ".")));
//					}
//					
//					pattern = Pattern.compile("(\\d{1,5},\\d{1,10})");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoKwHora(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoDescuentos() == null && linea.toLowerCase().contains("descuento")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s€)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoDescuentos(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoOtros() == null && linea.toLowerCase().contains("equipos")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoOtros(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoImpuestos() == null && linea.toLowerCase().contains("impuesto")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoImpuestos(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoTotal() == null && linea.toLowerCase().contains("total factura")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s+€)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoTotal(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				
//				//Consumo
//				else if(factura.getConsumoPunta() == null && linea.toLowerCase().contains("(real)")) {
//					pattern = Pattern.compile("(\\d{1,5})(?=\\skwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoPunta(Double.parseDouble(valor));
//					}
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoLlano(Double.parseDouble(valor));
//					}
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoValle(Double.parseDouble(valor));
//					}
//					
//					suma = factura.getConsumoPunta() + factura.getConsumoValle() + factura.getConsumoLlano();
//					factura.setConsumoTotal(suma);
//				}
//				
//				//Datos titular
//
//				else if(factura.getTitular() == null && linea.toLowerCase().startsWith("titular")) {
//					pattern = Pattern.compile("(?::\\s*)(.*)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						factura.setTitular(matcher.group(1));
//					}
//				}
//				else if(factura.getNif() == null && linea.toLowerCase().contains("cif/nif")) {
//					pattern = Pattern.compile("(?::\\s*)(.*)");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						factura.setNif(matcher.group(1));
//					}
//				}
//				else if((factura.getDireccion() == null && linea.toLowerCase().contains("suministro:"))
//						|| direccionEncontrada > 0) {
//					if(direccionEncontrada == 0) {
//						direccionEncontrada = 1;
//					}
//					else {
//						pattern = Pattern.compile(".*");
//						direccionEncontrada++;
//						if(direccionEncontrada > 2) {
//							direccionEncontrada = 0;
//						}
//					}
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						palabras = Arrays.asList(matcher.group(0).split("\\s+"));
//						int i = 0;
//						while(palabras.size() > i) {
//							if(factura.getDireccion() == null) factura.setDireccion(palabras.get(i));
//							else factura.setDireccion(factura.getDireccion() + " " + palabras.get(i));
//							i++;
//						}
//					}
//				}
//			}
//		}
//
//		quitarNulos(factura);
//		
//		//System.out.println(stripper.getText(document));
//        return factura;
//	}
//
//	private Factura obtenerDatosNaturgy(MultipartFile pdfFileDTO) throws Exception {
//		PDDocument document = PDDocument.load(pdfFileDTO.getInputStream());
//		PDFTextStripper stripper = new PDFTextStripper();
//		Factura factura = new Factura();
//
//		Double numeroDecimales = 6.0;
//		double multiplicador = Math.pow(10, numeroDecimales);
//		String patron = "";
//		String valor = "";
//		Double suma = 0.0;
//        Pattern pattern = null;
//        Matcher matcher = null;
//        boolean facturacionEncontrada = false;
//        Double fasesDistintasFacturacion = 0.0;
//        
//		for(String linea : stripper.getText(document).split("\n")) {
//			if(linea.length() > 5) {
//				
//				//Datos de la factura
//				if(factura.getNumFactura() == null && linea.toLowerCase().contains("nº factura")) {
//					patron = "(?::\\s+)(.*)";
//			        pattern = Pattern.compile(patron);
//			        matcher = pattern.matcher(linea);
//			        
//			        if(matcher.find()) {
//						factura.setNumFactura(matcher.group(1));
//			        }
//				}
//				else if((factura.getInicioPeriodoFacturacion() == null && factura.getFinPeriodoFacturacion() == null &&
//						linea.toLowerCase().contains("periodo")) || facturacionEncontrada) {
//					if(facturacionEncontrada) {
//						patron = "(\\d{1,2})(?: de )(\\w+)(?: de )(\\d{1,4})";
//				        pattern = Pattern.compile(patron);
//				        matcher = pattern.matcher(linea.toLowerCase());
//				        List<String> fechas = new ArrayList<>();
//				        
//				        while(matcher.find()) {
//				        	fechas.add(matcher.group());
//				        }
//				        
//				        if(fechas.size() == 2) {
//				        	DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
//				        	DateTimeFormatter formatoNuevo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//				        	LocalDate fechaObjeto = LocalDate.parse(fechas.get(0), formatoOriginal);
//				        	ZonedDateTime fecha = fechaObjeto.atStartOfDay(ZoneId.systemDefault());
//				        	
//							factura.setInicioPeriodoFacturacion(fecha.format(formatoNuevo));
//							
//							fechaObjeto = LocalDate.parse(fechas.get(1), formatoOriginal);
//				        	fecha = fechaObjeto.atStartOfDay(ZoneId.systemDefault());
//							factura.setFinPeriodoFacturacion(formatoNuevo.format(fecha));
//				        }
//				        facturacionEncontrada = false;
//					}
//					else {
//						facturacionEncontrada = true;
//					}
//				}
//				else if (factura.getPotenciaContratada() == null && linea.toLowerCase().contains("potencia contratada en punta")) {
//					patron = "\\d{1,5},\\d{1,5}";
//					pattern = Pattern.compile(patron);
//					matcher = pattern.matcher(linea);
//
//					if (matcher.find()) {
//						factura.setPotenciaContratada(Double.parseDouble(matcher.group().replace(",", ".")));
//					}
//				}
//				
//				//Datos gasto
//				else if((linea.toLowerCase().contains("potencia contratada"))) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=.*\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoPotencia(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(linea.toLowerCase().contains("energía consumida")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=.*\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoEnergia(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoDescuentos() == null && linea.toLowerCase().contains("compensacion")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=.*\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoDescuentos(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(factura.getGastoOtros() == null &&linea.toLowerCase().contains("alquiler del contador")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=.*\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoOtros(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				else if(linea.toLowerCase().contains("impuesto electricidad") || linea.toLowerCase().contains("impuesto aplicado")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=.*\\r)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						if(factura.getGastoImpuestos() == null) {
//							factura.setGastoImpuestos(Double.parseDouble(valor.replace(",", ".")));
//						}
//						else {
//							factura.setGastoImpuestos(factura.getGastoImpuestos() + Double.parseDouble(valor.replace(",", ".")));
//						}
//					}
//				}
//				else if(factura.getGastoTotal() == null && linea.toLowerCase().contains("importe factura")) {
//					pattern = Pattern.compile("\\d{1,5},\\d{1,5}(?=\\s+€)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setGastoTotal(Double.parseDouble(valor.replace(",", ".")));
//					}
//				}
//				
//				//Consumo
//				else if(linea.toLowerCase().contains("(punta)")) {
//					pattern = Pattern.compile("(\\d+.?\\d*)(?=kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoPunta(Double.parseDouble(valor));
//					}
//
//					pattern = Pattern.compile("\\d+,\\d{3,9}(?=\\s€/kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						suma += Double.parseDouble(valor.replace(",", "."));
//						fasesDistintasFacturacion++;
//					}
//				}
//				else if(linea.toLowerCase().contains("(llano)")) {
//					pattern = Pattern.compile("(\\d+.?\\d*)(?=kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoLlano(Double.parseDouble(valor));
//					}
//
//					pattern = Pattern.compile("\\d+,\\d{3,9}(?=\\s€/kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						suma += Double.parseDouble(valor.replace(",", "."));
//						fasesDistintasFacturacion++;
//					}
//				}
//				else if(linea.toLowerCase().contains("(valle)")) {
//					pattern = Pattern.compile("(\\d+.?\\d*)(?=kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoValle(Double.parseDouble(valor));
//					}
//					
//					pattern = Pattern.compile("\\d+,\\d{3,9}(?=.*/kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						suma += Double.parseDouble(valor.replace(",", "."));
//						fasesDistintasFacturacion++;
//					}
//				}
//				else if(factura.getConsumoTotal() == null && linea.toLowerCase().contains("su consumo en el periodo facturado ha sido")) {
//					pattern = Pattern.compile("(\\d+.?\\d*)(?=kwh)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(0);
//						factura.setConsumoTotal(Double.parseDouble(valor));
//					}
//				}
//				//Datos titular
//
//				else if(factura.getTitular() == null && linea.toLowerCase().contains("titular")) {
//					pattern = Pattern.compile("(?::\\s*)(.*)(nif)");
//					matcher = pattern.matcher(linea.toLowerCase());
//					
//					if(matcher.find()) {
//						valor = matcher.group(1);
//						StringBuilder sb = new StringBuilder();
//						sb.append(valor.substring(valor.lastIndexOf(",") + 2, valor.length()));
//						sb.append(valor.substring(0, valor.lastIndexOf(",")));
//						factura.setTitular(sb.toString());
//					}
//					
//					pattern = Pattern.compile("(?:NIF:\\s*)(.*)");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						factura.setNif(matcher.group(1));
//					}
//				}
//				else if((factura.getDireccion() == null && linea.toLowerCase().contains("dirección suministro:"))) {
//					pattern = Pattern.compile("(?::\\s*)(.*)");
//					matcher = pattern.matcher(linea);
//					
//					if(matcher.find()) {
//						factura.setDireccion(matcher.group(1));
//					}
//				}
//			}
//		}
//		
//		
//		quitarNulos(factura);
//		
//		//System.out.println(stripper.getText(document));
//		document.close();
//        return factura;
//	}
}
