package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class EmpresaVehiculos {
	
	//Atributos
	
	private String nombre;
	private ArrayList<Sede> sedes;
	private Administrador administradorGeneral;
	private ArrayList<Seguro> seguros;
	private Map<String, Categoria> categorias;
	private ArrayList<Usuario> usuarios;
	private Map<String, Reserva> reservas;
	private Map<String, ClienteNormal> clientes;
	private Map<String, Conductor> conductores;
	
	
	//Metodos
	
	public EmpresaVehiculos(String nombre, String adminGeneral, String cedulaAdmin, Usuario userAdmin) {
		this.nombre = nombre;
		this.administradorGeneral = new Administrador(adminGeneral, cedulaAdmin, userAdmin, "General");
		this.sedes = new ArrayList<>();
		this.categorias = new HashMap<>();
		this.seguros = new ArrayList<>();
		this.usuarios = new ArrayList<>();
		this.reservas = new HashMap<>();
		this.clientes = new HashMap<>();
		this.conductores = new HashMap<>();
	}

	public String getNombre() {
		return nombre;
	}


	public ArrayList<Sede> getSedes() {
		return sedes;
	}


	public Administrador getAdministradorGeneral() {
		return administradorGeneral;
	}

	public void setSede(String nombreSede, String ubicacion,  Map<String, String> horariosAtencion) {
		sedes.add(new Sede(nombreSede, ubicacion, horariosAtencion));
	}
	
	public ArrayList<Seguro> getSeguros(){
		return seguros;
	}
	
	public Map<String, Reserva> getReservas(){
		return reservas;
	}
	
	public void setSeguro(String nombre, String precioDiario, String detalles) throws IOException {
		Seguro seg = new Seguro(nombre, precioDiario, detalles);
		seguros.add(seg);
		
		//Modificamos el .txt
		String texto = "";
		texto += seg.getNombre() + ";";
		texto += seg.getPrecioDiario() + ";";
		texto += seg.getDetalles();
		escritor(texto, "./data/seguros.txt", true);
	}
	
	public Map<String, Categoria> getCategorias() {
		return categorias;
	}
	
	public ArrayList<Usuario> getUsuarios(){
		return usuarios;
	}
	public void setUsuario(Usuario user) throws IOException {
		usuarios.add(user);
		
		//Modificamos el .txt
		
		String texto = "\n";
		texto += user.getLogin() + ";";
		texto += user.getContrasena() + ";";
		texto += user.getRol();
		escritor(texto, "./data/usuarios.txt", true);
	}
	
	public Usuario getUsuario(String login) {
		for(Usuario user: usuarios) {
			if (user.getLogin().equals(login)) {
				return user;
			}
		}
		return null;
	}
	
	public ArrayList<String> listadoPlacasVehiculos(){
		ArrayList<String> carros = new ArrayList<>();
		for (int i = 0; i< sedes.size();i++) {
			Set<String> carrosSede = sedes.get(i).getInventario().getVehiculos().keySet();
			for (String categoria: carrosSede) {
				ArrayList<Vehiculo> vehiculosCat = sedes.get(i).getInventario().getVehiculos().get(categoria);
				for (Vehiculo carro: vehiculosCat) {
					carros.add(carro.getPlaca());
				}
			}
			
		}
		return carros;
	}
	
	//Escritura archivos
	public void escritor(String texto, String path, boolean otroFile) throws IOException {
		//True entonces creo un nuevo archivo
		if (otroFile == true) {
			try {
				FileWriter fileWriter = new FileWriter(path, true);
		        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		        bufferedWriter.write(texto);
		        bufferedWriter.close();
		        fileWriter.close();
				}catch (IOException e) {
					e.printStackTrace();
		        }
		}
		else {
			FileWriter archivo = new FileWriter(path);
			BufferedWriter writer;
			try {
				
				writer = new BufferedWriter(archivo);
				writer.write(texto);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//Verificacion de usuarios
	
	public String verificarUsuario(String login, String password) {
		String rol = "";
		for (int i=0; i < usuarios.size(); i++) {
			Usuario userActual = usuarios.get(i);
			if (userActual.getLogin().equals(login)) {
				if (userActual.getContrasena().equals(password)) {
					rol = userActual.getRol(); 
				}
			}
		}
		
		return rol;	
	}
	
	//Eliminar un vehiculo 
	
	public void eliminarVehiculo(String placa) throws IOException {
		for (int i=0; i<sedes.size(); i++) {
			Map<String, ArrayList<Vehiculo>> vehiculos = sedes.get(i).getInventario().getVehiculos();
			Set<String> listaCategoriasSede = vehiculos.keySet();
			for (String categoria: listaCategoriasSede) {
				ArrayList<Vehiculo> listaVehiculosSedeCat = vehiculos.get(categoria);
				for (int j=0; j< listaVehiculosSedeCat.size();j++) {
					if (placa.equals(listaVehiculosSedeCat.get(j).getPlaca())) {
						listaVehiculosSedeCat.remove(j);
					}
				}			
			}
		}
		
		
		//Modificamos el .txt de vehiculos
		File archivoVehiculos = new File("./data/vehiculos.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(placa)) {
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		FileWriter archivoVehiculosActualizado = new FileWriter("./data/vehiculos.txt");
		BufferedWriter writer;
		try {
			
			writer = new BufferedWriter(archivoVehiculosActualizado);
			writer.write(texto);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//Agregar vehiculo
	
	public void anadirVehiculo(String sede, Vehiculo carro) throws IOException {
		Sede sedeInteres = null;
		for (int i=0; i < sedes.size(); i++) {
			if (sede.equals(sedes.get(i).getNombre())) {
				sedeInteres = sedes.get(i);
				sedeInteres.getInventario().setVehiculo(carro);
			}
		}
		
		//Modificamos el .txt de vehiculos
		String texto = "\n"; 
		texto += carro.getPlaca() + ";";
		texto += carro.getMarca() + ";";
		texto += carro.getModelo() + ";";
		texto += carro.getColor() + ";";
		texto += carro.getTipoTransmicion() + ";";
		texto += carro.getCategoria().getIdCategoria() + ";";
		texto += carro.getSede() + ";";
		texto += "activo";
		escritor(texto, "./data/vehiculos.txt", true);
	}
	
	
	//Agregar Sede
	public void anadirSede(String nombre, String ubicacion, Map<String, String> horariosAtencion, Administrador admin, Usuario user) throws IOException {
		sedes.add(new Sede(nombre, ubicacion, horariosAtencion));
		
		//Modificamos el .txt
		String texto = "\n";
		texto += nombre + ";";
		texto += ubicacion + ";";
		String horariosAtencionStr = "";
		for (String dia: horariosAtencion.keySet()) {
			String horario = horariosAtencion.get(dia);
			horariosAtencionStr += horario + "/";
		}
		texto += horariosAtencionStr + ";";
		String infoAdmin = "";
		infoAdmin += admin.getNombre() + "," + admin.getCedula() + "," + user.getRol() + "," + user.getLogin() + "," + user.getContrasena();
		texto += infoAdmin;
		escritor(texto, "./data/sedes.txt", true);
	}
	
	//Eliminar Sede
	
	public void eliminarSede(String nombreSede) throws IOException {
		for (int i=0; i<sedes.size(); i++) {
			if (sedes.get(i).getNombre().equals(nombreSede)) {
				sedes.remove(i);
			}
		}
		
		//Modificamos .txt
		File archivoSedes = new File("./data/sedes.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoSedes));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(nombreSede)) {
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/sedes.txt", false);
	}
	
	
	//Consulta categoria
	
	public Categoria consultarCategoria(String cat) {
		return categorias.get(cat);
	}
	
	public void modificarCategoriaPreciosAnadirUno(String categoria, String fechaInit, String fechaFin, String precio,Map<ArrayList<LocalDate>, Integer> mapaFechas) throws IOException {
		String[] partesFechaInit = fechaInit.split("/");
		String[] partesFechaFin = fechaFin.split("/");
		LocalDate fechaInicial = LocalDate.of(Integer.parseInt(partesFechaInit[2]), Integer.parseInt(partesFechaInit[1]), Integer.parseInt(partesFechaInit[0]));
		LocalDate fechaFinal = LocalDate.of(Integer.parseInt(partesFechaFin[2]), Integer.parseInt(partesFechaFin[1]), Integer.parseInt(partesFechaFin[0]));
		ArrayList<LocalDate> fechas = new ArrayList<>();
		fechas.add(fechaInicial);
		fechas.add(fechaFinal);
		mapaFechas.put(fechas, Integer.parseInt(precio));
		
		Categoria catMod = categorias.get(categoria);
		catMod.setPrecioFechas(mapaFechas);
		categorias.remove(categoria);
		categorias.put(categoria, catMod);
		
		
		//Modificamos el .txt de categorias
		File archivoCategorias = new File("./data/categorias.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoCategorias));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(categoria)) {
				String infoFechas = "";
				Map<ArrayList<LocalDate>, Integer> precioFechas = categorias.get(categoria).getPrecioFechas();
				Set<ArrayList<LocalDate>> tuplasFechas = precioFechas.keySet();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				for (ArrayList<LocalDate> tupla: tuplasFechas) {
					String fechaUno = tupla.get(0).format(formato);
					String fechaDos = tupla.get(1).format(formato);
					Integer precioFechasInt = precioFechas.get(tupla);
					infoFechas += fechaUno + "-" + fechaDos + ":" + precioFechasInt + ",";
				}
				infoFechas = infoFechas.substring(0, infoFechas.length() - 1);
				texto += partes[0] + ";" + infoFechas + ";" + partes[2] + ";" + partes[3];
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		
		escritor(texto, "./data/categorias.txt", false);
	}
	
	
	public void modificarCategoriaConductor(Categoria categoria, int valorNuevo) throws IOException {
		Categoria categoriaMod = categorias.get(categoria.getIdCategoria());
		categoriaMod.setPrecioExtraConductor(valorNuevo);
		categorias.put(categoriaMod.getIdCategoria(), categoriaMod);
		
		//Modificamos el .txt
		
		File archivoVehiculos = new File("./data/categorias.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(categoriaMod.getIdCategoria())) {
				texto += partes[0] + ";" + partes[1] + ";" + valorNuevo + ";" + partes[3] + "\n";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/categorias.txt", false);
	}
	
	public void modificarCategoriaEntrega(Categoria categoria, int valorNuevo) throws IOException {
		Categoria categoriaMod = categorias.get(categoria.getIdCategoria());
		categoriaMod.setPrecioExtraEntrega(valorNuevo);
		categorias.put(categoriaMod.getIdCategoria(), categoriaMod);
		
		//Modificamos el .txt
		File archivoCategorias = new File("./data/categorias.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoCategorias));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(categoriaMod.getIdCategoria())) {
				texto += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + valorNuevo + "\n";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/categorias.txt", false);
		
	}
	
	public void eliminarSeguro(String nombreSeguro) throws IOException {

		for(int i=0; i<seguros.size(); i++) {
			if (nombreSeguro.equals(seguros.get(i).getNombre())) {
				seguros.remove(i);
			}
		}
		//Modificamos el .txt
		File archivoSeguros = new File("./data/seguros.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoSeguros));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(nombreSeguro)) {
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine();
		}
		br.close();
		escritor(texto, "./data/seguros.txt", false);
		
	}
	
	
	public void agregarSeguro(Seguro seguro) throws IOException {
		seguros.add(seguro);
		
		//Modificamos el .txt
		File archivoSeguros = new File("./data/seguros.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoSeguros));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			texto += linea + "\n";
			linea = br.readLine();
		}
		br.close();
		texto += seguro.getNombre() + ";" + seguro.getPrecioDiario() + ";" + seguro.getDetalles();
		escritor(texto, "./data/seguros.txt", false);
		
	}
	
	
	
	public Map<ArrayList<LocalDate>, Integer> crearMapaFechas(String fechaInit, String fechaFin, String precio, Map<ArrayList<LocalDate>, Integer> mapaFechas) {
		String[] partesFechaInit = fechaInit.split("/");
		String[] partesFechaFin = fechaFin.split("/");
		LocalDate fechaInicial = LocalDate.of(Integer.parseInt(partesFechaInit[2]), Integer.parseInt(partesFechaInit[1]), Integer.parseInt(partesFechaInit[0]));
		LocalDate fechaFinal = LocalDate.of(Integer.parseInt(partesFechaFin[2]), Integer.parseInt(partesFechaFin[1]), Integer.parseInt(partesFechaFin[0]));
		ArrayList<LocalDate> fechas = new ArrayList<>();
		fechas.add(fechaInicial);
		fechas.add(fechaFinal);
		mapaFechas.put(fechas, Integer.parseInt(precio));
		return mapaFechas;
	}
	
	public void setCategoria(Categoria categoria) throws IOException {
		categorias.put(categoria.getIdCategoria(), categoria);
		
		//Modificamos el .txt
		String texto = "";
		String infoFechas = "";
		Map<ArrayList<LocalDate>, Integer> precioFechas = categorias.get(categoria.getIdCategoria()).getPrecioFechas();
		Set<ArrayList<LocalDate>> tuplasFechas = precioFechas.keySet();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (ArrayList<LocalDate> tupla: tuplasFechas) {
			String fechaUno = tupla.get(0).format(formato);
			String fechaDos = tupla.get(1).format(formato);
			Integer precioFechasInt = precioFechas.get(tupla);
			infoFechas += fechaUno + "-" + fechaDos + ":" + precioFechasInt + ",";
		}
		infoFechas = infoFechas.substring(0, infoFechas.length() - 1);
		texto += categoria.getIdCategoria() + ";" + infoFechas + ";" + categoria.getPrecioExtraConductor() 
				+ ";" + categoria.getPrecioExtraEntrega() + "\n";
		escritor(texto, "./data/categorias.txt", true);
	}
	
	public void agregarUsuario(Usuario user, String cc, String sede, String nombre) throws IOException {
		usuarios.add(user);
		
		Sede sedeUser = null;
		for (int i=0; i<sedes.size(); i++) {
			if (sedes.get(i).getNombre().equals(sede)) {
				sedeUser = sedes.get(i);
				sedes.remove(i);
			}
		}
		ArrayList<Empleado> empleadosSede = sedeUser.getEmpleadosSede();
		Empleado empleadoInteres = new Empleado(nombre, cc, user.getRol(), user);
		empleadosSede.add(empleadoInteres);	
		
		sedeUser.setEmpleados(empleadosSede);
		sedes.add(sedeUser);
		
		//Modificamos usuarios.txt
		String texto2 = "\n" + user.getLogin() + ";" + user.getContrasena()+ ";" + user.getRol();
		escritor(texto2, "./data/usuarios.txt", true);
		
		//Modificamos sedes.txt
		File archivoSedes = new File("./data/sedes.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoSedes));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(sedeUser.getNombre())) {
				texto += linea + ";" + empleadoInteres.getNombre() + "," + empleadoInteres.getCedula() + "," +
						 user.getRol() + "," + user.getLogin() + "," + user.getContrasena() + "\n";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/sedes.txt", false);
		
	}
	
	public void agregarUsuarioCliente(Usuario user) throws IOException {
		usuarios.add(user);
		
		//Modificamos el .txt
		String texto2 = "\n" + user.getLogin() + ";" + user.getContrasena()+ ";" + user.getRol();
		escritor(texto2, "./data/usuarios.txt", true);
		
	}
	
	public void agregarCliente(ClienteNormal cliente) throws IOException {
		clientes.put(cliente.getCedula(), cliente);
		
		//Modificamos el clientes.txt
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String texto = "\n";
		texto += cliente.getNombre() + ";" + cliente.getCedula() + ";" + cliente.getNumeroCelular() + ";";
		texto += cliente.getFechaNacimiento().format(formato) + ";" + cliente.getNacionalidad() + ";";
		texto += cliente.getNumeroLicencia() + ";" + cliente.getPaisExpedicionLicencia() + ";" + cliente.getFechaVencimientoLicencia().format(formato) + ";";
		texto += cliente.getTarjeta().getNumeroTarjeta() + ";" + cliente.getTarjeta().getFechaVencimientoTarjeta().format(formato) + ";";
		texto += cliente.getTarjeta().getCodigoSeguridadTarjeta() + ";" + cliente.getTarjeta().getTipoTarjeta() + ";" + cliente.getUser().getLogin() + ";";
		texto += cliente.getUser().getContrasena() + ";" + cliente.getCorreo() + ";" + "libre" ;
		
		escritor(texto, "./data/clientes.txt", true);
	}
	
	public void eliminarUsuario(Usuario user, String sede) throws IOException {
		for(int i=0; i<usuarios.size(); i++) {
			if (usuarios.get(i).getLogin().equals(user.getLogin())) {
				usuarios.remove(i);
			}
		}
		Sede sedeUser = null;
		for (int i=0; i<sedes.size(); i++) {
			if (sedes.get(i).getNombre().equals(sede)) {
				sedeUser = sedes.get(i);
				sedes.remove(i);
			}
		}
		ArrayList<Empleado> empleadosSede = sedeUser.getEmpleadosSede();
		for(int i=0; i<empleadosSede.size(); i++) {
			if (empleadosSede.get(i).getUsuarioEmpleado().getLogin().equals(user.getLogin())) {
				empleadosSede.remove(i);
			}
		}		
		sedeUser.setEmpleados(empleadosSede);
		sedes.add(sedeUser);
		
		//Modificamos usuarios.txt
		File archivoUsuarios = new File("./data/usuarios.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(user.getLogin())) {
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/usuarios.txt", false);
		
		//Modificamos sedes.txt
		File archivoSedes = new File("./data/sedes.txt");
		BufferedReader br1 = new BufferedReader(new FileReader(archivoSedes));
		String linea1 = br1.readLine();
		String texto1 = "";
		while (linea1 != null) 
		{
			String[] partes = linea1.split(";");
			if (partes[0].equals(sedeUser.getNombre())) {
				texto1 += partes[0] + ";";
				texto1 += partes[1] + ";";
				texto1 += partes[2] + ";";
				for (int i=3; i<partes.length; i++) {
					String[] miniPartes = partes[i].split(",");
					if (miniPartes[3].equals(user.getLogin())) {
					}
					else {
						texto1 += partes[i] + "\n";
					}
				}
			}
			else {
				texto1 += linea1 + "\n";
			}
			linea1 = br1.readLine(); 
		}
		br1.close();
		escritor(texto1, "./data/sedes.txt", false);
	}
	
	
	public Reserva validarReserva(String cc, String id) {
		if (reservas.get(cc+id) != null) {
			return reservas.get(cc+id);
		}
		else {
			return null;
		}
	}
	
	public void guardarReserva(Reserva reserva) throws IOException {
		
		reservas.put(reserva.getCliente().getCedula() + reserva.getIdReserva(), reserva);
		
		//Modificamos reservas.txt
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String texto = "";
		for (String idReserva: reservas.keySet()) {
			Reserva reservaN = reservas.get(idReserva);
			texto += reservaN.getIdReserva() + ";" + reservaN.getCliente().getCedula() + ";" ;
			String textoFechas = "";
			String textoFechaInit = reservaN.getFechaRecogida().format(formato);
			String textoFechaFin = reservaN.getFechaEntrega().format(formato);
			textoFechas += textoFechaInit + "-" + textoFechaFin;
			texto += textoFechas + ";" + reservaN.getSedeRecogida() + ";" + reservaN.getSedeEntrega() + ";";
			texto += reservaN.getConductorPrincipal().getCedula() + ";" + reservaN.getVehiculoArrendado().getPlaca() + ";";
			texto += reservaN.getActiva() + ";";
			if (reservaN.getConductoresAdicionales().size() > 0) {
				for(Conductor condu: reservaN.getConductoresAdicionales()) {
					texto += condu.getCedula() + ",";
				}
				texto = texto.substring(0, texto.length() - 1) + ";";
			}
			else {
				texto += " ;";
			}
			if (reservaN.getSegurosAdicionales().size() > 0) {
				for (Seguro segu: reservaN.getSegurosAdicionales()) {
					texto += segu.getNombre() + ",";
				}
				texto = texto.substring(0, texto.length() - 1);
				
			}
			else {
				texto += " ";
			}
			texto += "\n";
		}
		texto = texto.substring(0, texto.length() - 1);
		escritor(texto, "./data/reservas.txt", false);
		
		//Modificamos vehiculos.txt
		
		File archivoVehiculos = new File("./data/vehiculos.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos));
		String linea = br.readLine();
		String texto2 = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(reserva.getVehiculoArrendado().getPlaca())) {
				texto2 += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" ;
				texto2 += partes[4] + ";" + partes[5] + ";" + partes[6] + ";";
				String textoFechaInit = reserva.getFechaRecogida().format(formato);
				String textoFechaFin = reserva.getFechaEntrega().format(formato);
				if (partes[7].equals("-")) {
					texto2 += textoFechaInit + "-" + textoFechaFin +  ";" ;
				}
				else {
					texto2 += partes[7] + "," + textoFechaInit + "-" + textoFechaFin + ";";
				}
				texto2 += partes[8] + "\n";
			}
			else {
				texto2 += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto2, "./data/vehiculos.txt", false);
		
	}
	

	
	public void agregarConductor(Conductor conductor) {
		conductores.put(conductor.getCedula(), conductor);
		
		//Modificamos el conductores.txt
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String texto = "\n";
		texto += conductor.getNombre() + ";" + conductor.getCedula() + ";" + conductor.getNumeroLicencia() + ";" ;
		texto += conductor.getPaisExpedicionLicencia() + ";" + conductor.getFechaVencimientoLicencia().format(formato) + ";";
		texto += conductor.getFotoLicencia() + ";" + conductor.getNumeroContacto() + ";" + conductor.getCorreo() + "";
		try {
			File archivo = new File("./data/conductores.txt");
			FileWriter fileWriter = new FileWriter(archivo, true);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(texto);
	        bufferedWriter.close();
	        fileWriter.close();
			}catch (IOException e) {
				e.printStackTrace();
	        }		
	}
	
	private ArrayList<Vehiculo> encontrarVehiculosValidos(Sede sede, Categoria categoria) {
		return sede.getInventario().getVehiculos().get(categoria.getIdCategoria());
	}
	
	private boolean compararIntervalos(LocalDate inicioA, LocalDate finA, LocalDate inicioB, LocalDate finB ) {
		//True es que si se cruzan
		return (inicioA.isBefore(finB) || inicioA.isEqual(finB)) && (inicioB.isBefore(finA) || inicioB.isEqual(finA));
	}
	
	private boolean sonTodosFalse(ArrayList<Boolean> lista) {
        for (boolean elemento : lista) {
            if (elemento != false) {
                return false;
            }
        }
        return true;
	}
	
	public String revisarFechasReserva(Sede sede, Categoria categoria, String fechaInicio, String fechaFin) {
		
		ArrayList<Vehiculo> vehiculosValidos = encontrarVehiculosValidos(sede, categoria);
		String[] infoFechaInit = fechaInicio.split("/");
		LocalDate fechaRecogida = LocalDate.of(Integer.parseInt(infoFechaInit[2]), Integer.parseInt(infoFechaInit[1]), 
					Integer.parseInt(infoFechaInit[0]));
		
		String[] infoFechaFin = fechaInicio.split("/");
		LocalDate fechaEntrega = LocalDate.of(Integer.parseInt(infoFechaFin[2]), Integer.parseInt(infoFechaFin[1]), 
					Integer.parseInt(infoFechaFin[0]));
		
		if (vehiculosValidos.size()== 0) {
			return "";
		}
		else {
			String placa = "";
			for (Vehiculo carro: vehiculosValidos) {
				ArrayList<ArrayList<LocalDate>> fechas = carro.getFechasAlquilado();
				ArrayList<Boolean> validos = new ArrayList<>();
				 for(ArrayList<LocalDate> tuplaFechas: fechas) {
					 LocalDate fechaInit = tuplaFechas.get(0);
					 LocalDate fechaFinal = tuplaFechas.get(1);
					 boolean valido = compararIntervalos(fechaRecogida, fechaEntrega, fechaInit, fechaFinal);
					 validos.add(valido);
				 }
				 boolean validez = sonTodosFalse(validos);
				 if (validez == true && carro.getEstado().equals("activo")) {
					 placa = carro.getPlaca();
				 }
			}
			return placa;
		}
		
		
	}
	
	public boolean validarCambioFecha(Reserva reserva, String fechaInit, String fechaFin) {
		Vehiculo carro = reserva.getVehiculoArrendado();
		ArrayList<ArrayList<LocalDate>> fechasAlquilado = carro.getFechasAlquilado();
		LocalDate fechaOriginalInit = reserva.getFechaRecogida();
		LocalDate fechaOriginalFin = reserva.getFechaEntrega();
		int i = 0;
		int index =0 ;
		for (ArrayList<LocalDate> tuplaFechas: fechasAlquilado) {
			if (tuplaFechas.get(0).isEqual(fechaOriginalInit) && tuplaFechas.get(1).isEqual(fechaOriginalFin)) {
				index = i;
			}
			i++;
		}
		fechasAlquilado.remove(index);
		
		String[] partes = fechaInit.split("/");
		LocalDate fechaInit2 =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
		String[] partes2 = fechaFin.split("/");
		LocalDate fechaFin2 =  LocalDate.of(Integer.parseInt(partes2[2]), Integer.parseInt(partes2[1]), Integer.parseInt(partes2[0]));

		ArrayList<Boolean> validos = new ArrayList<>();
		 for(ArrayList<LocalDate> tuplaFechas: fechasAlquilado) {
			 LocalDate fechaInicial = tuplaFechas.get(0);
			 LocalDate fechaFinal = tuplaFechas.get(1);
			 boolean valido = compararIntervalos(fechaInit2, fechaFin2, fechaInicial, fechaFinal);
			 validos.add(valido);
		 }
		 boolean validez = sonTodosFalse(validos);
		 if (validez) {
			 return true;
		 }
		 else {
			 return false;
		 }
	}
	
	public Vehiculo encontrarVehiculo(String placa) {
		Vehiculo vehiculo = null;
		for (int i=0; i<sedes.size(); i++) {
			Map<String, ArrayList<Vehiculo>> vehiculos = sedes.get(i).getInventario().getVehiculos();
			Set<String> listaCategoriasSede = vehiculos.keySet();
			for (String categoria: listaCategoriasSede) {
				ArrayList<Vehiculo> listaVehiculosSedeCat = vehiculos.get(categoria);
				for (int j=0; j< listaVehiculosSedeCat.size();j++) {
					if (placa.equals(listaVehiculosSedeCat.get(j).getPlaca())) {
						vehiculo =  listaVehiculosSedeCat.get(j);
					}
				}			
			}
		}
		return vehiculo;
	}
	
	public ClienteNormal encontrarCliente(String login) {
		ClienteNormal clienteA = null; 
		for (String cliente: clientes.keySet()) {
			if (clientes.get(cliente).getUser().getLogin().equals(login)){
				return clientes.get(cliente);
			}
		}
		return clienteA;
	}
	
	
	public void cerrarVehiculo(Reserva reserva) throws IOException {
		
		
		//Modificamos el vehiculos.txt
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		File archivoVehiculos = new File("./data/vehiculos.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos));
		String linea = br.readLine();
		String texto2 = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(reserva.getVehiculoArrendado().getPlaca())) {
				texto2 += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" ;
				texto2 += partes[4] + ";" + partes[5] + ";";
				texto2 += reserva.getSedeEntrega() + ";";
				String textoFechaFin = reserva.getFechaEntrega().format(formato);
				String[] fechitas = partes[7].split(",");
				String textoFechas = "";
				for (String fecha: fechitas) {
					if (fecha.contains(textoFechaFin)) {
					}
					else {
						textoFechas += fecha + ",";
					}
				}
				if (textoFechas.equals("")) {
					textoFechas += "-";
				}
				else {
					textoFechas = textoFechas.substring(0, textoFechas.length() - 1);
				}
				texto2 += textoFechas + ";" + "mantenimiento" + "\n";	
			}
			else {
				texto2 += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto2, "./data/vehiculos.txt", false);
		
	}
	
	
	public boolean comparacionHoras(String hora1, String hora2) {
		String[] horasSede = hora1.split("-");
		String[] horasCliente = hora2.split("-");
		int inicio1 = Integer.parseInt(horasSede[0]);
        int fin1 = Integer.parseInt(horasSede[1]);
        int inicio2 = Integer.parseInt(horasCliente[0]);
        int fin2 = Integer.parseInt(horasCliente[1]);
        if ((inicio1 < inicio2) && (fin2 < fin1)) {
            return true;
        } else {
            return false;
        }
	}
	
	
	
	public boolean validarHorasDevuelta(Sede sede, String horas, LocalDate fechaFin) {
		DayOfWeek diaDeLaSemana = fechaFin.getDayOfWeek();
        String nombreDelDia = diaDeLaSemana.getDisplayName(TextStyle.FULL, Locale.getDefault());
        String dias = "Monday Tuesday Wednesday Thursday Friday"; 
        String horasSede = "";
        if (dias.contains(nombreDelDia) ) {
        	horasSede = sede.getHorariosAtencion().get("L-V");
        }
        else if(nombreDelDia.equals("Saturday")) {
        	horasSede = sede.getHorariosAtencion().get("S");
        }
        else {
        	horasSede = sede.getHorariosAtencion().get("D-F");
        }
        return comparacionHoras(horasSede, horas);
	}
	
	
	public void eliminarReserva(Reserva reserva) throws IOException {
		String llave = reserva.getCliente().getCedula() + reserva.getIdReserva();
		reservas.remove(llave);
		
		//Modificamos el reservas.txt
		
		File archivoReservas = new File("./data/reservas.txt");
		BufferedReader br = new BufferedReader(new FileReader(archivoReservas));
		String linea = br.readLine();
		String texto = "";
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(reserva.getIdReserva())) {
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/reservas.txt", false);
		
		//Modificamos vehiculos.txt
		
		cerrarVehiculo(reserva);
		
	}
	
	
	public void mandarSacarMantenimiento(String placa, String estado) throws IOException {
		

		//Modificar archivo vehiculos.txt
		String texto = "";
		BufferedReader br = new BufferedReader(new FileReader("./data/vehiculos.txt"));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if (partes[0].equals(placa)) {
				texto += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" + partes[4] + ";" + partes[5] + ";";
				texto += partes[6] + ";" + partes[7] + ";" + estado + "\n";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		escritor(texto, "./data/vehiculos.txt", false);	
	}
	
	public void guardarFactura(File archivo, String texto)
	{
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(archivo.getName()));
			writer.write(texto);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void logPlaca(String placa) throws IOException {
		String texto = "";
		for (String idReserva: reservas.keySet()) {
			Reserva reserva = reservas.get(idReserva);
			if (placa.equals(reserva.getVehiculoArrendado().getPlaca())) {
				texto += reserva.getInformacion();
				texto += "\n -------------------------------------------------- \n";
			}
		}
		
		File archivo = new File("Log_"+placa+".txt");
		guardarFactura(archivo, texto);
	}
	
	
	// Metodos de carga de archivos
	
	public void cargarVehiculos(File archivoVehiculos) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String placa = partes[0];
			String marca = partes[1];
			String modelo = partes[2];
			String color = partes[3];
			String transmicion = partes[4];
			String categoria = partes[5];
			String sede = partes[6];
			Categoria cat = categorias.get(categoria);
			String infoFechas = partes[7];
			String activo = partes[8];
			
			Vehiculo carro = new Vehiculo(placa, marca, modelo, color, transmicion, cat, sede, infoFechas, activo);
			for (int i=0; i < sedes.size(); i++) {
				if (sede.equals(sedes.get(i).getNombre())) {
					Sede sedeInteres = sedes.get(i);
					sedeInteres.getInventario().setVehiculo(carro);
				}
			}
			linea = br.readLine(); 
		}
		br.close();
	}

	
	public void cargarSeguros(File archivoSeguros) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoSeguros));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String precioDiario = partes[1];
			String descripcion = partes[2];
			Seguro seg = new Seguro(nombre, precioDiario, descripcion);
			seguros.add(seg);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarCategorias(File archivoCategorias) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoCategorias));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String idCategoria = partes[0];
			Map<ArrayList<LocalDate>, Integer> precioFechas = new HashMap<>();
			String infoFechas = partes[1];
			String[] partes2 = infoFechas.split(",");
			for (int i = 0; i < partes2.length; i++) {
				String infoUnaFecha = partes2[i];
				String[] partes3 = infoUnaFecha.split(":");
				String precioFecha = partes3[1];
				String[] partes4 = partes3[0].split("-");
				String[] fechaInicioStr = partes4[0].split("/");
				String[] fechaFinStr = partes4[1].split("/");
				
				LocalDate fechaInicio = LocalDate.of(Integer.parseInt(fechaInicioStr[2]), Integer.parseInt(fechaInicioStr[1]),
						Integer.parseInt(fechaInicioStr[0]));
				LocalDate fechaFin = LocalDate.of(Integer.parseInt(fechaFinStr[2]), Integer.parseInt(fechaFinStr[1]),
						Integer.parseInt(fechaFinStr[0]));
				ArrayList<LocalDate> fechas = new ArrayList<>();
				fechas.add(fechaInicio);
				fechas.add(fechaFin);
				precioFechas.put(fechas, Integer.parseInt(precioFecha));
			}
			String precioExtraConductor = partes[2];
			String precioExtraEntrega = partes[3];
			Categoria cat = new Categoria(idCategoria, Integer.parseInt(precioExtraEntrega), Integer.parseInt(precioExtraConductor),
					precioFechas);
			categorias.put(idCategoria, cat);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarSedes(File archivoSedes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoSedes));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String ubicacion = partes[1];
			String[] partes2 = partes[2].split("/");
			Map<String, String> horarioAtencion = new HashMap<>();
			horarioAtencion.put("L-V", partes2[0]);
			horarioAtencion.put("S", partes2[1]);
			horarioAtencion.put("D-F", partes2[2]);
			Sede sedeNueva = new Sede(nombre, ubicacion, horarioAtencion);
			for (int i=3; i < partes.length; i++) {
				String[] datosEmpleado = partes[i].split(",");
				String login = datosEmpleado[3];
				String password = datosEmpleado[4];
				Usuario user = new Usuario(login, password, datosEmpleado[2]);
				if (datosEmpleado[2].equals("admin")) {
					sedeNueva.setAdministradorLocal(datosEmpleado[0], datosEmpleado[1],user);
					
				}
				else {
					sedeNueva.setEmpleado(datosEmpleado[0], datosEmpleado[1], datosEmpleado[2], user);
				}
			}
			sedes.add(sedeNueva);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarUsuarios(File archivoUsuarios) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String login = partes[0];
			String password = partes[1];
			String rol = partes[2];
			Usuario user = new Usuario(login, password, rol);
			usuarios.add(user);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarClientes(File archivoClientes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoClientes));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String cedula = partes[1];
			String numeroCelular = partes[2];
			String fechaNacimiento = partes[3];
			String nacionalidad = partes[4];
			String numeroLicencia =  partes[5];
			String paisExpedicionLicencia =  partes[6];
			String fechaVencimientoLicencia = partes[7];
			String numeroTarjeta = partes[8];
			String fechaVencimientoTarjeta = partes[9];
			String codigoSeguridadTarjeta = partes[10];
			String tipoTarjeta = partes[11];
			Usuario user = new Usuario(partes[12], partes[13], "cliente");
			String correo = partes[14];
			String estadoTarjeta = partes[15];
			
			ClienteNormal cliente = new ClienteNormal(nombre, cedula, numeroCelular, fechaNacimiento, nacionalidad, numeroLicencia, 
					paisExpedicionLicencia, fechaVencimientoLicencia,numeroTarjeta, fechaVencimientoTarjeta,codigoSeguridadTarjeta,
					tipoTarjeta, user, correo, estadoTarjeta);
			clientes.put(cedula, cliente);
			linea = br.readLine(); 
		}
		br.close();
		
	}
	
	public void cargarReservas(File archivoReservas) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoReservas));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String idReserva = partes[0];
			String cedulaCliente = partes[1];
			String[] infoFechas = partes[2].split("-");
			String infoFechaInit = infoFechas[0];
			String infoFechaFin = infoFechas[1];
			String sedeInicio = partes[3];
			String sedeFin = partes[4];
			String cedulaConductor = partes[5];
			String placaCarro = partes[6];
			
			ClienteNormal cliente = clientes.get(cedulaCliente);
			Map<String, ArrayList<Vehiculo>> inventario = new HashMap<>();
			for (Sede sede: sedes) {
				if (sede.getNombre().equals(sedeInicio)) {
					inventario = sede.getInventario().getVehiculos();
				}
			}
			Vehiculo vehiculo = null;
			for (String cat: inventario.keySet()) {
				for (Vehiculo carro: inventario.get(cat)) {
					if (carro.getPlaca().equals(placaCarro)) {
						vehiculo = carro;
					}
				}
			}

			
			Conductor conductor = conductores.get(cedulaConductor);
			boolean cobroExtraEntrega = false;
			if (sedeInicio.equals(sedeFin)) {
			}
			else {
				cobroExtraEntrega = true;
			}
			
			String activa = partes[7];
			Reserva reserva = new Reserva(idReserva , infoFechaInit, infoFechaFin, conductor, vehiculo, cliente,
					cobroExtraEntrega, sedeInicio, sedeFin, activa);

			
			if (!partes[8].equals(" ")) {
				 for(String cc: partes[8].split(",")) {
					 Conductor condu = conductores.get(cc);
					 reserva.setConductorAdicional(condu, false);
				 }
			}

			if (!partes[9].equals(" ")) {
				Seguro seguro = null;
				for(String seg: partes[9].split(",")) {
					for(int i=0; i<seguros.size();i++) {
						if(seguros.get(i).getNombre().equals(seg)) {
							seguro = seguros.get(i);
						}
					}
					 reserva.setSeguroAdicional(seguro);
				 }
			}
			
			reservas.put(cedulaCliente + idReserva, reserva);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarConductores(File archivoConductores) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoConductores));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String cedula = partes[1];
			String numeroLicencia = partes[2];
			String paisLicencia = partes[3];
			String fechaLicencia = partes[4];
			String imagen = partes[5];
			String celular = partes[6];
			String correo = partes[7];
			Conductor condu = new Conductor(nombre, cedula,numeroLicencia,paisLicencia,fechaLicencia, 
					imagen, celular, correo);
			conductores.put(cedula, condu);
			linea = br.readLine(); 
		}
		br.close();
	}
	
	public void cargarArchivos(File archivoVehiculos, File archivoSedes, File archivoUsuarios, File archivoCategorias, File archivoSeguros,
			File archivoClientes, File archivoReservas, File archivoConductores) throws IOException {
		cargarSedes(archivoSedes);
		cargarCategorias(archivoCategorias);
		cargarVehiculos(archivoVehiculos);
		cargarSeguros(archivoSeguros);
		cargarUsuarios(archivoUsuarios);
		cargarClientes(archivoClientes);
		cargarConductores(archivoConductores);
		cargarReservas(archivoReservas);
	}

	
}
