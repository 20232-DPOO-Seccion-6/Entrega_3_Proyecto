package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ClienteNormal{
	//Atributos
	
	private String nombre;
	private String cedula;
	private String numeroCelular;
	private LocalDate fechaNacimiento;
	private String nacionalidad;
	private String numeroLicencia;
	private String paisExpedicionLicencia;
	private LocalDate fechaVencimientoLicencia;
	private Tarjeta tarjeta;
	private Usuario user;
	private String correo;
	
	
	//Metodos
	
	public ClienteNormal(String nombre, String cedula, String numeroCelular, String fechaNacimiento, String nacionalidad,
			String numeroLicencia, String paisExpedicionLicencia, String fechaVencimientoLicencia, String numeroTarjeta,
			String fechaVencimientoTarjeta, String codigoSeguridadTarjeta, String tipoTarjeta, Usuario user, String correo, String estadoTarjeta) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.numeroCelular = numeroCelular;
		String[] partes = fechaNacimiento.split("/");
		this.fechaNacimiento =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
		this.nacionalidad = nacionalidad;
		this.numeroLicencia = numeroLicencia;
		this.paisExpedicionLicencia = paisExpedicionLicencia;
		String[] partes2 = fechaNacimiento.split("/");
		this.fechaVencimientoLicencia =  LocalDate.of(Integer.parseInt(partes2[2]), Integer.parseInt(partes2[1]), Integer.parseInt(partes2[0]));
		this.tarjeta = new Tarjeta(numeroTarjeta, fechaVencimientoTarjeta,codigoSeguridadTarjeta,tipoTarjeta, estadoTarjeta);
		this.user = user;
		this.correo = correo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getCedula() {
		return cedula;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public String getNacionalidad() {
		return nacionalidad;
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
	
	public Tarjeta getTarjeta() {
		return tarjeta;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void bloquearTarjeta() throws IOException {
		tarjeta.bloquearTarjeta();
		
		//Modificamos el .txt
		File archivoClientes = new File("./data/clientes.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoClientes));
		String linea = br.readLine(); 
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(nombre)) {
				texto += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" + partes[4] + ";" + partes[5] + ";";
				texto += partes[6] + ";" + partes[7] + ";" + partes[8] + ";" + partes[9] + ";" + partes[10] + ";" + partes[11] + ";";
				texto += partes[12] + ";" + partes[13] + ";" + partes[14] + ";" + "bloqueada";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		
		FileWriter archivo = new FileWriter("./data/clientes.txt");
		BufferedWriter writer;
		try {
			
			writer = new BufferedWriter(archivo);
			writer.write(texto);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
