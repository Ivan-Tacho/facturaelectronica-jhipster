package com.facturaelectronica.app.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Factura")
public class Factura {
	

    @Id
    @Column
    private String numFactura;
    
    @Column
    private String inicioPeriodoFacturacion;
    
    @Column
    private String finPeriodoFacturacion;
    
    @Column
    private Double potenciaContratada;
    
    @Column
    private Double peajeAcceso;

    @Column
    private Double gastoPotencia;
    
    @Column
    private Double gastoEnergia;
    
    @Column
    private Double gastoImpuestos;
    
    @Column
    private Double gastoAlquierEquipos;
    
    @Column
    private Double gastoOtros;
    
    @Column
    private Double Descuentos;
    
    @Column
    private Double porcentajeDescuentoPotencia;
    
    @Column
    private Double porcentajeDescuentoEnergia;
    
    @Column
    private Double gastoTotal;

    @Column
    private Double consumoPunta;
    
    @Column
    private Double consumoLlano;
    
    @Column
    private Double consumoValle;
    
    @Column
    private Double consumoP1;
    
    @Column
    private Double consumoP2;
    
    @Column
    private Double consumoP3;
    
    @Column
    private Double consumoP4;
    
    @Column
    private Double consumoP5;
    
    @Column
    private Double consumoP6;
    
    @Column
    private Double consumoTotal;
    
    @Column
    private Double potenciaP1;
    
    @Column
    private Double potenciaP2;
    
    @Column
    private Double potenciaP3;
    
    @Column
    private Double potenciaP4;
    
    @Column
    private Double potenciaP5;
    
    @Column
    private Double potenciaP6;
    
    @Column
    private Double potenciaMaximaPunta;
    
    @Column
    private Double potenciaMaximaLlano;
    
    @Column
    private Double potenciaMaximaValle;
    
    @Column
    private Double potenciaMaximaP1;
    
    @Column
    private Double potenciaMaximaP2;
    
    @Column
    private Double potenciaMaximaP3;
    
    @Column
    private Double potenciaMaximaP4;
    
    @Column
    private Double potenciaMaximaP5;
    
    @Column
    private Double potenciaMaximaP6;
    
    @Column
    private String titular;
    
    @Column
    private String nif;
    
    @Column
    private String direccion;
    
    @Column
    private String companiaElec;
    
    @Column
    private String nombreArchivo;
    
    @ManyToOne
    @JoinColumn
    private Usuario comercial;

	public Factura() {
		super();
	}
    
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	public String getInicioPeriodoFacturacion() {
		return inicioPeriodoFacturacion;
	}
	public void setInicioPeriodoFacturacion(String inicioPeriodoFacturacion) {
		this.inicioPeriodoFacturacion = inicioPeriodoFacturacion;
	}
	public String getFinPeriodoFacturacion() {
		return finPeriodoFacturacion;
	}
	public void setFinPeriodoFacturacion(String finPeriodoFacturacion) {
		this.finPeriodoFacturacion = finPeriodoFacturacion;
	}
	public Integer getDiasFacturados() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = new Date();
        Date endDate = new Date();
        
		try {
			if(inicioPeriodoFacturacion != null && finPeriodoFacturacion != null && !inicioPeriodoFacturacion.isEmpty() && !finPeriodoFacturacion.isEmpty()) {
				startDate = sdf.parse(inicioPeriodoFacturacion);
				endDate = sdf.parse(finPeriodoFacturacion);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

        if (!endDate.after(startDate)) {
            return 0;
        }

        long diffInMillies = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
	}
	public Double getPotenciaContratada() {
		return potenciaContratada;
	}
	public void setPotenciaContratada(Double potenciaContratada) {
		this.potenciaContratada = potenciaContratada;
	}
	public Double getGastoPotencia() {
		return gastoPotencia;
	}
	public void setGastoPotencia(Double gastoPotencia) {
		this.gastoPotencia = gastoPotencia;
	}
	public Double getGastoEnergia() {
		return gastoEnergia;
	}
	public void setGastoEnergia(Double gastoEnergia) {
		this.gastoEnergia = gastoEnergia;
	}
	public Double getGastoImpuestos() {
		return gastoImpuestos;
	}
	public void setGastoImpuestos(Double gastoImpuestos) {
		this.gastoImpuestos = gastoImpuestos;
	}
	public Double getGastoOtros() {
		return gastoOtros;
	}
	public void setGastoOtros(Double gastoOtros) {
		this.gastoOtros = gastoOtros;
	}

	public Double getPorcentajeDescuentoPotencia() {
		return porcentajeDescuentoPotencia;
	}
	
	public void setPorcentajeDescuentoPotencia(Double porcentajeDescuentoPotencia) {
		this.porcentajeDescuentoPotencia = porcentajeDescuentoPotencia;
	}
	
	public Double getPorcentajeDescuentoEnergia() {
		return porcentajeDescuentoEnergia;
	}
	
	public void setPorcentajeDescuentoEnergia(Double porcentajeDescuentoEnergia) {
		this.porcentajeDescuentoEnergia = porcentajeDescuentoEnergia;
	}
	
	public Double getGastoTotal() {
		return gastoTotal;
	}
	
	public void setGastoTotal(Double gastoTotal) {
		this.gastoTotal = gastoTotal;
	}
	
	public Double getConsumoPunta() {
		return consumoPunta;
	}
	
	public void setConsumoPunta(Double consumoPunta) {
		this.consumoPunta = consumoPunta;
	}
	
	public Double getConsumoLlano() {
		return consumoLlano;
	}
	
	public void setConsumoLlano(Double consumoLlano) {
		this.consumoLlano = consumoLlano;
	}
	
	public Double getConsumoValle() {
		return consumoValle;
	}
	
	public void setConsumoValle(Double consumoValle) {
		this.consumoValle = consumoValle;
	}
	
	public Double getConsumoTotal() {
		return consumoTotal;
	}
	
	public void setConsumoTotal(Double consumoTotal) {
		this.consumoTotal = consumoTotal;
	}
	
	public String getTitular() {
		return titular;
	}
	
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public String getNif() {
		return nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(numFactura);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		return Objects.equals(numFactura, other.numFactura);
	}


	public Double getPeajeAcceso() {
		return peajeAcceso;
	}

	public void setPeajeAcceso(Double peajeAcceso) {
		this.peajeAcceso = peajeAcceso;
	}

	public Usuario getComercial() {
		return comercial;
	}

	public void setComercial(Usuario comercial) {
		this.comercial = comercial;
	}

	public String getCompaniaElec() {
		return companiaElec;
	}

	public void setCompaniaElec(String companiaElec) {
		this.companiaElec = companiaElec;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Double getDescuentos() {
		return Descuentos;
	}

	public void setDescuentos(Double descuentos) {
		Descuentos = descuentos;
	}

	public Double getPotenciaMaximaPunta() {
		return potenciaMaximaPunta;
	}

	public void setPotenciaMaximaPunta(Double potenciaMaximaPunta) {
		this.potenciaMaximaPunta = potenciaMaximaPunta;
	}

	public Double getPotenciaMaximaValle() {
		return potenciaMaximaValle;
	}

	public void setPotenciaMaximaValle(Double potenciaMaximaValle) {
		this.potenciaMaximaValle = potenciaMaximaValle;
	}
	
	public Double getGastoAlquierEquipos() {
		return gastoAlquierEquipos;
	}

	public void setGastoAlquierEquipos(Double gastoAlquierEquipos) {
		this.gastoAlquierEquipos = gastoAlquierEquipos;
	}
	
	public Double getPotenciaMaximaLlano() {
		return potenciaMaximaLlano;
	}

	public void setPotenciaMaximaLlano(Double potenciaMaximaLlano) {
		this.potenciaMaximaLlano = potenciaMaximaLlano;
	}
	
	public Double getPotenciaP1() {
		return potenciaP1;
	}

	public void setPotenciaP1(Double potenciaP1) {
		this.potenciaP1 = potenciaP1;
	}

	public Double getPotenciaP2() {
		return potenciaP2;
	}

	public void setPotenciaP2(Double potenciaP2) {
		this.potenciaP2 = potenciaP2;
	}

	public Double getPotenciaP3() {
		return potenciaP3;
	}

	public void setPotenciaP3(Double potenciaP3) {
		this.potenciaP3 = potenciaP3;
	}

	public Double getPotenciaP4() {
		return potenciaP4;
	}

	public void setPotenciaP4(Double potenciaP4) {
		this.potenciaP4 = potenciaP4;
	}

	public Double getPotenciaP5() {
		return potenciaP5;
	}

	public void setPotenciaP5(Double potenciaP5) {
		this.potenciaP5 = potenciaP5;
	}

	public Double getPotenciaP6() {
		return potenciaP6;
	}

	public void setPotenciaP6(Double potenciaP6) {
		this.potenciaP6 = potenciaP6;
	}

	public Double getConsumoP1() {
		return consumoP1;
	}

	public void setConsumoP1(Double consumoP1) {
		this.consumoP1 = consumoP1;
	}

	public Double getConsumoP2() {
		return consumoP2;
	}

	public void setConsumoP2(Double consumoP2) {
		this.consumoP2 = consumoP2;
	}

	public Double getConsumoP3() {
		return consumoP3;
	}

	public void setConsumoP3(Double consumoP3) {
		this.consumoP3 = consumoP3;
	}

	public Double getConsumoP4() {
		return consumoP4;
	}

	public void setConsumoP4(Double consumoP4) {
		this.consumoP4 = consumoP4;
	}

	public Double getConsumoP5() {
		return consumoP5;
	}

	public void setConsumoP5(Double consumoP5) {
		this.consumoP5 = consumoP5;
	}

	public Double getConsumoP6() {
		return consumoP6;
	}

	public void setConsumoP6(Double consumoP6) {
		this.consumoP6 = consumoP6;
	}

	public Double getPotenciaMaximaP1() {
		return potenciaMaximaP1;
	}

	public void setPotenciaMaximaP1(Double potenciaMaximaP1) {
		this.potenciaMaximaP1 = potenciaMaximaP1;
	}

	public Double getPotenciaMaximaP2() {
		return potenciaMaximaP2;
	}

	public void setPotenciaMaximaP2(Double potenciaMaximaP2) {
		this.potenciaMaximaP2 = potenciaMaximaP2;
	}

	public Double getPotenciaMaximaP3() {
		return potenciaMaximaP3;
	}

	public void setPotenciaMaximaP3(Double potenciaMaximaP3) {
		this.potenciaMaximaP3 = potenciaMaximaP3;
	}

	public Double getPotenciaMaximaP4() {
		return potenciaMaximaP4;
	}

	public void setPotenciaMaximaP4(Double potenciaMaximaP4) {
		this.potenciaMaximaP4 = potenciaMaximaP4;
	}

	public Double getPotenciaMaximaP5() {
		return potenciaMaximaP5;
	}

	public void setPotenciaMaximaP5(Double potenciaMaximaP5) {
		this.potenciaMaximaP5 = potenciaMaximaP5;
	}

	public Double getPotenciaMaximaP6() {
		return potenciaMaximaP6;
	}

	public void setPotenciaMaximaP6(Double potenciaMaximaP6) {
		this.potenciaMaximaP6 = potenciaMaximaP6;
	}

	@Override
	public String toString() {
		return "Factura [numFactura=" + numFactura + "\n inicioPeriodoFacturacion=" + inicioPeriodoFacturacion
				+ "\n finPeriodoFacturacion=" + finPeriodoFacturacion + "\n potenciaContratada=" + potenciaContratada
				+ "\n peajeAcceso=" + peajeAcceso + "\n gastoPotencia=" + gastoPotencia + "\n gastoEnergia=" + gastoEnergia
				+ "\n gastoImpuestos=" + gastoImpuestos + "\n gastoAlquierEquipos=" + gastoAlquierEquipos
				+ "\n gastoOtros=" + gastoOtros + "\n Descuentos=" + Descuentos + "\n porcentajeDescuentoPotencia="
				+ porcentajeDescuentoPotencia + "\n porcentajeDescuentoEnergia=" + porcentajeDescuentoEnergia
				+ "\n gastoTotal=" + gastoTotal + "\n consumoPunta=" + consumoPunta + "\n consumoLlano=" + consumoLlano
				+ "\n consumoValle=" + consumoValle + "\n consumoP1=" + consumoP1 + "\n consumoP2=" + consumoP2
				+ "\n consumoP3=" + consumoP3 + "\n consumoP4=" + consumoP4 + "\n consumoP5=" + consumoP5 + "\n consumoP6="
				+ consumoP6 + "\n consumoTotal=" + consumoTotal + "\n potenciaP1=" + potenciaP1 + "\n potenciaP2="
				+ potenciaP2 + "\n potenciaP3=" + potenciaP3 + "\n potenciaP4=" + potenciaP4 + "\n potenciaP5="
				+ potenciaP5 + "\n potenciaP6=" + potenciaP6 + "\n potenciaMaximaPunta=" + potenciaMaximaPunta
				+ "\n potenciaMaximaLlano=" + potenciaMaximaLlano + "\n potenciaMaximaValle=" + potenciaMaximaValle
				+ "\n potenciaMaximaP1=" + potenciaMaximaP1 + "\n potenciaMaximaP2=" + potenciaMaximaP2
				+ "\n potenciaMaximaP3=" + potenciaMaximaP3 + "\n potenciaMaximaP4=" + potenciaMaximaP4
				+ "\n potenciaMaximaP5=" + potenciaMaximaP5 + "\n potenciaMaximaP6=" + potenciaMaximaP6 + "\n titular="
				+ titular + "\n nif=" + nif + "\n direccion=" + direccion + "\n companiaElec=" + companiaElec
				+ "\n nombreArchivo=" + nombreArchivo + "\n comercial=" + comercial + "]";
	}
	
}
