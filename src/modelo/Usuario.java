package modelo;

public class Usuario {
	
	// Atributos
	private String login;
	private String contrasena;
	private String rol;
	
	//Metodos
	
	public Usuario(String login, String contrasena, String rol) 
	{
		this.login = login;
		this.contrasena = contrasena;
		this.rol = rol;
	}
	
	public String getLogin() 
	{
		return login;
	}
	
	public String getContrasena()
	{
		return contrasena;
	}
	
	public String getRol() {
		return rol;
	}
	
}

