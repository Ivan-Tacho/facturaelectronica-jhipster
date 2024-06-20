package com.facturaelectronica.app.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity 
@Table(name="Facturas")
public class Factura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Lob
    private byte[] file;

    
    @Column
    private String numFactura;
    @Column
    private String inicioPeriodoFacturacion;
    @Column
    private String finPeriodoFacturacion;
    @Column
    private Double potenciaContratada;

    
    @Column
    private Double gastoPotencia;
    @Column
    private Double gastoEnergia;
    @Column
    private Double gastoImpuestos;
    @Column
    private Double gastoOtros;
    @Column
    private Double gastoDescuentos;
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
    private Double consumoSuperValle;
    @Column
    private Double consumoTotal;
    @Column
    private Double consumoKwHora;

    
    @Column
    private String titular;
    @Column
    private String nif;
    @Column
    private String direccion;

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
			startDate = sdf.parse(inicioPeriodoFacturacion);
			endDate = sdf.parse(finPeriodoFacturacion);
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
	public Double getGastoDescuentos() {
		return gastoDescuentos;
	}
	public void setGastoDescuentos(Double gastoDescuentos) {
		this.gastoDescuentos = gastoDescuentos;
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
	
	public Double getConsumoSuperValle() {
		return consumoSuperValle;
	}
	
	public void setConsumoSuperValle(Double consumoSuperValle) {
		this.consumoSuperValle = consumoSuperValle;
	}
	
	public Double getConsumoTotal() {
		return consumoTotal;
	}
	
	public void setConsumoTotal(Double consumoTotal) {
		this.consumoTotal = consumoTotal;
	}
	
	public Double getConsumoKwHora() {
		return consumoKwHora;
	}
	
	public void setConsumoKwHora(Double consumoKwHora) {
		this.consumoKwHora = consumoKwHora;
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

	@Override
	public String toString() {
		return "Factura [id=" + id + ", numFactura=" + numFactura + ", inicioPeriodoFacturacion="
				+ inicioPeriodoFacturacion + ", finPeriodoFacturacion=" + finPeriodoFacturacion
				+ ", diasFacturados=" + getDiasFacturados()
				+ ", potenciaContratada=" + potenciaContratada + ", gastoPotencia=" + gastoPotencia + ", gastoEnergia="
				+ gastoEnergia + ", gastoImpuestos=" + gastoImpuestos + ", gastoOtros=" + gastoOtros
				+ ", gastoDescuentos=" + gastoDescuentos + ", porcentajeDescuentoPotencia=" + porcentajeDescuentoPotencia
				+ ", porcentajeDescuentoEnergia=" + porcentajeDescuentoEnergia + ", gastoTotal=" + gastoTotal + ", consumoPunta="
				+ consumoPunta + ", consumoLlano=" + consumoLlano + ", consumoValle=" + consumoValle
				+ ", consumoSuperValle=" + consumoSuperValle + ", consumoTotal=" + consumoTotal + ", consumoKwHora="
				+ consumoKwHora + ", titular=" + titular + ", nif=" + nif + ", direccion=" + direccion + "]";
	}

}
