package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelo.Administrador;
import modelo.Categoria;
import modelo.ClienteNormal;
import modelo.Conductor;
import modelo.EmpresaVehiculos;
import modelo.Reserva;
import modelo.Sede;
import modelo.Seguro;
import modelo.Usuario;
import modelo.Vehiculo;

public class Aplicacion {
	
	
	//Atributos 
	static Usuario userAdmin = new Usuario("dlmanrique","1234","adminGeneral");
	private static EmpresaVehiculos empresa = new EmpresaVehiculos("Car Rental", "Leonardo Manrique", "1001168408", userAdmin);
	
	//Metodos
	
	public static String inputEnter(String mensaje)
	{
		try
		{
			System.out.print(mensaje);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static ArrayList<String> pedirUsuario() throws IOException {
		Boolean registrado = false;
		String rol = "";
		String login = "";
		while (!registrado) {
			String input = inputEnter("\nPor favor digite su login: ");
			if(input == null) {
				System.out.println("\nLa entrada no puede estar vacia.");
			}
			else {
				login = input;
				String password = inputEnter("\nPor favor digite la contraseña: ");
				if(password == null) {
					System.out.println("\nLa contraseña no puede estar vacia.");
				}
				else {
					rol = empresa.verificarUsuario(login, password);
					if (rol.equals("")) {
						System.out.println("\nUsuario o contraseña incorrectos");
					}
					else {
						registrado = true;
					}
				}
			}
		}
		ArrayList<String> info = new ArrayList<>();
		info.add(login);
		info.add(rol);
		return info;
	}
	
	private static void mostrarOpcionesDisponiblesTotales(String rol, String login) {
		System.out.println("\n\tOpciones disponibles");
		if (rol.equals("adminGeneral")) {
			System.out.println("1) Registrar o eliminar un vehiculo");
			System.out.println("2) Registrar o eliminar sede");
			System.out.println("3) Modificar precios para una categoria de vehiculo");
			System.out.println("4) Agregar o eliminar información de un seguro");
			System.out.println("5) Generar archivo .log de un vehiculo");
			System.out.println("6) Registrar nueva categoria");
			System.out.println("7) Log out");
			System.out.println("8) Mover un carro de sede");
		}
		else if (rol.equals("admin")) {
			System.out.println("1) Agregar usuario");
			System.out.println("2) Eliminar usuario");
			System.out.println("3) Log out");
		}
		else if (rol.equals("recepcion")){
			System.out.println("1) Registrar entrega de un vehiculo");
			System.out.println("2) Registrar devolución de un vehiculo");
			System.out.println("3) Log out");
		}
		else if (rol.equals("cliente")){
			System.out.println("1) Crear reserva");
			System.out.println("2) Modificar reserva");
			System.out.println("3) Eliminar reserva");
			System.out.println("4) Log out");
		}
		else {
			System.out.println("1) Reportar vehiculo a mantenimiento");
			System.out.println("2) Actualizar estado vehiculo (sacarlo de mantenimiento)");
			System.out.println("3) Log out");
		}
	}
	
	private static void ejecutarOpciones(Integer opcionSeleccionada, String rol, String login) throws IOException {
		boolean continuar = true;
		while(continuar) {
		if (rol.equals("adminGeneral")) {
			//Registrar o eliminar un nuevo vehiculo
			if (opcionSeleccionada == 1) {
				String opcion  = inputEnter("\nPor favor digite E si va a eliminar o A si va a agregar: ");
				if (opcion == null) {
					System.out.print("\nLa entrada no puede estar vacía.");
				}
				if (opcion.equals("E")) {
					String placaEliminar  = inputEnter("\nPor favor digite la placa del vehiculo que desea eliminar: ");
					if (placaEliminar == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					ArrayList<String> posiblesVehiculos = empresa.listadoPlacasVehiculos();
					boolean encontrado = false;
					for (String placa: posiblesVehiculos) {
						if (placa.equals(placaEliminar)) {
							empresa.eliminarVehiculo(placaEliminar);
							System.out.print("\nEl vehiculo ha sido eliminado correctamente");
							encontrado = true;
							continuar = false;
						}
					}
					if (encontrado == false) {
						System.out.print("\nNo existe un vehiculo registrado que tenga la placa ingresada. Las placas de los vehiculos de la empresa son: ");
						for (String placa: posiblesVehiculos) {
							System.out.print("\n" + placa);
						}
						continuar = false;
					}
					
				}
				else if(opcion.equals("A")) {
					String placaNueva = inputEnter("\nPor favor digite la placa del vehiculo que desea añadir: ");
					if (placaNueva == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}

					String marcaNueva = inputEnter("\nPor favor digite la marca del vehiculo que desea añadir: ");
					if (marcaNueva == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					
					String modeloNuevo = inputEnter("\nPor favor digite el modelo del vehiculo que desea añadir: ");
					if (modeloNuevo == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					
					String colorNuevo = inputEnter("\nPor favor digite el color del vehiculo que desea añadir: ");
					if (colorNuevo == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					
					String transNuevo = inputEnter("\nPor favor digite el tipo de transmicion del vehiculo que desea añadir: ");
					if (transNuevo == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					
					String sedeNuevo = inputEnter("\nPor favor digite la sede donde esta el vehiculo que desea añadir: ");
					if (sedeNuevo == null) {
						System.out.print("\nLa entrada no puede estar vacía.");
					}
					ArrayList<Sede> posiblesSedes = empresa.getSedes();
					boolean encontradoSede = false;
					for (Sede sede: posiblesSedes ) {
						String nombre = sede.getNombre();
						if (sedeNuevo.equals(nombre)) {
							encontradoSede = true;
						}
					}
					if (encontradoSede == false) {
						System.out.print("\nNo se ha encontrado esta sede. Las sedes de la empresa son: ");
						ArrayList<Sede> sedes = empresa.getSedes();
						for (int i=0; i<sedes.size(); i++) {
							System.out.print("\n"+sedes.get(i).getNombre());
						}
						System.out.print("\nIngrese una sede valida o registre la nueva sede antes.");
						continuar = false;
					}
					else {
						String categoriaNuevo = inputEnter("\nPor favor digite la categoria del vehiculo que desea añadir: ");
						if (categoriaNuevo == null) {
							System.out.print("\nLa entrada no puede estar vacía.");
						}
						
						
						Map<String, Categoria> posiblesCategorias = empresa.getCategorias();
						boolean encontrado = false;
						for (String key : posiblesCategorias.keySet()) {
							if (categoriaNuevo.equals(key)) {
								encontrado = true;
							}
						}
						
						if (encontrado == false) {
							System.out.print("\n Ingrese una categoria valida o registre la nueva categoria antes. Las categorias validas son:");
							Set<String> cats = posiblesCategorias.keySet();
							for (String cat: cats) {
								System.out.print("\n" + cat);
							}
							continuar = false;
						}
						else if (encontrado == true){
							Categoria catNuevo = empresa.consultarCategoria(categoriaNuevo);
							Vehiculo carro = new Vehiculo(placaNueva, marcaNueva, modeloNuevo, colorNuevo, transNuevo, catNuevo, sedeNuevo, "-" ,"activo");
							empresa.anadirVehiculo(sedeNuevo,carro);
							System.out.print("\n El vehiculo ha sido añadido correctamente");
							continuar = false;
						}
					}
				}
				else {
					System.out.print("\nIngrese una opción valida.");
				}
			}
			else if (opcionSeleccionada == 2) {
				String opcion  = inputEnter("\nPor favor digite E si va a eliminar o A si va a agregar: ");
				if (opcion == null) {
					System.out.print("\nLa entrada no puede estar vacía.");
				}
				if (opcion.equals("A")) {
					
					// Registrar sede
					
					String nombre = inputEnter("\nPor favor digite el nombre de la sede que desea agregar: ");
					String ubicacion = inputEnter("\nPor favor digite la ubicacion de la sede que desea agregar: ");
					ArrayList<String> dias = new ArrayList<>();
					dias.add("Lunes-Viernes");
					dias.add("Sabado");
					dias.add("Domingos y Festivos");
					dias.add("L-V");
					dias.add("S");
					dias.add("D-F");
					Map<String, String> horarios = new HashMap<>();
					
					for (int i=0; i<3; i++)
					{
						String apertura = inputEnter("Ingrese la hora de apertura de la nueva sede para los dias " + dias.get(i) + 
								                        " (formato de 24 horas): ");
						
						String cierre = inputEnter("Ingrese la hora de cierre de la nueva sede para los dias " + dias.get(i) + 
		                        					" (formato de 24 horas): ");
						horarios.put(dias.get(i+3), apertura + "-" + cierre);
						
					}
					String nombreAdminSede = inputEnter("\n Ingrese el nombre del administrador de la nueva sede: ");
					String ccAdminSede = inputEnter("\n Ingrese la cedula del administrador de la nueva sede: ");
					String confirmacionUsuario = inputEnter("\n El administrador ya tiene usuario, digite 'S' para si y 'N' para no: ");
					
					Usuario userAdmin = null;
					if (confirmacionUsuario.equals("S")) {
						String loginAdmin2 = inputEnter("\n Ingrese el login del administrador de la nueva sede: ");
						ArrayList<Usuario> users = empresa.getUsuarios();
						for (Usuario user: users) {
							if (user.getLogin().equals(loginAdmin2)) {
								userAdmin = empresa.getUsuario(user.getLogin());
							}
						}
					}
					else {
						System.out.print("\nIngrese los datos para crear el usuario del administrador");
						String loginAdminSede = inputEnter("\n Ingrese el login del administrador de la nueva sede: ");
						String passwordAdminSede = inputEnter("\n Ingrese la contraseña del administrador de la nueva sede: ");
						userAdmin = new Usuario(loginAdminSede, passwordAdminSede, "admin");
						empresa.setUsuario(userAdmin);
						}
					
					Administrador admin = new Administrador(nombreAdminSede, ccAdminSede, userAdmin, nombre);
					empresa.anadirSede(nombre, ubicacion, horarios, admin, userAdmin);
					System.out.print("\nLa sede ha sido añadida correctamente");
					continuar = false;
				}
				else if (opcion.equals("E")) {
					String nombre = inputEnter("\nPor favor digite el nombre de la sede que desea eliminar: ");
					ArrayList<Sede> posiblesSedes = empresa.getSedes();
					boolean encontrado = false;
					for (Sede sede: posiblesSedes) {
						if (sede.getNombre().equals(nombre)) {
							encontrado = true;
						}
					}
					
					if (encontrado == true) {
						empresa.eliminarSede(nombre);
						System.out.print("\nLa sede ha sido eliminada correctamente");
						continuar = false;
					}
					else if (encontrado == false) {
						System.out.print("\nNo se ha podido encontrar la sede que desea eliminar, las sedes de la empresa son: ");
						for (Sede sede: posiblesSedes) {
							System.out.print("\n" + sede.getNombre());
						}
					}
					
				}
				
			}
			else if (opcionSeleccionada == 3) {
				// Modificar precios para un tipo de vehiculo
				Map<String, Categoria> posiblesCategorias = empresa.getCategorias();
				Set<String> cats = posiblesCategorias.keySet();
				ArrayList<String> catsArr = new ArrayList<>(cats);
				Integer i = 0;
				for (String cat: catsArr) {
					i++;
					System.out.print("\n" + i + " " + cat);
				}
				String categoriaSeleccionada = inputEnter("\nIngrese el numero de la categoria de vehiculo que desea modificar: ");
				
				Categoria categoriaMod = posiblesCategorias.get(catsArr.get(Integer.parseInt(categoriaSeleccionada)-1));
				System.out.print("\n 1) Precio diario de alquiler por fechas.");
				System.out.print("\n 2) Precio diario conductor auxiliar.");
				System.out.print("\n 3) Precio de entrega en sede no autorizada.");
				String opcionMod = inputEnter("\n Ingrese el numero del parametro que desea cambiar: ");
				
				int opcionModInt = Integer.parseInt(opcionMod);
				if (opcionModInt == 1) {
					boolean continuar2 = true;
					String opcionEdit = inputEnter("\n Si desea añadir un nuevo rango de fechas digite 'E', si quiere cambiar todos los "
							+ "rangos de la categoria existente digite 'N': ");
					Map<ArrayList<LocalDate>, Integer> mapaFechas = new HashMap<>();
					if (opcionEdit.equals("N")) {
						mapaFechas = new HashMap<>();
						
					}
					else if (opcionEdit.equals("E")) {
						mapaFechas = categoriaMod.getPrecioFechas();
					}
					else {
						System.out.print("\n Ingrese una opcion valida.");
						continuar2 = false;
					}
					System.out.print("\n A continuacion se le van a mostrar las opciones para modificar las tarifas por fechas "
							+ "para la categoria deseada. \n Simplemente ingrese sucesivamente las fechas iniciales y finales (de la forma 'dd/mm/yyyy') "
							+ "y posteriormente ingrese el precio para esos dias. \n Asegurese de que todos los dias del año cuenten con un precio."
							);
					while (continuar2) {
						String fechaInit = inputEnter("\n Ingrese la fecha inicial del rango de fechas que quiere definir (formato 'dd/mm/yyyy'. Ejemplo: '02/11/2023'): ");
						String fechaFin = inputEnter("\n Ingrese la fecha final del rango de fechas que quiere definir (formato 'dd/mm/yyyy'. Ejemplo: '02/11/2023'): ");
						String precio = inputEnter("\n Ingrese el precio correspondiente para las fechas indicadas anteriormente: ");
						
						empresa.modificarCategoriaPreciosAnadirUno(categoriaMod.getIdCategoria() ,fechaInit, fechaFin, precio, mapaFechas);
						System.out.print("\n La modificacion se hizo exitosamente");
						
						String salida = inputEnter("\n Si finalizo de ingresar las tarifas por fechas presione 'F', si quiere continuar presione 'Enter': ");
						if (salida.equals("F")) {
							continuar2 = false;
						}
					}
				}
				else if(opcionModInt == 2) {
					String valorNuevo = inputEnter("\n Ingrese el nuevo precio diario conductor auxiliar: ");
					empresa.modificarCategoriaConductor(categoriaMod, Integer.parseInt(valorNuevo));
					System.out.print("\n El precio fue actualizado correctamente.");
					continuar = false;
				}
				else if(opcionModInt == 3) {
					String valorNuevo = inputEnter("Ingrese el nuevo precio de entrega en sede no autorizada: ");
					empresa.modificarCategoriaEntrega(categoriaMod, Integer.parseInt(valorNuevo));
					System.out.print("\n El precio fue actualizado correctamente.");
					continuar = false;
				}
				else {
					System.out.print("\n Ingrese una opcion valida.");
					continuar = false;
				}
			}
			else if(opcionSeleccionada == 4) {
				//Agregar o eliminar seguro
				
				String opcion  = inputEnter("\nPor favor digite E si va a eliminar o A si va a agregar: ");
				if (opcion == null) {
					System.out.print("\nLa entrada no puede estar vacía.");
				}
				if (opcion.equals("E")) {
					ArrayList<Seguro> posiblesSeguros = empresa.getSeguros();
					int i=0; 
					for(Seguro seguro: posiblesSeguros) {
						i++;
						System.out.print("\n" + i + " " + seguro.getNombre());
					}
					String seguroEliminarIndice  = inputEnter("\nPor favor digite el numero del seguro que desea eliminar: ");
					empresa.eliminarSeguro(posiblesSeguros.get(Integer.parseInt(seguroEliminarIndice)-1).getNombre());
					System.out.print("\n El seguro fue eliminado correctamente.");
					continuar = false;
					
				}
				else if(opcion.equals("A")) {
					String nombreSeguro = inputEnter("\n Ingrese el nombre del nuevo seguro: ");
					String precioSeguro = inputEnter("\n Ingrese el precio del nuevo seguro: ");
					String detallesSeguro = inputEnter("\n Ingrese detalles sobre el nuevo seguro: ");
					empresa.agregarSeguro(new Seguro(nombreSeguro, precioSeguro, detallesSeguro));
					continuar = false;
					System.out.print("\n El seguro fue añadido correctamente.");
				}
				else {
					System.out.print("\n Selecione una opcion correcta.");
					continuar = false;
				}
			}
			else if(opcionSeleccionada == 5) {
				String placa = inputEnter("\n Ingrese la placa del vehiculo: ");
				empresa.logPlaca(placa);
				System.out.print("\n Se ha generado el archivo log del vehiculo solicitado.");
				
			}
			else if(opcionSeleccionada == 6) {
				//Agregar una categoria 
				
				String nombreCat = inputEnter("\n Ingrese el nombre de la nueva categoria: ");
				System.out.print("\n A continuacion se le van a mostrar las opciones para modificar las tarifas por fechas "
						+ "para la categoria deseada. \n Simplemente ingrese sucesivamente las fechas iniciales y finales (de la forma 'dd/mm/yyyy') "
						+ "y posteriormente ingrese el precio para esos dias. \n Asegurese de que todos los dias del año cuenten con un precio."
						);
				boolean continuar2 = true;
				Map<ArrayList<LocalDate>, Integer> mapaFechas = new HashMap<>();
				while (continuar2) {
					String fechaInit = inputEnter("\n Ingrese la fecha inicial del rango de fechas que quiere definir (formato 'dd/mm/yyyy'. Ejemplo: '02/11/2023'): ");
					String fechaFin = inputEnter("\n Ingrese la fecha final del rango de fechas que quiere definir (formato 'dd/mm/yyyy'. Ejemplo: '02/11/2023'): ");
					String precio = inputEnter("\n Ingrese el precio correspondiente para las fechas indicadas anteriormente: ");
					mapaFechas = empresa.crearMapaFechas(fechaInit, fechaFin, precio, mapaFechas);
					String salida = inputEnter("\n Si finalizo de ingresar las tarifas por fechas presione 'F', si quiere continuar presione 'Enter': ");
					if (salida.equals("F")) {
						continuar2 = false;
					}
				}
				String precioCondu = inputEnter("\n Ingrese el precio diario por conductor extra de la nueva categoria: ");
				String precioEntrega = inputEnter("\n Ingrese el precio entregar el vehiculo en otra sede de la nueva categoria: ");
				empresa.setCategoria(new Categoria(nombreCat,Integer.parseInt(precioEntrega), Integer.parseInt(precioCondu), mapaFechas));
				System.out.print("\n Se ha agregado una categoria.");
				continuar = false;
			}
			else if(opcionSeleccionada == 7) {
				continuar = false;
				System.out.print("\n Saliendo del sistema.");
			}
			else if (opcionSeleccionada == 8) {
				String placaMover = inputEnter("\n Ingrese la placa del carro que desea cambiar de sede: ");
				continuar = false;
			}
			else {
				System.out.print("\n Ingrese una opcion valida.");
			}
		}
		else if(rol.equals("admin")) {
			//Agregar usuario
			if (opcionSeleccionada == 1) {
				login = inputEnter("\n Ingrese el login del nuevo usuario: ");
				String password = inputEnter("\n Ingrese la contraseña del nuevo usuario: ");
				System.out.print("\n 1) Empleado de la recepción.");
				System.out.print("\n 2) Empleado de limpieza de carros.");
				String rolUser = inputEnter("\n Ingrese el numero del rol del nuevo usuario: ");
				String rol1 = "";
				if (rolUser.equals("1")) {
					rol1 = "recepcion";
				}
				else if(rolUser.equals("2")) {
					rol1 = "limpiador"; 
				}
				else {
					System.out.print("\n Ingrese un valor valido.");
				}
				Usuario user = new Usuario(login, password, rol1);
				String nombre = inputEnter("\n Ingrese el nombre del nuevo usuario: ");
				String cc = inputEnter("\n Ingrese el numero de cedula del nuevo usuario: ");
				ArrayList<Sede> posiblesSedes = empresa.getSedes();
				for (int i=0; i<posiblesSedes.size(); i++) {
					System.out.print("\n " + (i+1) + " " + posiblesSedes.get(i).getNombre() );
				}
 				String sedeIndex = inputEnter("\n Ingrese el numero de la sede en la que va a trabajar el nuevo usuario: ");
 				String sede = posiblesSedes.get(Integer.parseInt(sedeIndex)-1).getNombre();
				empresa.agregarUsuario(user, cc, sede, nombre);
				System.out.print("\n El usuario ha sido agregado correctamente");
				continuar = false;
			}
			else if(opcionSeleccionada == 2) {
				
				ArrayList<Usuario> posiblesUsuarios = empresa.getUsuarios();
				for(int i=0; i<posiblesUsuarios.size(); i++) {
					System.out.print("\n" + (i+1) + " " + posiblesUsuarios.get(i).getLogin());
				}
				String loginId = inputEnter("\n Ingrese el numero del login del usuario que quiere eliminar: ");
				ArrayList<Sede> posiblesSedes = empresa.getSedes();
				for (int i=0; i<posiblesSedes.size(); i++) {
					System.out.print("\n " + (i+1) + " " + posiblesSedes.get(i).getNombre() );
				}
 				String sedeIndex = inputEnter("\n Ingrese el numero de la sede en la que trabaja el nuevo usuario: ");
 				String sede = posiblesSedes.get(Integer.parseInt(sedeIndex)-1).getNombre();
				Usuario userEliminar = posiblesUsuarios.get(Integer.parseInt(loginId)-1);
				empresa.eliminarUsuario(userEliminar, sede);
				System.out.print("\n El usuario ha sido eliminado correctamente");
				continuar = false;
			}
			else if(opcionSeleccionada == 3) {
				continuar = false;
				System.out.print("\n Saliendo del sistema.");
			}
			else {
				System.out.print("\n Ingrese una opcion valida.");
			}
		}
		else if(rol.equals("recepcion")) {
			if (opcionSeleccionada == 1) {
				//Registrar recogida de un vehiculo
				String cc = inputEnter("\n Digite la cedula del cliente que realizo la reserva: ");
				String id = inputEnter("\n Digite el Id de reserva del cliente: ");
				Reserva reserva = empresa.validarReserva(cc,id);
				if (reserva == null) {
					System.out.print("\n La reserva no existe o el numero de cedula es incorrecto.");
					continuar = false;
				}
				else {
					System.out.print("\n La informacion de la reserva es: ");
					System.out.print(reserva.getInformacion());
					boolean cont = true;
					while (cont) {
						System.out.print("\n 1) Agregar conductor extra.");
						System.out.print("\n 2) Agregar un seguro extra.");
						System.out.print("\n 3) Pago del 70% y entrega del vehiculo.");
						String opt = inputEnter("\n Digite la opcion que desea realizar: ");
						if (Integer.parseInt(opt) == 1) {
							String nombre = inputEnter("\n Ingrese el nombre del conductor: ");
							String cedula = inputEnter("\n Ingrese la cedula del conductor: ");
							String numeroLicencia = inputEnter("\n Ingrese el numero de licencia del conductor: ");
							String paisExpedicionLicencia = inputEnter("\n Ingrese el pais de expedicion de la licencia: ");
							String fechaVencimientoLicencia = inputEnter("\n Ingrese la fecha de vencimiento de la "
																		+ "licencia 'formato (dd/mm/yyyy)': ");
							String fotoLicencia = inputEnter("\n Ingrese el path de la foto de la licencia: ");
							String numeroContacto = inputEnter("\n Ingrese el numero de contacto del conductor: ");
							String correo = inputEnter("\n Ingrese el correo del conductor: ");
							Conductor conduAdicional = new Conductor(nombre,cedula,numeroLicencia,paisExpedicionLicencia,fechaVencimientoLicencia,
														fotoLicencia,numeroContacto,correo);
							reserva.setConductorAdicional(conduAdicional, true);
							empresa.guardarReserva(reserva);
							System.out.print("\n El conductor ha sido agregado.\n");
							
						}
						else if(Integer.parseInt(opt) == 2) {
							ArrayList<Seguro> seguros = empresa.getSeguros();
							for (int i=0; i< seguros.size(); i++) {
								System.out.print("\n " + (i+1) + seguros.get(i).getInformacion());
							}
							String seguro = inputEnter("\n Ingrese el numero del seguro que desea añadir: ");
							reserva.setSeguroAdicional(seguros.get(Integer.parseInt(seguro)-1));
							empresa.guardarReserva(reserva);
							System.out.print("\n El seguro ha sido agregado.\n");
						}
						else if(Integer.parseInt(opt) == 3) {
							int precioTotal = reserva.getPrecioTotal();
							System.out.print("\n El cliente debe pagar: " + precioTotal*0.7);
							String siPago = inputEnter("\n Presione 'S' si ya se realizo el pago. ");
							if(siPago.equals("S")) {
								ClienteNormal cliente = reserva.getCliente();
								cliente.bloquearTarjeta();
								empresa.guardarReserva(reserva);
								cont = false;
								continuar = false;
							}
							else {
								System.out.print("\n Ingrese una opcion valida.");
							}
							
						}
						else {
							System.out.print("\n Ingrese una opcion valida.");
						}
					}
					
				}
				
				
			}
			else if(opcionSeleccionada == 2) {
				//Registrar devolucion de un vehiculo
				String cc = inputEnter("\n Digite la cedula del cliente que realizo la reserva: ");
				String id = inputEnter("\n Digita el Id de la reserva: ");
				Reserva reserva = empresa.validarReserva(cc, id);
				if (reserva == null) {
					System.out.print("\n La reserva no existe o el numero de cedula es incorrecto.");
					continuar = false;
				}
				else {
					System.out.print("\n La informacion de la reserva es: ");
					System.out.print(reserva.getInformacion());
					String fin = inputEnter("\n Presione 'F' para formalizar la devolucion del vehiculo: ");
					if(fin.equals("F")) {
						reserva.finReserva();
						empresa.guardarReserva(reserva);
						empresa.cerrarVehiculo(reserva);
						continuar = false;
					}
					else {
						System.out.print("\n Ingrese una opcion valida.");
					}
					
				}
			}
			else if(opcionSeleccionada == 3) {
				System.out.print("\n Saliendo del sistema.");
				continuar = false;
			}
		}
		
		else if(rol.equals("cliente")) {
			//Crear una reserva
			if (opcionSeleccionada == 1) {
				boolean cont = true;
				while (cont) {
					ArrayList<Sede> sedes = empresa.getSedes();
					for (int i=0; i<sedes.size(); i++) {
						System.out.print("\n " + (i+1) + "  "+sedes.get(i).getNombre());
					}
					String numeroSede = inputEnter("\n Ingrese el numero de la sede donde desea reservar un vehiculo: ");
					Sede sedeInicio = sedes.get(Integer.parseInt(numeroSede) - 1);
					System.out.print("\n Categorias de vehiculo posibles para reservar en la sede seleccionada: ");
					int i = 0; 
					Set<String> posiblesCats = sedeInicio.getInventario().getVehiculos().keySet();
					for (String cat: posiblesCats) {
						i++;
						System.out.print("\n "+ i + " "+cat);
					}
					String catCont = inputEnter("\n Ingrese el numero de la categoria que desea seleccionar. Si quiere revisar otra sede digite 'O': ");
					if (catCont.equals("O")) {
						System.out.print("\n Volviendo el menu de sedes");
					}
					else {
						List<String> listaCats = new ArrayList<String>(posiblesCats);
						Categoria categoria = empresa.getCategorias().get(listaCats.get(Integer.parseInt(catCont)-1));
						String fechaInicio = inputEnter("\n Ingrese la fecha de inicio de su reserva 'formato (dd/mm/yyyy)': ");
						String fechaFin = inputEnter("\n Ingrese la fecha de fin de su reserva 'formato (dd/mm/yyyy)': ");
						String placaVehiculoValido = empresa.revisarFechasReserva(sedeInicio, categoria, fechaInicio, fechaFin);
						if (placaVehiculoValido.equals("")) {
							System.out.print("\n No hay vehiculos disponibles para esta fecha. Seleccione otra sede o categoria");
						}
						else {
							System.out.print("\n Se ha encontrado un vehiculo para estas fechas.");
							String sedeRecogida = sedeInicio.getNombre();
							ArrayList<Sede> sedes2 = empresa.getSedes();
							for (int i1=0; i1<sedes.size(); i1++) {
								System.out.print("\n " + (i1+1) + "  "+sedes2.get(i1).getNombre());
							}
							String numeroSedeFin = inputEnter("\n Ingrese el numero de la sede donde desea devolver un vehiculo: ");
							Sede sedeFin = sedes.get(Integer.parseInt(numeroSedeFin) - 1);
							String sedeEntrega = sedeFin.getNombre();
							String conduPrinci = inputEnter("\n Si el cliente que hace la reserva a ser el condcutor principal digite 'S', si desea registrar otra "
									+ "persona como conductor principal digite 'N': ");
							
							ClienteNormal cliente = empresa.encontrarCliente(login);
							Conductor conductor = null;
							if (conduPrinci.equals("S")) {
								DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								String fechaVencimientoLicencia = cliente.getFechaVencimientoLicencia().format(formato);
								
								
								conductor = new Conductor(cliente.getNombre(),cliente.getCedula(), 
										cliente.getNumeroLicencia(), cliente.getPaisExpedicionLicencia(), fechaVencimientoLicencia,
										"fotico.png", cliente.getNumeroCelular(), cliente.getCorreo());
							}
							else if(conduPrinci.equals("N")) {
								String nombre = inputEnter("\n Ingrese el nombre del conductor: ");
								String cedula = inputEnter("\n Ingrese la cedula del condcutor: ");
								String numeroLicencia = inputEnter("\n Ingrese el numero de licencia del condcutor: ");
								String paisExpedicionLicencia = inputEnter("\n Ingrese el pais de expedicion de la licencia: ");
								String fechaVencimientoLicencia = inputEnter("\n Ingrese la fecha de vencimiento de la "
																			+ "licencia 'formato (dd/mm/yyyy)': ");
								String fotoLicencia = inputEnter("\n Ingrese el path de la foto de la licencia: ");
								String numeroContacto = inputEnter("\n Ingrese el numero de contacto del conductor: ");
								String correo = inputEnter("\n Ingrese el correo del conductor: ");
								conductor = new Conductor(nombre,cedula,numeroLicencia,paisExpedicionLicencia,fechaVencimientoLicencia,
															fotoLicencia,numeroContacto,correo);
								empresa.agregarConductor(conductor);
							}
							else {
								System.out.print("\n Ingrese una opcion valida.");
								cont = false;
							}
							Vehiculo vehiculoArrendado = empresa.encontrarVehiculo(placaVehiculoValido);
							boolean cobroExtraEntrega = false;
							if (!sedeRecogida.equals(sedeEntrega)) {
								cobroExtraEntrega = true;
							}
							String horas = inputEnter("\n Ingrese en que horas devolveria el vehiculo (formato 24h. Ejemplo: 9-11. Recuerde "
									+ "ingresar el intervalo, no horas sueltas y separado por '-'): ");
							
							String[] partes = fechaFin.split("/");
							LocalDate fechaRecogida =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
							boolean valHoras = empresa.validarHorasDevuelta(sedeFin, horas, fechaRecogida );
							
							if (valHoras == true) {
								Reserva reserva = new Reserva(null,fechaInicio,fechaFin, conductor,vehiculoArrendado,cliente,
										cobroExtraEntrega, sedeRecogida, sedeEntrega, "activa");
								
								double precio30 = reserva.getPrecioTotal() * 0.3;
								String pagoStr = inputEnter("\n Debe cancelar el 30% del valor total de su reserva, lo cual es: " + precio30 + 
										"\n Digite 'S' para pagar: ");
								if (pagoStr.equals("S")) {
									System.out.print("\n La reserva se ha creado con exito.");
									System.out.print("\n Su reserva tiene id: " + reserva.getIdReserva());
									empresa.guardarReserva(reserva);
									cont = false;
									continuar = false;
									
								}
								else {
									System.out.print("\n Pague por favor");
									cont = false;
								}
							}
							else {
								System.out.print("\n Las horas seleccionadas para la devolucion no son validas.");
								cont = false;
							}
							
							
						}
						
					}
					
					
				}
				
			}
			else if(opcionSeleccionada == 2) {
				//Modificar reserva
				String cc = inputEnter("\n Digite la cedula del cliente que realizo la reserva: ");
				String id = inputEnter("\n Digite el Id de reserva del cliente: ");
				Reserva reserva = empresa.validarReserva(cc,id);
				if (reserva == null) {
					System.out.print("\n La reserva no existe o el numero de cedula es incorrecto.");
					continuar = false;
				}
				else {
					System.out.print("\n La informacion de la reserva es: ");
					System.out.print(reserva.getInformacion());
					System.out.print("\n 1) Modificar fechas de entrega o recogida");
					System.out.print("\n 2) Modificar sede de entrega o recogida");
					String opcioncita = inputEnter("\n Digite la opcion que desea realizar: ");
					
					if (opcioncita.equals("1")) {
						String fechaInicio = inputEnter("\n Ingrese la nueva fecha de inicio de su reserva 'formato (dd/mm/yyyy)': ");
						String fechaFin = inputEnter("\n Ingrese la nueva fecha de fin de su reserva 'formato (dd/mm/yyyy)': ");
						boolean valido = empresa.validarCambioFecha(reserva, fechaInicio, fechaFin);
						if (valido == false) {
							System.out.print("\n No hay vehiculos disponibles para esta fecha. No se puede editar la reserva."
									+ "\n Primero intente editar la categoria o la sede de inicio.");
						}
						else {
							Reserva reservaEdit = new Reserva(reserva.getIdReserva(),fechaInicio,fechaFin, reserva.getConductorPrincipal(),
									reserva.getVehiculoArrendado(), reserva.getCliente(),
									reserva.getCobroExtraEntrega(), reserva.getSedeRecogida(), reserva.getSedeEntrega(), "activa");
							
							empresa.eliminarReserva(reserva);
							empresa.guardarReserva(reservaEdit);
							System.out.print("\n Se ha encontrado un vehiculo para estas fechas. Su reserva ha sido actualizada");
							System.out.print(reservaEdit.getInformacion());
							continuar = false;
					}
					}
					else if(opcioncita.equals("2")) {
						
						String opt = inputEnter("\n Digite 'I' si quiere cambiar la sede Inicial, digite 'F' si quiere cambiar la sede Final: ");
						if (opt.equals("I")) {
							ArrayList<Sede> sedes = empresa.getSedes();
							for (int i=0; i<sedes.size(); i++) {
								System.out.print("\n " + (i+1) + "  "+sedes.get(i).getNombre());
							}
							String numeroSede = inputEnter("\n Ingrese el numero de la nueva sede donde desea recoger un vehiculo: ");
							Sede sedeInicio = sedes.get(Integer.parseInt(numeroSede) - 1);
							Set<String> posiblesCats = sedeInicio.getInventario().getVehiculos().keySet();
							boolean encontrado = false;
							for (String cat: posiblesCats) {
								if (cat.equals(reserva.getVehiculoArrendado().getCategoria().getIdCategoria())) {
									encontrado = true;
								}
							}
							if (encontrado) {
								DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								String fechaInicio = reserva.getFechaRecogida().format(formato);
								String fechaFin = reserva.getFechaEntrega().format(formato);
								String placaVehiculoValido = empresa.revisarFechasReserva(sedeInicio, reserva.getVehiculoArrendado().getCategoria(), 
										fechaInicio, fechaFin);
								if (placaVehiculoValido.equals("")) {
									System.out.print("\n No es posible editar la reserva, no hay vehiculos disposibles para las fechas de la reserva original.");
									continuar = false;
								}
								else {
									Vehiculo vehiculoArrendado = empresa.encontrarVehiculo(placaVehiculoValido);
									
									Reserva reservaEdit = new Reserva(reserva.getIdReserva(),fechaInicio,fechaFin, reserva.getConductorPrincipal(),
											vehiculoArrendado, reserva.getCliente(),
											reserva.getCobroExtraEntrega(), sedeInicio.getNombre(), reserva.getSedeEntrega(), "activa");
									empresa.eliminarReserva(reserva);
									empresa.guardarReserva(reservaEdit);
									System.out.print("\n Se ha encontrado un vehiculo para estas fechas. Su reserva ha sido actualizada");
									System.out.print(reservaEdit.getInformacion());
									continuar = false;
								}
							}
						
							else {
								System.out.print("\n No es posible editar la reserva, la nueva sede no cuenta con vehiculos de la categoria de la reserva original");
								continuar = false;
							}
						}
						else if(opt.equals("F")) {
							ArrayList<Sede> sedes = empresa.getSedes();
							for (int i=0; i<sedes.size(); i++) {
								System.out.print("\n " + (i+1) + "  "+sedes.get(i).getNombre());
							}
							String numeroSede = inputEnter("\n Ingrese el numero de la nueva sede donde desea entregar un vehiculo: ");
							Sede sedeFin = sedes.get(Integer.parseInt(numeroSede) - 1);
							String horas = inputEnter("\n Ingrese en que horas devolveria el vehiculo (formato 24h. Ejemplo: 9-11. Recuerde "
									+ "ingresar el intervalo, no horas sueltas y separado por '-'): ");
							boolean valHoras = empresa.validarHorasDevuelta(sedeFin, horas, reserva.getFechaEntrega());
							DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							String fechaInicio = reserva.getFechaRecogida().format(formato);
							String fechaFin = reserva.getFechaEntrega().format(formato);
							if (valHoras == true) {
								Reserva reservaNueva = new Reserva(reserva.getIdReserva(),fechaInicio,fechaFin, reserva.getConductorPrincipal(),
										reserva.getVehiculoArrendado(), reserva.getCliente(),
										reserva.getCobroExtraEntrega(), reserva.getSedeRecogida(), sedeFin.getNombre(), "activa");
							
								System.out.print("\n La reserva se ha modificado con exito.");
								System.out.print("\n Su reserva tiene id: " + reserva.getIdReserva());
								empresa.eliminarReserva(reserva);
								empresa.guardarReserva(reservaNueva);
								continuar = false;
							
							}
							else {
								System.out.print("\n Las horas de devolucion no son validas.");
							}
					}
					}
					else {
						System.out.print("\n Ingrese una opcion valida.");
					}
				}
				
			}
			else if(opcionSeleccionada == 3) {
				//Eliminar reserva
				String cc = inputEnter("\n Digite la cedula del cliente que realizo la reserva: ");
				String id = inputEnter("\n Digite el Id de reserva del cliente: ");
				Reserva reserva = empresa.validarReserva(cc,id);
				if (reserva == null) {
					System.out.print("\n La reserva no existe o el numero de cedula es incorrecto.");
					continuar = false;
				}
				else {
					empresa.eliminarReserva(reserva);
					System.out.print("\n La reserva ha sido eliminada.");
					continuar = false;
				}
				
			}
			else if(opcionSeleccionada ==4) {
				System.out.print("\n Saliendo del sistema.");
				continuar = false;
			}
		}
		else if(rol.equals("limpiador")) {
			if (opcionSeleccionada == 1) {
				//Reportar vehiculo a mantenimiento
				
				String placa = inputEnter("\n Digite la placa del vehiculo: ");
				String fechaVuelta = inputEnter("\n Digite la fecha cuando volveria el vehiculo '(formato dd/mm/yyyy)': ");
				empresa.mandarSacarMantenimiento(placa, "mantenimiento");
				System.out.print("\n El vehiculo ha sido mandado a mantenimiento, fecha de vuelta a operaciones "+fechaVuelta);
				continuar = false;
			}
			
			else if(opcionSeleccionada == 2) {
				//Cambiar estado de vehiculo
				String placa = inputEnter("\n Digite la placa del vehiculo: ");
				empresa.mandarSacarMantenimiento(placa, "activo");
				continuar = false;
			}
			else if(opcionSeleccionada == 3) {
				continuar = false;
				System.out.print("\n Saliendo del sistema.");
				}
				
			}
			else {
				System.out.print("\n Saliendo del sistema");
				continuar = false;
			}
		
		
		}
	}
	
	
	public static void main(String[] args) throws IOException{
		
		File archivoVehiculos = new File("./data/vehiculos.txt");
		File archivoSedes = new File("./data/sedes.txt");
		File archivoUsuarios = new File("./data/usuarios.txt");
		File archivoCategorias = new File("./data/categorias.txt");
		File archivoSeguros = new File("./data/seguros.txt");
		File archivoClientes = new File("./data/clientes.txt");
		File archivoReservas = new File("./data/reservas.txt");
		File archivoConductores = new File("./data/conductores.txt");
		empresa.cargarArchivos(archivoVehiculos,archivoSedes, archivoUsuarios, archivoCategorias, archivoSeguros,
								archivoClientes, archivoReservas, archivoConductores);
		
		boolean inicio = true;
		while (inicio) {
			String regis = inputEnter("\n Digite 'R' si es un empleado/admin/cliente registrado, digite 'N' si es un cliente y aun no tiene usuario y contraseña: ");
			if (regis.equals("N")) {
				String nombre = inputEnter("\n Digite su nombre: ");
				String cc = inputEnter("\n Digite su cedula: ");
				String celular = inputEnter("\n Digite su celular: ");
				String fechaNacimiento = inputEnter("\n Digite su fecha de nacimiento 'formato (dd/mm/yyyy)': ");
				String nacionalidad = inputEnter("\n Digite su nacionalidad: ");
				String numeroLicencia = inputEnter("\n Digite su numero de Licencia: ");
				String paisLicencia = inputEnter("\n Digite el pais donde se expedio su licencia: ");
				String fechaVencimientoLicencia = inputEnter("\n Digite la fecha de vencimiento de su licencia 'formato (dd/mm/yyyy)': ");
				String numeroTarjeta = inputEnter("\n Digite el numero de su tarjeta: ");
				String fechaVencimientoTarjeta = inputEnter("\n Digite la fecha de vencimiento de su tarjeta 'formato (dd/mm/yyyy)': ");
				String codigoTarjeta = inputEnter("\n Digite el codigo de seguridad de su tarjeta: ");
				String tipoTarjeta = inputEnter("\n Digite el tipo de su tarjeta (Visa, Mastercard, etc.): ");
				String correo = inputEnter("\n Digite su correo electronico: ");
				String login = inputEnter("\n Digite su nuevo login: ");
				String password = inputEnter("\n Digite su nueva contraseña: ");
				Usuario user = new Usuario(login, password, "cliente");
				empresa.agregarUsuarioCliente(user);
				empresa.agregarConductor(new Conductor(nombre,cc,numeroLicencia,paisLicencia,fechaVencimientoLicencia, 
						"fotico.png", celular, correo));
				empresa.agregarCliente(new ClienteNormal(nombre,cc,celular,fechaNacimiento,nacionalidad,
						numeroLicencia,paisLicencia, fechaVencimientoLicencia, numeroTarjeta,
						fechaVencimientoTarjeta,codigoTarjeta,tipoTarjeta, user,correo, "libre"));
				
			}
			else {
				ArrayList<String> info = pedirUsuario();
				String login = info.get(0);
				String rol = info.get(1);
				int op = 0;
				int salida = 0;
				if (rol.equals("adminGeneral")) {
					salida = 7;
				}
				else if (rol.equals("admin")) {
					salida = 4;
				}
				else if (rol.equals("recepcion")){
					salida = 5;
				}
				else if(rol.equals("cliente")) {
					salida = 3;
				}
				else {
					salida = 3;
				}
				
				while (op != salida && inicio == true) {
					mostrarOpcionesDisponiblesTotales(rol, login);
					System.out.print("\nPor favor digite la opción que desee realizar: ");
					BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
					String input = br.readLine();
					if(input == null) {
						System.out.println("\nLa entrada no puede estar vacia.");
					}
					else {
						try {
						op = Integer.parseInt(input);
						ejecutarOpciones(op, rol, login);
						System.out.print("\nPresione 'Enter' para continuar. Si desea cerrar el sistema digite F: ");
						BufferedReader brStop = new BufferedReader(new InputStreamReader(System.in));
						try {
							String inputStop = brStop.readLine();
							if (inputStop.equals("F")) {
								inicio = false;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (NumberFormatException nfe ) {
						System.out.println("La entrada debe ser un valor numérico.");
					}
				  }	
				}
				inicio = false;
		}
		}
	}

}
