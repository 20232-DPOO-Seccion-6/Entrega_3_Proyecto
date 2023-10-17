package modelo;

public class Seguro {
	//Atributo 
	
	private String nombre;
	private String precioDiario;
	private String detalles;
	
	//Metodos
	
	public Seguro(String nombre, String precioDiario, String detalles) {
		this.nombre = nombre;
		this.precioDiario = precioDiario;
		this.detalles = detalles;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPrecioDiario() {
		return precioDiario;
	}

	public String getDetalles() {
		return detalles;
	}
	
	public String getInformacion() {
		String texto = "";
		texto += "\n Nombre: " + nombre + "\n Precio diario: " + precioDiario + "\n Detalles:\n " + detalles;
		return texto;
	}
}
