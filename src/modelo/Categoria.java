package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Categoria {

	//Atributos
	
	private String IdCategoria;
	private int precioExtraEntrega;
	private int precioExtraConductor;
	private Map<ArrayList<LocalDate>, Integer> precioFechas;
	
	
	//Metodos
	
	public Categoria(String idCategoria, int precioExtraEntrega, int precioExtraConductor, Map<ArrayList<LocalDate>, Integer> precioFechas) {
		IdCategoria = idCategoria;
		this.precioExtraEntrega = precioExtraEntrega;
		this.precioExtraConductor = precioExtraConductor;
		this.precioFechas = precioFechas;
	}

	public String getIdCategoria() {
		return IdCategoria;
	}


	public int getPrecioExtraEntrega() {
		return precioExtraEntrega;
	}


	public int getPrecioExtraConductor() {
		return precioExtraConductor;
	}
	
	public Map<ArrayList<LocalDate>, Integer> getPrecioFechas(){
		return precioFechas;
	}
	
	public int getPrecioParaFechaDada(LocalDate fechaDada) {
		int precio = 0;
		for (ArrayList<LocalDate> fechas : precioFechas.keySet()) {
			LocalDate fechaInicio = fechas.get(0);
			LocalDate fechaFin = fechas.get(1);
			boolean estaEntre = fechaDada.isAfter(fechaInicio) && fechaDada.isBefore(fechaFin);
			if (estaEntre == true){
				precio = precioFechas.get(fechas); 
			}
		}
		return precio;
	}

	public void setPrecioExtraEntrega(int precioExtraEntrega) {
		this.precioExtraEntrega = precioExtraEntrega;
	}

	public void setPrecioExtraConductor(int precioExtraConductor) {
		this.precioExtraConductor = precioExtraConductor;
	}

	public void setPrecioFechas(Map<ArrayList<LocalDate>, Integer> mapaFechas) {
		this.precioFechas = mapaFechas;
	}
	
}
