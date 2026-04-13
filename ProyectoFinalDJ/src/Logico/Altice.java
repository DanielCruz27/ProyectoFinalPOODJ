package Logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Visual.ListarServicios;

public class Altice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Altice altice = null;
	private ArrayList<Cliente> listaClientes;
	private ArrayList<Personal> listaEmpleados;
	private ArrayList<Servicio> catalogoServicio;
	private ArrayList<Pago> historialPagos;
	private ArrayList<Ticket> listaTickets;
	private ArrayList<Contrato> listaContratos;
	private Object usuarioLogueado;
	public int codigoPersonal = 1;
	public int codigoCliente = 1;
	public int codigoContrato = 1;
	public int codigoFactura = 1;
	public int codigoServicio = 1;
	public int codigoTicket = 1;
	private ArrayList<Valoracion>listaValoraciones;
	public int codigoValoracion = 1;


	public Altice() {
		super();
		this.listaClientes = new ArrayList<>();
		this.listaEmpleados = new ArrayList<>();
		this.catalogoServicio = new ArrayList<>();
		this.historialPagos = new ArrayList<>();
		this.listaTickets = new ArrayList<>();
		this.listaContratos = new ArrayList<>();
		this.listaValoraciones = new ArrayList<>();
		
	}

	public static Altice getInstance() {
		if (altice == null) {
			altice = new Altice();
		}
		return altice;
	}
	public Object verificarAccesoUniversal(String user, String pass) {
	    Object login = null;
	    
	    for (Personal p : listaEmpleados) {
	        if (p.getMiCuenta().getNombreUsuario().equalsIgnoreCase(user) && p.getMiCuenta().getContraseña().equals(pass)) {
	            login = p; 
	            this.usuarioLogueado = p; // <--- AGREGAR ESTO
	        }
	    }
	    
	    for (Cliente c : listaClientes) {
	        if (c.getMiCuenta().getNombreUsuario().equalsIgnoreCase(user) && c.getMiCuenta().getContraseña().equals(pass)) {
	            login = c;
	            this.usuarioLogueado = c; // <--- AGREGAR ESTO
	        }
	    }
	    
	    return login; 
	}


	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ArrayList<Personal> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Personal> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public ArrayList<Servicio> getCatalogoServicio() {
		return catalogoServicio;
	}

	public void setCatalogoServicio(ArrayList<Servicio> catalogoServicio) {
		this.catalogoServicio = catalogoServicio;
	}

	public ArrayList<Pago> getHistorialPagos() {
		return historialPagos;
	}

	public void setHistorialPagos(ArrayList<Pago> historialPagos) {
		this.historialPagos = historialPagos;
	}

	public ArrayList<Ticket> getListaTickets() {
	    if (listaTickets == null) {
	        listaTickets = new ArrayList<>();
	    }
	    return listaTickets;
	}

	public void setListaTickets(ArrayList<Ticket> listaTickets) {
		this.listaTickets = listaTickets;
	}

	public ArrayList<Contrato> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(ArrayList<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}
	
	public ArrayList<Valoracion> getListaValoraciones() {
		return listaValoraciones;
	}

	public void setListaValoraciones(ArrayList<Valoracion> listaValoraciones) {
		this.listaValoraciones = listaValoraciones;
	}

	private String generarNumeroTelefonico() {
		String numero = "";
		int opt = ThreadLocalRandom.current().nextInt(1, 4);
		int number = ThreadLocalRandom.current().nextInt(1000000, 10000000);
		String sufix = "";

		if (opt == 1) {
			sufix = "809";
		} else if (opt == 2) {
			sufix = "849";
		} else if (opt == 3) {
			sufix = "829";
		}
		numero = sufix + number;

		return numero;
	}
	
	public void contratarServicio(String idCliente, String idServicio, String idVendedor) {
	    Cliente client = buscarCliente(idCliente);
	    Servicio service = buscarServicioById(idServicio);
	    Personal vendedor = buscarEmpleadoPorId(idVendedor);

	    if (client != null && service != null) {
	        Servicio servicioReal = null;

	        if (service instanceof PlanMovil) {
	            PlanMovil p = (PlanMovil) service;
	            servicioReal = new PlanMovil(p.getIdServicio(), p.getNombreServicio(), p.getPrecioBase(), true,
	                    generarNumeroTelefonico(), p.getMinutosIncluidos(), p.getRedesLibresIncluidas());
	        } else if (service instanceof PlanHogar) {
	            PlanHogar h = (PlanHogar) service;
	            servicioReal = new PlanHogar(h.getIdServicio(), h.getNombreServicio(), h.getPrecioBase(), true,
	                    generarNumeroTelefonico(), h.getVelocidadInternet(), h.getStreamingIncluido(),
	                    h.getMinutosTelefonoHogar());
	        }

	        if (client.getMiContrato() == null) {
	            ArrayList<Servicio> listaInicial = new ArrayList<>();
	            listaInicial.add(servicioReal);
	            
	            Contrato nuevo = new Contrato(client, listaInicial, vendedor, new ArrayList<Pago>(), LocalDate.now());
	            client.setMiContrato(nuevo);
	            
	            // --- ESTA LÍNEA ES VITAL ---
	            this.listaContratos.add(nuevo); 
	            // ---------------------------
	        } else {
	            client.getMiContrato().getMisServicios().add(servicioReal);
	        }
	    }
	}

	private Contrato buscarContratoPorCliente(String idCliente) {
		Contrato aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaContratos.size()) {

			if (listaContratos.get(i).getElTitular().getIdCliente().equalsIgnoreCase(idCliente)) {
				aux = listaContratos.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public Personal buscarEmpleadoPorId(String idVendedor) {
		Personal aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaEmpleados.size()) {

			if (listaEmpleados.get(i).getIdEmpleado().equalsIgnoreCase(idVendedor)) {
				aux = listaEmpleados.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public Servicio buscarServicioById(String idServicio) {
		Servicio aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < catalogoServicio.size()) {

			if (catalogoServicio.get(i).getIdServicio().equalsIgnoreCase(idServicio)) {
				aux = catalogoServicio.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public Servicio realizarRecarga(String numeroTelefonico, int saldoAgregar) {
	    Contrato contrato = findContractByNumber(numeroTelefonico);
	    
	    if (contrato == null) {
	        return null; // Si no hay contrato con ese número, terminamos de una vez
	    }

	    // 1. Intentamos actualizar el saldo del plan (Hogar o Móvil)
	    Servicio serviceEncontrado = actualizarSaldoServicio(contrato, numeroTelefonico, saldoAgregar);

	    // 2. Si se actualizó el saldo, generamos la factura automáticamente
	    if (serviceEncontrado != null) {
	        generarFacturaRecarga(contrato, saldoAgregar);
	    }

	    return serviceEncontrado; 
	}
	
	private Servicio actualizarSaldoServicio(Contrato contrato, String numero, int saldo) {
	    for (Servicio aux : contrato.getMisServicios()) {
	        if (aux instanceof PlanHogar) {
	            PlanHogar ph = (PlanHogar) aux;
	            if (ph.getNumeroTelefonico().equalsIgnoreCase(numero)) {
	                ph.setMinutosTelefonoHogar(ph.getMinutosTelefonoHogar() + saldo);
	                return ph;
	            }
	        } else if (aux instanceof PlanMovil) {
	            PlanMovil pm = (PlanMovil) aux;
	            if (pm.getNumeroTelefonico().equalsIgnoreCase(numero)) {
	                pm.setMinutosLibres(pm.getMinutosIncluidos() + saldo);
	                return pm;
	            }
	        }
	    }
	    return null;
	}
	
	private void generarFacturaRecarga(Contrato contrato, int monto) {
	    float itbis = monto * 0.18f;
	    float total = monto + itbis;
	    
	    // Buscamos el método de pago del cliente (usamos el primero de la lista)
	    ArrayList<MetodoDePago> metodos = contrato.getElTitular().getMisMetodos();
	    MetodoDePago metodoUsado = (metodos != null && !metodos.isEmpty()) ? metodos.get(0) : new Efectivo(total);

	    // Creamos la factura (Pago)
	    Pago nuevoPago = new Pago("F-" + codigoFactura, LocalDate.now(), total, true, metodoUsado, itbis, contrato);
	    
	    // Guardamos en los historiales
	    this.historialPagos.add(nuevoPago);
	    contrato.getElTitular().getMisPagos().add(nuevoPago);
	    
	    codigoFactura++; // Incrementamos el correlativo para la próxima
	}

	private Contrato findContractByNumber(String numeroTelefonico) {
	    Contrato aux = null;
	    
	    // Recorremos la lista de clientes, que es la que tiene la data real
	    for (Cliente cli : listaClientes) {
	        // Verificamos si el cliente tiene un contrato asignado
	        if (cli.getMiContrato() != null) {
	            // Usamos tu método de apoyo 'tieneElServicioBuscado'
	            if (tieneElServicioBuscado(cli.getMiContrato(), numeroTelefonico)) {
	                aux = cli.getMiContrato();
	                break; // Lo encontramos, salimos del bucle
	            }
	        }
	    }

	    return aux;
	}

	private boolean tieneElServicioBuscado(Contrato contrato, String numeroTelefonico) {
		boolean encontrado = false;
	    ArrayList<Servicio> servicios = contrato.getMisServicios();
	    int j = 0;

	    while (j < servicios.size() && !encontrado) {
	        Servicio service = servicios.get(j);
	        
	        if (service instanceof PlanHogar && ((PlanHogar) service).getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)) {
	            encontrado = true;
	        } 
	        else if (service instanceof PlanMovil && ((PlanMovil) service).getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)) {
	            encontrado = true;
	        }
	        
	        j++;
	    }

	    return encontrado;
	}		
	

	public boolean vincularMetodoPago(String idCliente, MetodoDePago metPago) {
	    Cliente client = buscarCliente(idCliente);
	    boolean aux = false;

	    if (client != null) {
	        aux = true;
	        // Cambiamos client.setMiMetodo(metPago) por:
	        client.addMetodoPago(metPago); 
	    }

	    return aux;
	}

	public Cliente buscarCliente(String idCliente) {
		Cliente aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaClientes.size()) {

			if (listaClientes.get(i).getIdCliente().equalsIgnoreCase(idCliente)) {
				aux = listaClientes.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public Cliente buscarClienteByEmail(String email) {
		Cliente aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaClientes.size()) {

			if (listaClientes.get(i).getEmailCliente().equalsIgnoreCase(email)) {
				aux = listaClientes.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public void registrarCliente(Cliente cliente) {

		if (buscarClienteByEmail(cliente.getEmailCliente()) == null) {

			listaClientes.add(cliente);
		}

	}

	public float reporteGanancias(LocalDate inicio, LocalDate fin) {
		float total = 0;

		for (Pago pago : historialPagos) {
			if (pago.getFechaEmision().isAfter(inicio) && pago.getFechaEmision().isBefore(fin)) {

				total += pago.getMontoTotal();
			}
		}

		return total;
	}

	public ArrayList<Cliente> reporteClientePorZona(String areaZone) {
		ArrayList<Cliente> ZoneClients = new ArrayList<>();

		for (Cliente client : listaClientes) {
			if (client.getZonaVivienda().equalsIgnoreCase(areaZone)) {
				ZoneClients.add(client);
			}
		}

		return ZoneClients;
	}

	public int getCodigotPersonal() {

		return codigoPersonal;
	}

	public void incrementarPersonal() {

		codigoPersonal++;
	}

	public int getCodigoCliente() {

		return codigoCliente;
	}

	public void incrementarCliente() {

		codigoCliente++;
	}

	public int getCodigoServicio() {

		return codigoServicio;
	}

	public void incrementarServicio() {

		codigoServicio++;
	}
	
	public int getCodigoTicket() {

		return codigoTicket;
	}

	public void incrementarTicket() {

		codigoTicket++;
	}
	
	public int getcodigoValoracion() {

		return codigoValoracion;
	}

	

	public void RegistarPersonal(Personal empleado) {
		// Usamos el contador interno del objeto Altice
		empleado.setIdEmpleado("P-" + codigoPersonal);
		listaEmpleados.add(empleado);
		codigoPersonal++; // Se incrementa y se guardará en el .dat
	}

	public void InsertaCliente(Cliente client) {
		listaClientes.add(client);
		codigoCliente++;

	}

	public boolean buscarUsuario(String nombreUsuario) {
		boolean finded = false;

		for (Cliente aux : listaClientes) {
			if (aux.getMiCuenta().getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
				finded = true;
			}
		}

		return finded;

	}

	public static void setInstance(Altice temp) {
		altice = temp;
	}

	public void RegistarServicio(Servicio service) {
		service.setIdServicio("S-" + codigoServicio);
		catalogoServicio.add(service);
		codigoServicio++;
	}

	// Método para cambiar el estado de un servicio (Activar/Desactivar)
	public void cambiarEstadoServicio(String idServicio) {
		Servicio s = buscarServicioById(idServicio);
		if (s != null) {
			// Si está true lo pone false, y viceversa
			s.setEstadoDelServicio(!s.isEstadoDelServicio());
		}
	}

	public Object getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Object usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	// Método para contar servicios totales de un cliente sumando todos sus
	// contratos
	public int contarCantServiciosDeCliente(String idCliente) {
	    int total = 0;
	    Cliente client = buscarCliente(idCliente);
	    
	    // Verificamos que el cliente exista y que ya tenga un contrato creado
	    if (client != null && client.getMiContrato() != null) {
	        total = client.getMiContrato().getMisServicios().size();
	    }
	    
	    return total;
	}

	// Método para verificar si el cliente tiene facturas sin pagar
	public String comprobarSiHayDeuda(String idCliente) {
		String tieneDeuda = "No";
		Cliente client = buscarCliente(idCliente);

		if (client != null && client.getMisPagos() != null) {
			boolean encontrado = false;
			int i = 0;
			while (i < client.getMisPagos().size() && !encontrado) {
				if (!client.getMisPagos().get(i).isEstadoPago()) { // Si el pago no está realizado
					tieneDeuda = "Si";
					encontrado = true;
				}
				i++;
			}
		}
		return tieneDeuda;
	}

	public Cliente buscarClienteByCedula(String cedula) {
		Cliente aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaClientes.size()) {

			if (listaClientes.get(i).getCedula().equalsIgnoreCase(cedula)) {
				aux = listaClientes.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}
	public ArrayList<Contrato> buscarContratoByUser() {
	    ArrayList<Contrato> contratos = new ArrayList<Contrato>();
	    
	    Personal emp = buscarPersonalPorUser();
	    
	    if (emp != null) {
	        for (Contrato aux : listaContratos) {
	            if (aux.getVendedor().getIdEmpleado().equalsIgnoreCase(emp.getIdEmpleado())) {
	                contratos.add(aux); 
	            }
	        }
	    }
	    return contratos;
	}

	private Personal buscarPersonalPorUser() {
	    Personal emp = null;
	    
	    if (usuarioLogueado != null && usuarioLogueado instanceof Personal) {
	        Personal logueado = (Personal) usuarioLogueado;
	        String userActual = logueado.getMiCuenta().getNombreUsuario();

	        boolean finded = false;
	        int i = 0;
	        while (!finded && i < listaEmpleados.size()) {
	            if (listaEmpleados.get(i).getMiCuenta().getNombreUsuario().equalsIgnoreCase(userActual)) {
	                finded = true;
	                emp = listaEmpleados.get(i);
	            }
	            i++;
	        }
	    }
	    return emp;
	}

	// Devuelve la cantidad de meses que debe el cliente basándose en el reloj de la PC
	public int calcularAtrasosReales(Cliente cli) {
	    int totalAtrasos = 0;
	    
	    // Verificamos que el cliente tenga su contrato creado
	    if (cli != null && cli.getMiContrato() != null) {
	        Contrato con = cli.getMiContrato();
	        
	        // Calculamos meses entre la firma y hoy (reloj de tu PC)
	        long mesesDesdeFirma = ChronoUnit.MONTHS.between(con.getFechaFirma(), LocalDate.now());
	        
	        // Obtenemos cuántos pagos hay en el historial de ese contrato
	        int pagosHechos = con.getHistorialDePagos().size();
	        
	        int diferencia = (int) mesesDesdeFirma - pagosHechos;
	        
	        if (diferencia > 0) {
	            totalAtrasos = diferencia;
	        }
	    }
	    
	    return totalAtrasos;
	}
	
	public float calcularMontoDeudaReal(Cliente cli) {
	    float montoTotal = 0;
	    
	    if (cli != null && cli.getMiContrato() != null) {
	        Contrato con = cli.getMiContrato();
	        
	        long mesesDesdeFirma = ChronoUnit.MONTHS.between(con.getFechaFirma(), LocalDate.now());
	        int pagosHechos = con.getHistorialDePagos().size();
	        int mesesDebidos = (int) mesesDesdeFirma - pagosHechos;

	        if (mesesDebidos > 0) {
	            float sumaMensualSubtotal = 0;
	            for (Servicio s : con.getMisServicios()) {
	                sumaMensualSubtotal += s.getPrecioBase();
	            }
	            
	            // Calculamos el total con ITBIS incluido (Precio * 1.18)
	            float mensualidadConItbis = sumaMensualSubtotal * 1.18f;
	            montoTotal = mesesDebidos * mensualidadConItbis;
	        }
	    }
	    return montoTotal;
	}
	
	public void generarTicket(Cliente cliente, String problema) {
	    String area = "";
	    
	    // Asignación automática de área según el problema elegido
	    if (problema.equalsIgnoreCase("Internet lento")) {
	        area = "Soporte técnico";
	    } else if (problema.equalsIgnoreCase("cable roto")) {
	        area = "Planta externa";
	    } else if (problema.equalsIgnoreCase("poste inclinado")) {
	        area = "Infraestructura";
	    } else if (problema.equalsIgnoreCase("instalación de equipo")) {
	        area = "Instalacion";
	    }
	    
	    
	    Ticket nuevo = new Ticket("TKT-" + codigoTicket, cliente, area, LocalDate.now(), 0, null);
	    
	    listaTickets.add(nuevo);
	    codigoTicket++;
	}

	public Ticket buscarTicketById(String idTicket) {
	    if (idTicket == null || idTicket.isEmpty()) {
	        return null;
	    }
	    
	    for (Ticket ticket : listaTickets) {
	        if (ticket.getIdTicket().equalsIgnoreCase(idTicket)) {
	            return ticket; 
	        }
	    }
		return null;
	    
	}
	
	
	public double calcularComisionesPorVendedor(String idVendedor) {
	    double totalComision = 0;
	    
	    // 1. Buscamos todos los contratos de este vendedor
	    for (Contrato c : listaContratos) {
	        if (c.getVendedor() != null && c.getVendedor().getIdEmpleado().equalsIgnoreCase(idVendedor)) {
	            
	            // 2. Sumamos la comisión de cada servicio dentro del contrato
	            for (Servicio s : c.getMisServicios()) {
	                // Ejemplo: 10% del precio base
	                totalComision += (s.getPrecioBase() * 0.10);
	            }
	        }
	    }
	    
	    return totalComision;
	}
	
	public void simularConsumoAleatorio(Cliente cliente) {
	    if (cliente != null && cliente.getMiContrato() != null) {
	        for (Servicio s : cliente.getMiContrato().getMisServicios()) {
	            // Consumo aleatorio entre 2 y 20 minutos
	            int consumo = java.util.concurrent.ThreadLocalRandom.current().nextInt(2, 21);
	            
	            if (s instanceof PlanMovil) {
	                PlanMovil pm = (PlanMovil) s;
	                // Restamos y nos aseguramos de no bajar de cero
	                int actual = pm.getMinutosIncluidos();
	                pm.setMinutosLibres(Math.max(0, actual - consumo));
	            } else if (s instanceof PlanHogar) {
	                PlanHogar ph = (PlanHogar) s;
	                int actual = ph.getMinutosTelefonoHogar();
	                ph.setMinutosTelefonoHogar(Math.max(0, actual - consumo));
	            }
	        }
	    }
	}
	
	public void registrarValoracion(Valoracion v) {
	    listaValoraciones.add(v);
	    codigoValoracion++;
	}

	
	
}