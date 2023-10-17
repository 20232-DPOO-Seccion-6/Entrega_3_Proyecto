package modelo;

public class ClienteEspecial{
	
	//Atributos
	
	private String nombre;
	private String cedula;
	
	
	//Metodos
	
	public ClienteEspecial(String nombre, String cedula) {
		this.nombre = nombre;
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCedula() {
		return cedula;
	}	
}
