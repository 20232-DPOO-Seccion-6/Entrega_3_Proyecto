package modelo;

import java.time.LocalDate;

public class TrasladoReserva {
	
	//Atributos
	
	private String placaVehiculo;
	private LocalDate fecha;
	private String sedeSalida;
	private String sedeLlegada;
	private ClienteEspecial conductor;
	
	//Metodos
	
	public TrasladoReserva(String placaVehiculo,
			String fecha, String sedeSalida, String sedeLlegada, ClienteEspecial conductor) {
		this.placaVehiculo = placaVehiculo;
		String[] partes = fecha.split("/");
		this.fecha =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
		this.sedeSalida = sedeSalida;
		this.sedeLlegada = sedeLlegada;
		this.conductor = conductor;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getSedeSalida() {
		return sedeSalida;
	}

	public String getSedeLlegada() {
		return sedeLlegada;
	}
	
	public ClienteEspecial getConductor() {
		return conductor;
	}
}
