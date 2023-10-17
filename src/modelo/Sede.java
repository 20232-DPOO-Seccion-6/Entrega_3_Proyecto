package modelo;

import java.util.ArrayList;
import java.util.Map;

public class Sede {
	
	//Atributos
	
	private String nombre;
	private String ubicacion;
	private Map<String, String> horariosAtencion;
	private Administrador administradorLocal;
	private ArrayList<Empleado> empleadosSede;
	private Inventario inventarioSede;
	
	//Metodos
	
	public Sede(String nombre, String ubicacion, Map<String, String> horariosAtencion) {
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.horariosAtencion = horariosAtencion;
		this.empleadosSede = new ArrayList<>();
		this.inventarioSede = new Inventario();
		
	}

	public String getNombre() {
		return nombre;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public Map<String, String> getHorariosAtencion() {
		return horariosAtencion;
	}
	
	public String getAdministradorLocal() {
		return administradorLocal.getNombre();
	}
	
	public ArrayList<Empleado> getEmpleadosSede(){
		return empleadosSede;
	}
	
	public void setAdministradorLocal(String nombre, String cedula, Usuario usuarioAdmin) {
		this.administradorLocal = new Administrador(nombre, cedula, usuarioAdmin, this.nombre);
	}
	
	public void setEmpleado(String nombre, String cedula, String tipoEmpleado, Usuario user) {
		empleadosSede.add(new Empleado(nombre, cedula, tipoEmpleado, user));
	}
	
	public void setHorariosAtencion(String dia, String horaInicio, String horaFin) {
		// Los dias son L-V, S o D-F
		horariosAtencion.put(dia, horaInicio + '-' + horaFin);
	}
	
	public void modifyHorarioAtencion(String dia, String horaInicio, String horaFin) {
		
		/* This method just changes the data of the aperture hours, it assumes that the 
		 * day exists in the dictionary.
		 */
        horariosAtencion.remove(dia); 
        horariosAtencion.put(dia, horaInicio + '-' + horaFin);
	}
	
	public Inventario getInventario() {
		return inventarioSede;
	}
	
	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleadosSede = empleados;
	}
	
}
