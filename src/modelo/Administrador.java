package modelo;

public class Administrador {
	
	//Atributos
	private String nombre;
	private String cedula;
	private Usuario usuarioAdmin;
	private String sede;
	
	
	
	//Metodos
	
	public Administrador(String nombre, String cedula, Usuario usuarioAdmin, String sede) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.usuarioAdmin = usuarioAdmin;
		this.sede = sede;
	}

	public String getNombre() {
		return nombre;
	}


	public String getCedula() {
		return cedula;
	}
	
	
	public Usuario getUsuarioAdmin() {
		return usuarioAdmin;
	}
	
	public String getsede() {
		return sede;
	}
}
