package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
	//Atributos
	
	private LocalDate fechaRecogida;
	private LocalDate fechaEntrega;
	private Conductor conductorPrincipal;
	private Vehiculo vehiculoArrendado;
	private ArrayList<Conductor> conductoresAdicionales;
	private String IdReserva;
	private static int numeroReservas;
	private ArrayList<Seguro> segurosAdicionales;
	private ClienteNormal cliente;
	private boolean terminada;
	private boolean pagado30;
	private boolean cobroExtraEntrega;
	private String sedeRecogida;
	private String sedeEntrega;
	
	//Metodos
	
	public Reserva(String idReserva , String fechaRecogida, String fechaEntrega, Conductor conductorPrincipal, Vehiculo vehiculoArrendado, ClienteNormal cliente,
			boolean cobroExtraEntrega, String sedeRecogida, String sedeEntrega, String activa) {
		numeroReservas ++;
		if (idReserva != null) {
			this.IdReserva = idReserva;
		}
		else {
			this.IdReserva = Integer.toString(numeroReservas);
		}
		String[] partes = fechaRecogida.split("/");
		this.fechaRecogida =  LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
		String[] partes2 = fechaEntrega.split("/");
		this.fechaEntrega = LocalDate.of(Integer.parseInt(partes2[2]), Integer.parseInt(partes2[1]), Integer.parseInt(partes2[0]));
		this.conductoresAdicionales = new ArrayList<>();
		this.segurosAdicionales = new ArrayList<>();
		this.cliente = cliente;
		if (activa.equals("activa")) {
			this.terminada = false;
		}
		else {
			this.terminada = true;
		}
		this.cobroExtraEntrega = cobroExtraEntrega;
		this.sedeEntrega = sedeEntrega;
		this.sedeRecogida = sedeRecogida;
		this.vehiculoArrendado = vehiculoArrendado;
		this.conductorPrincipal = conductorPrincipal;
	}

	public String getSedeRecogida() {
		return sedeRecogida;
	}

	public String getSedeEntrega() {
		return sedeEntrega;
	}

	public void setSedeRecogida(String sedeRecogida) {
		this.sedeRecogida = sedeRecogida;
	}

	public void setSedeEntrega(String sedeEntrega) {
		this.sedeEntrega = sedeEntrega;
	}

	public LocalDate getFechaRecogida() {
		return fechaRecogida;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public Conductor getConductorPrincipal() {
		return conductorPrincipal;
	}

	public Vehiculo getVehiculoArrendado() {
		return vehiculoArrendado;
	}
	
	public String getIdReserva() {
		return IdReserva;
	}

	public int getNumeroReservas() {
		return numeroReservas;
	}
	
	public ArrayList<Conductor> getConductoresAdicionales(){
		return conductoresAdicionales;
	}
	
	public void setConductorAdicional(Conductor conductor, boolean edit) {
		conductoresAdicionales.add(conductor);
		
		if (edit) {
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
	}
	
	public void setSeguroAdicional(Seguro seguro) {
		segurosAdicionales.add(seguro);
	}
	
	public ClienteNormal getCliente() {
		return cliente;
	}
	
	public boolean getTerminada() {
		return terminada;
	}
	public void setTerminada() {
		terminada = true;
	}
	
	public boolean getPagado30() {
		return pagado30;
	}
	
	public void setPagado30() {
		pagado30 = true;
		cliente.getTarjeta().bloquearTarjeta();
	}
	
	public Integer getDiasReserva() {
		long dias = ChronoUnit.DAYS.between(fechaRecogida, fechaEntrega);
		return (int) dias;
	}
	
	public String getInformacion() {
		String texto = "";
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		texto += "\n Id de la reserva: " + IdReserva + "\n";
		texto += "Fecha inicio: " + fechaRecogida.format(formato) + "\n";
		texto += "Fecha fin: " + fechaEntrega.format(formato) + "\n";
		texto += "Reserva creada por: " + cliente.getNombre() + "\n";
		texto += "Vehiculo reservado: " + vehiculoArrendado.getPlaca() + " Categoria: " + vehiculoArrendado.getCategoria().getIdCategoria() + "\n";
		texto += "Sede recogida: " + sedeRecogida + " Sede entrega: " + sedeEntrega;
		return texto;
	}
	
	public static List<LocalDate> obtenerFechasIntermedias(LocalDate fechaInicio, LocalDate fechaFin) {
		long diferenciaEnDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        List<LocalDate> fechasIntermedias = new ArrayList<>();
        fechasIntermedias.add(fechaInicio);
        for (int i = 1; i < Math.abs((int) diferenciaEnDias); i++) {
            LocalDate fechaIntermedia = fechaInicio.plusDays(i);
            fechasIntermedias.add(fechaIntermedia);
        }
        fechasIntermedias.add(fechaFin);
        return fechasIntermedias;
    }
	
	public Integer getPrecioTotal() {
		int precio = 0;
		List<LocalDate> fechasIntermedias = obtenerFechasIntermedias(fechaEntrega, fechaRecogida);
        for (LocalDate fecha : fechasIntermedias) {
            precio += vehiculoArrendado.getCategoria().getPrecioParaFechaDada(fecha);
            precio += conductoresAdicionales.size() * vehiculoArrendado.getCategoria().getPrecioExtraConductor();
        }
        for (int i=0; i<segurosAdicionales.size(); i++) {
        	precio += Integer.parseInt(segurosAdicionales.get(i).getPrecioDiario()) * fechasIntermedias.size();
        }
        if (cobroExtraEntrega == true) {
        	precio += vehiculoArrendado.getCategoria().getPrecioExtraEntrega();
        }
        return precio;
	}

	public ArrayList<Seguro> getSegurosAdicionales() {
		return segurosAdicionales;
	}
	
	public void finReserva() throws IOException {
		terminada = true;
		
		//Modificamos el reservas.txt
		File archivoReservas = new File("./data/reservas.txt");
		String texto = "";
		BufferedReader br = new BufferedReader(new FileReader(archivoReservas));
		String linea = br.readLine();      
		while (linea != null) 
		{
			String[] partes = linea.split(";");
			if(partes[0].equals(this.IdReserva)) {
				texto += partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" + partes[4] + ";" + partes[5] + ";";
				texto += partes[6] + ";" + "finalizada" + partes[8] + ";" + partes[9] + "\n";
			}
			else {
				texto += linea + "\n";
			}
			linea = br.readLine(); 
		}
		br.close();
		
		texto =  texto.substring(0, texto.length() - 1);
		
		FileWriter archivo = new FileWriter("./data/reservas.txt");
		BufferedWriter writer;
		try {
			
			writer = new BufferedWriter(archivo);
			writer.write(texto);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getActiva() {
		if(terminada == false) {
			return "activa";
		}
		else {
			return "finalizada";
		}
	}

	public boolean getCobroExtraEntrega() {
		return cobroExtraEntrega;
	}
	
}
