package modelo;

import java.time.LocalDate;

public class Tarjeta {
	//Atributos
	
	private String numeroTarjeta;
	private LocalDate fechaVencimientoTarjeta;
	private String codigoSeguridadTarjeta;
	private String tipoTarjeta;
	private boolean bloqueo;
	
	//Metodos
	
	public Tarjeta(String numeroTarjeta, String fechaVencimientoTarjeta, String codigoSeguridadTarjeta,
			String tipoTarjeta, String estadoTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
		String[] partes = fechaVencimientoTarjeta.split("/");
		this.fechaVencimientoTarjeta = LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));;
		this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
		this.tipoTarjeta = tipoTarjeta;
		if (estadoTarjeta.equals("libre")) {
			this.bloqueo = false;
		}
		else {
			this.bloqueo = true;
		}
		
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public LocalDate getFechaVencimientoTarjeta() {
		return fechaVencimientoTarjeta;
	}

	public String getCodigoSeguridadTarjeta() {
		return codigoSeguridadTarjeta;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public void setFechaVencimientoTarjeta(LocalDate fechaVencimientoTarjeta) {
		this.fechaVencimientoTarjeta = fechaVencimientoTarjeta;
	}

	public void setCodigoSeguridadTarjeta(String codigoSeguridadTarjeta) {
		this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	public boolean getBloqueo() {
		return bloqueo;
	}
	
	public void bloquearTarjeta() {
		bloqueo = true;
	}
}
