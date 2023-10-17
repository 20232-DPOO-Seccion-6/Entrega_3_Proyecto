package modelo;

public class Empleado {
	
	//Atributos
	
	private String nombre;
	private String cedula;
	private String tipoEmpleado;
	private Usuario usuarioEmpleado;
	
	//Metodos
	
	public Empleado(String nombre, String cedula, String tipoEmpleado, Usuario user) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.tipoEmpleado = tipoEmpleado;
		this.usuarioEmpleado = user;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public String getTipoEmpleado() {
		return tipoEmpleado;
	}	
	
	public void setUsuarioEmpleado(String login, String contrasena) {
		usuarioEmpleado = new Usuario(login, contrasena, tipoEmpleado);
	}
	
	public Usuario getUsuarioEmpleado() {
		return usuarioEmpleado;
	}
}
