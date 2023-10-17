package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vehiculo {
	
	//Atributos
	private String marca;
	private String placa;
	private String modelo;
	private String color;
	private String tipoTransmicion;
	private ArrayList<ArrayList<LocalDate>> fechasAlquilado;
	private Categoria categoriaVehiculo;
	private String sede;
	private String estado;
	
	//Metodos
	                                    
	public Vehiculo(String placa, String marca, String modelo, String color, String tipoTransmicion, Categoria categoria, String sede,
			String infoFechasAlquilado, String activo) {
		this.marca = marca;
		this.placa = placa;
		this.modelo = modelo;
		this.color = color;
		this.tipoTransmicion = tipoTransmicion;
		this.categoriaVehiculo = categoria;
		if (activo.equals("activo")) {
			this.estado = "activo";
		}
		else {
			this.estado = "mantenimiento";
		}
		this.sede = sede;
		ArrayList<ArrayList<LocalDate>> fechasAlquilado = new ArrayList<ArrayList<LocalDate>>();
		if (!infoFechasAlquilado.equals("-")) {
			String[] partes = infoFechasAlquilado.split(",");
			ArrayList<LocalDate> tuplaFechas = new ArrayList<LocalDate>();
			for (String parte: partes) {
				String[] minipartes = parte.split("-");
				tuplaFechas = new ArrayList<LocalDate>();
				for (String miniparte: minipartes) {
					String[] infoFecha = miniparte.split("/");
					LocalDate fecha = LocalDate.of(Integer.parseInt(infoFecha[2]), Integer.parseInt(infoFecha[1]), Integer.parseInt(infoFecha[0]));
					tuplaFechas.add(fecha);
				}
				fechasAlquilado.add(tuplaFechas);
			}
			
			this.fechasAlquilado = fechasAlquilado;
		}
		else {
			this.fechasAlquilado = new ArrayList<ArrayList<LocalDate>>();	
		}
	}
	
	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public Categoria getCategoria() {
		return categoriaVehiculo;
	}
	
	public String getMarca() {
		return marca;
	}
	public String getPlaca() {
		return placa;
	}
	public String getModelo() {
		return modelo;
	}
	public String getColor() {
		return color;
	}
	public String getTipoTransmicion() {
		return tipoTransmicion;
	}
	public ArrayList<ArrayList<LocalDate>> getFechasAlquilado() {
		return fechasAlquilado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
