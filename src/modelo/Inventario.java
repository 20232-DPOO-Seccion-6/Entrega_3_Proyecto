package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventario {
	
	//Atributos
	private Map<String, ArrayList<Vehiculo>> vehiculos;
	
	//Metodos
	
	public Inventario() {
		this.vehiculos = new HashMap<>();
	}


	public Map<String, ArrayList<Vehiculo>> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		String categoria = vehiculo.getCategoria().getIdCategoria();
		if (vehiculos.get(categoria) == null) {
			ArrayList<Vehiculo> carros = new ArrayList<>();
			carros.add(vehiculo);
			vehiculos.put(categoria, carros);
		}
		else {
			vehiculos.get(categoria).add(vehiculo);
		}
	}
	
	
	
}
