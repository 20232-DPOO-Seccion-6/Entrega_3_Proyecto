package modelo;

import java.time.LocalDate;

public class Conductor {
	
	//Atributos
	private String nombre;
	private String cedula;
	private String numeroLicencia;
	private String paisExpedicionLicencia;
	private LocalDate fechaVencimientoLicencia; //Siempre en formato dd/mm/yyyy
	private String fotoLicencia;
	private String numeroContacto;
	private String correo;
	
	
	//Metodos
	
	public Conductor(String nombre,String cedula, String numeroLicencia,String paisExpedicionLicencia,
	String fechaVencimientoLicencia, String fotoLicencia, String numeroContacto, String correo) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.numeroLicencia = numeroLicencia;
		this.paisExpedicionLicencia = paisExpedicionLicencia;
		String[] partes = fechaVencimientoLicencia.split("/");
		this.fechaVencimientoLicencia =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
		this.fotoLicencia = fotoLicencia;
		this.numeroContacto = numeroContacto;
		this.correo = correo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getCedula() {
		return cedula;
	}
	
	public String getNumeroLicencia() {
		return numeroLicencia;
	}
	
	public String getPaisExpedicionLicencia() {
		return paisExpedicionLicencia;
	}
	
	public LocalDate getFechaVencimientoLicencia() {
		return fechaVencimientoLicencia;
	}
	
	public String getFotoLicencia() {
		return fotoLicencia;
	}
	
	public String getNumeroContacto() {
		return numeroContacto;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	
}
