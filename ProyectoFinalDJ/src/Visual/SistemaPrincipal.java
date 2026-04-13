package Visual;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;

import Logico.Altice;

public class SistemaPrincipal extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCuerpo; // El área donde se mostrarán los formularios
	private JTextField textFieldHora;
	private JToggleButton tglbtnAdmin, tglbtnTecnico, tglbtnComercial, tglbtnCliente;

	// Variables para el control del menú acordeón
	private JPanel panelContenedorMenu;
	private boolean menuPersonalAbierto = false;
	private boolean menuClientesAbierto = false;
	private boolean menuReportesAbierto = false;
	
	// Variables para control del menú Comercial
	private boolean menuVentasOpen = false;
	private boolean menuServOpenCom = false;

	// --- VARIABLES PARA CLIENTE ---
	private boolean menuInfoClienteOpen = false;
	private boolean menuSoporteClienteOpen = false;
	private boolean menuValoracionClienteOpen = false;

	// --- VARIABLES AGREGADAS PARA TECNICO ---
	private boolean menuTrabajoTecnicoOpen = false;
	private boolean menuRendimientoTecnicoOpen = false;

	static Socket sfd = null;
	static DataInputStream EntradaSocket;
	static DataOutputStream SalidaSocket;
	
	public SistemaPrincipal(String rolUsuario) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FileOutputStream altice;
				ObjectOutputStream alticeWrite;
				try {
					altice = new FileOutputStream("Alticee.dat");
					alticeWrite = new ObjectOutputStream(altice);
					alticeWrite.writeObject(Altice.getInstance());
					alticeWrite.close();
				}catch (FileNotFoundException e1) {
                	JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.WARNING_MESSAGE);
				}catch (IOException e1 ) {
                	JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.WARNING_MESSAGE);
				}
 
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SistemaPrincipal.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Altice - Sistema de Gestión");

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int anchoPantalla = dim.width;
		int altoPantalla = dim.height - 40;

		setSize(anchoPantalla, altoPantalla);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- 1. PANEL LATERAL ---
		JPanel panelLateral = new JPanel();
		panelLateral.setOpaque(false);
		panelLateral.setBorder(new LineBorder(new Color(180, 180, 180), 1));
		panelLateral.setBounds(0, 0, 223, altoPantalla);
		contentPane.add(panelLateral);
		panelLateral.setLayout(null);

		JLabel lblLogoLateral = new JLabel("");
		ImageIcon imgLogoLat = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/AlticeMenuLogo.png"));
		Image logoLatEscalado = imgLogoLat.getImage().getScaledInstance(160, 70, Image.SCALE_SMOOTH);
		lblLogoLateral.setIcon(new ImageIcon(logoLatEscalado));
		lblLogoLateral.setBounds(31, 11, 160, 70);
		panelLateral.add(lblLogoLateral);

		panelContenedorMenu = new JPanel();
		panelContenedorMenu.setOpaque(false);
		panelContenedorMenu.setBackground(new Color(0,0,0,0));
		panelContenedorMenu.setBounds(0, 92, 223, altoPantalla - 200);
		panelLateral.add(panelContenedorMenu);
		panelContenedorMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// --- CARGA DINÁMICA DE MENÚS ---
		if (rolUsuario.equalsIgnoreCase("Administrador")) {
			crearMenuAdministrador(altoPantalla,rolUsuario);
		} else if (rolUsuario.equalsIgnoreCase("Comercial")) {
			crearMenuComercial(altoPantalla,rolUsuario);
		} else if (rolUsuario.equalsIgnoreCase("Cliente")) {
			crearMenuCliente(altoPantalla, rolUsuario);
		} else if (rolUsuario.equalsIgnoreCase("Tecnico")) {
			crearMenuTecnico(altoPantalla, rolUsuario); // <--- AGREGADO
		}

		JLabel lblFondoLateral = new JLabel("");
		ImageIcon imgFondoLat = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/FondoDegradado.jpg"));
		Image fondoLatEscalado = imgFondoLat.getImage().getScaledInstance(223, altoPantalla, Image.SCALE_SMOOTH);
		lblFondoLateral.setIcon(new ImageIcon(fondoLatEscalado));
		lblFondoLateral.setBounds(0, 0, 223, altoPantalla);
		panelLateral.add(lblFondoLateral);
		panelLateral.setComponentZOrder(lblFondoLateral, panelLateral.getComponentCount() - 1);

		// --- 2. PANEL SUPERIOR ---
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		int anchoBarraSuperior = anchoPantalla - 223;
		panelSuperior.setBounds(223, 0, anchoBarraSuperior, 68);
		contentPane.add(panelSuperior);
		panelSuperior.setLayout(null);

		ButtonGroup grupoRoles = new ButtonGroup();
		tglbtnAdmin = new JToggleButton("Administrador");
		tglbtnAdmin.setBounds(10, 20, 150, 30);
		estilarBotonSuperior(tglbtnAdmin);
		grupoRoles.add(tglbtnAdmin);
		panelSuperior.add(tglbtnAdmin);

		tglbtnTecnico = new JToggleButton("Técnico");
		tglbtnTecnico.setBounds(165, 20, 110, 30);
		estilarBotonSuperior(tglbtnTecnico);
		grupoRoles.add(tglbtnTecnico);
		panelSuperior.add(tglbtnTecnico);

		tglbtnComercial = new JToggleButton("Comercial");
		tglbtnComercial.setBounds(280, 20, 120, 30);
		estilarBotonSuperior(tglbtnComercial);
		grupoRoles.add(tglbtnComercial);
		panelSuperior.add(tglbtnComercial);

		tglbtnCliente = new JToggleButton("Cliente");
		tglbtnCliente.setBounds(405, 20, 100, 30);
		estilarBotonSuperior(tglbtnCliente);
		grupoRoles.add(tglbtnCliente);
		panelSuperior.add(tglbtnCliente);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(255, 255, 255, 100));
		separator.setBounds(520, 15, 2, 40);
		panelSuperior.add(separator);

		ImageIcon iconoRedOriginal = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/IconoRed.png"));
		Image redEscalada = iconoRedOriginal.getImage().getScaledInstance(88, 43, Image.SCALE_SMOOTH);
		JLabel lblEstadoRed = new JLabel("Red: Operacional");
		lblEstadoRed.setIcon(new ImageIcon(redEscalada));
		lblEstadoRed.setForeground(new Color(144, 238, 144));
		lblEstadoRed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblEstadoRed.setBounds(535, 15, 216, 43);
		panelSuperior.add(lblEstadoRed);

		JLabel lblSede = new JLabel("Sede: Santiago, RD");
		lblSede.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSede.setForeground(new Color(200, 200, 200));
		lblSede.setFont(new Font("Arial", Font.ITALIC, 13));
		lblSede.setBounds(anchoBarraSuperior - 450, 26, 150, 19);
		panelSuperior.add(lblSede);

		textFieldHora = new JTextField();
		textFieldHora.setEditable(false);
		textFieldHora.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldHora.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		textFieldHora.setBounds(anchoBarraSuperior - 280, 26, 135, 19);
		panelSuperior.add(textFieldHora);

		ImageIcon iconoOriginal = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/LogoUsuario.png"));
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(95, 48, Image.SCALE_SMOOTH);
		JLabel lblNewLabelLogo = new JLabel(new ImageIcon(imagenEscalada));
		lblNewLabelLogo.setBounds(anchoBarraSuperior - 120, 10, 95, 48);
		panelSuperior.add(lblNewLabelLogo);

		JLabel lblNewLabelFondoBarraSuperior = new JLabel("");
		ImageIcon imgBarra = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/FondoBarraSuperior.jpg"));
		Image imgBarraEscalada = imgBarra.getImage().getScaledInstance(anchoBarraSuperior, 68, Image.SCALE_SMOOTH);
		lblNewLabelFondoBarraSuperior.setIcon(new ImageIcon(imgBarraEscalada));
		lblNewLabelFondoBarraSuperior.setBounds(0, 0, anchoBarraSuperior, 68);
		panelSuperior.add(lblNewLabelFondoBarraSuperior);

		// --- 3. PANEL CUERPO (CENTRAL) ---
		panelCuerpo = new JPanel();
		panelCuerpo.setBackground(Color.WHITE);
		panelCuerpo.setBounds(223, 68, anchoBarraSuperior, altoPantalla - 68);
		contentPane.add(panelCuerpo);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		// Panel de contenido interno con scroll
		JPanel panelContenidoFijo = new JPanel();
		panelContenidoFijo.setBackground(Color.WHITE);
		panelContenidoFijo.setLayout(null);
		panelContenidoFijo.setPreferredSize(new Dimension(anchoBarraSuperior - 40, 1850)); 

		// A. Banner de Planes Proporcional
		JLabel lblBanner = new JLabel("");
		int altoBannerCalculado = 400; // Valor por defecto
		try {
			ImageIcon imgPromo = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/ImagenPromocional.jpg"));
			Image imgOriginal = imgPromo.getImage();
			int maxW = anchoBarraSuperior - 60;
			altoBannerCalculado = (maxW * imgOriginal.getHeight(null)) / imgOriginal.getWidth(null);
			lblBanner.setIcon(new ImageIcon(imgOriginal.getScaledInstance(maxW, altoBannerCalculado, Image.SCALE_SMOOTH)));
			lblBanner.setBounds(30, 20, maxW, altoBannerCalculado);
		} catch (Exception e) {
			lblBanner.setBounds(30, 20, anchoBarraSuperior - 60, 250);
			lblBanner.setText("BANNER PROMOCIONAL ALTICE");
			lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
			lblBanner.setBorder(new LineBorder(Color.LIGHT_GRAY));
		}
		panelContenidoFijo.add(lblBanner);

		// B. Sección de Noticias
		int yNoticias = lblBanner.getY() + lblBanner.getHeight() + 40;
		JLabel lblTitNoticia = new JLabel("NOTICIAS: ALTICE CONECTA");
		lblTitNoticia.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		lblTitNoticia.setForeground(new Color(0, 102, 204));
		lblTitNoticia.setBounds(30, yNoticias, 600, 30);
		panelContenidoFijo.add(lblTitNoticia);

		JTextArea txtNoticia = new JTextArea("La empresa de telecomunicaciones Altice Dominicana presentó oficialmente Altice Conecta, una innovadora plataforma de encuentros estratégicos que reunirá a líderes y expertos de distintas áreas con el propósito de ofrecer información de valor a directivos y empresarios del país. A través de esta iniciativa, se abordarán temas clave para el crecimiento empresarial y los desafíos contemporáneos desde múltiples perspectivas.\r\n"
				+ "\nLa plataforma Altice Conecta forma parte de la renovada estrategia de Altice Negocios, liderada por Mauricio Salazar, vicepresidente de Negocios de Altice, quien compartió esta novedad con los asistentes durante el lanzamiento.El primer foro de la iniciativa se llevó a cabo en Santiago de los Caballeros, reafirmando el compromiso de Altice con el desarrollo de la región Norte. La elección de esta ciudad responde al reconocimiento de su dinamismo económico y a la necesidad de acceso a conocimientos de vanguardia.\r\n"
				+ "");
		txtNoticia.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtNoticia.setLineWrap(true);
		txtNoticia.setWrapStyleWord(true);
		txtNoticia.setEditable(false);
		txtNoticia.setBackground(new Color(248, 249, 250));
		txtNoticia.setBorder(new CompoundBorder(new LineBorder(new Color(230, 230, 230)), new EmptyBorder(15, 15, 15, 15)));
		txtNoticia.setBounds(30, yNoticias + 40, anchoBarraSuperior - 80, 200);
		panelContenidoFijo.add(txtNoticia);

		// C. Sección de Valoraciones
        int yValoraciones = txtNoticia.getY() + txtNoticia.getHeight() + 40;
        JLabel lblTitVal = new JLabel("VALORACIONES DE NUESTROS CLIENTES");
        lblTitVal.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        lblTitVal.setForeground(new Color(0, 102, 204)); // Azul Altice
        lblTitVal.setBounds(30, yValoraciones, 500, 30);
        panelContenidoFijo.add(lblTitVal);

        ImageIcon iconoPersona = null;
        try {
            ImageIcon imgOriginalPersona = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/LogoValoraciones.png")); 
            Image imgEscaladaPersona = imgOriginalPersona.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            iconoPersona = new ImageIcon(imgEscaladaPersona);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo de persona, usando respaldo.");
        }

        ImageIcon iconoEstrellas = null;
        try {
            ImageIcon imgOriginalEstrellas = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/Estrellas.jpg")); 
            Image imgEscaladaEstrellas = imgOriginalEstrellas.getImage().getScaledInstance(80, 15, Image.SCALE_SMOOTH);
            iconoEstrellas = new ImageIcon(imgEscaladaEstrellas);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen de 5 estrellas.");
        }

        // Valoración 1: Leury
        JPanel pVal1 = new JPanel(null);
        pVal1.setBackground(Color.WHITE);
        pVal1.setBorder(new LineBorder(new Color(235, 235, 235), 1));
        pVal1.setBounds(30, yValoraciones + 45, anchoBarraSuperior - 80, 100); 
        
        JLabel icon1 = new JLabel(iconoPersona); 
        if (iconoPersona == null) icon1.setText("👤"); 
        icon1.setBounds(15, 15, 45, 45); pVal1.add(icon1);
        
        JLabel nom1 = new JLabel("Leury Castillo"); nom1.setFont(new Font("Arial", Font.BOLD, 14));
        nom1.setBounds(75, 15, 200, 20); pVal1.add(nom1);

        JLabel lblEstrellas1 = new JLabel(iconoEstrellas);
        lblEstrellas1.setBounds(75, 35, 80, 15); 
        pVal1.add(lblEstrellas1);
        
        JLabel com1 = new JLabel("<html><i>\"Sinceramente, la mejor empresa que existe. Tienen planes muy buenos precio-calidad. Altice es lo mejor.\"</i></html>");
        com1.setBounds(75, 50, anchoBarraSuperior - 170, 45); pVal1.add(com1); 
        panelContenidoFijo.add(pVal1);

        // Valoración 2: José
        JPanel pVal2 = new JPanel(null);
        pVal2.setBackground(Color.WHITE);
        pVal2.setBorder(new LineBorder(new Color(235, 235, 235), 1));
        pVal2.setBounds(30, pVal1.getY() + 115, anchoBarraSuperior - 80, 100); 
        
        JLabel icon2 = new JLabel(iconoPersona); 
        if (iconoPersona == null) icon2.setText("👤");
        icon2.setBounds(15, 15, 45, 45); pVal2.add(icon2);
        
        JLabel nom2 = new JLabel("José Alonso"); nom2.setFont(new Font("Arial", Font.BOLD, 14));
        nom2.setBounds(75, 15, 200, 20); pVal2.add(nom2);

        JLabel lblEstrellas2 = new JLabel(iconoEstrellas);
        lblEstrellas2.setBounds(75, 35, 80, 15);
        pVal2.add(lblEstrellas2);
        
        JLabel com2 = new JLabel("<html><i>\"De verdad quedé encantado con todo lo que ofrece esta empresa... esos muchachos se merecen su 100 en el proyecto.\"</i></html>");
        com2.setBounds(75, 50, anchoBarraSuperior - 170, 45); pVal2.add(com2);
        panelContenidoFijo.add(pVal2);

        // Valoración 3: Zoe
        JPanel pVal3 = new JPanel(null);
        pVal3.setBackground(Color.WHITE);
        pVal3.setBorder(new LineBorder(new Color(235, 235, 235), 1));
        pVal3.setBounds(30, pVal2.getY() + 115, anchoBarraSuperior - 80, 100); 
        
        JLabel icon3 = new JLabel(iconoPersona); 
        if (iconoPersona == null) icon3.setText("👤");
        icon3.setBounds(15, 15, 45, 45); pVal3.add(icon3);
        
        JLabel nom3 = new JLabel("Zoe Morales"); nom3.setFont(new Font("Arial", Font.BOLD, 14));
        nom3.setBounds(75, 15, 200, 20); pVal3.add(nom3);

        JLabel lblEstrellas3 = new JLabel(iconoEstrellas);
        lblEstrellas3.setBounds(75, 35, 80, 15);
        pVal3.add(lblEstrellas3);
        
        JLabel com3 = new JLabel("<html><i>\"Reporté un problema en mi casa y el técnico llegó en 15 minutos. No creo que exista una empresa tan eficiente.\"</i></html>");
        com3.setBounds(75, 50, anchoBarraSuperior - 170, 45); pVal3.add(com3);
        panelContenidoFijo.add(pVal3);

		// Scroll
		JScrollPane scrollCentral = new JScrollPane(panelContenidoFijo);
		scrollCentral.setBorder(null);
		scrollCentral.getVerticalScrollBar().setUnitIncrement(20);
		panelCuerpo.add(scrollCentral, BorderLayout.CENTER);

		Timer timerHora = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldHora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
			}
		});
		timerHora.start();

		configurarVistaSegunRol(rolUsuario);
	}

	private void crearMenuAdministrador(int altoPantalla,String rolUsuario) {
		JButton btnDash = crearBotonMenu("Dashboard", 203, 40, false);
		panelContenedorMenu.add(btnDash);

		final JButton btnGPers = crearBotonMenu("> Gestión de Personal", 203, 40, false);
		final JButton subRegPers = crearBotonMenu("   Registrar Personal", 203, 30, true);
		final JButton subListModPers = crearBotonMenu("   Listar y Modificar Personal", 203, 30, true); 
		final JButton subBajaPers = crearBotonMenu("   Dar de Baja / Reactivar", 203, 30, true); 

		panelContenedorMenu.add(btnGPers);
		panelContenedorMenu.add(subRegPers);
		panelContenedorMenu.add(subListModPers);
		panelContenedorMenu.add(subBajaPers);

		btnGPers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPersonalAbierto = !menuPersonalAbierto;
				btnGPers.setText(menuPersonalAbierto ? "v Gestión de Personal" : "> Gestión de Personal");
				subRegPers.setVisible(menuPersonalAbierto);
				subListModPers.setVisible(menuPersonalAbierto);
				subBajaPers.setVisible(menuPersonalAbierto); 
				panelContenedorMenu.revalidate();
			}
		});

		subRegPers.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { RegistrarPersonal aux = new RegistrarPersonal(); aux.setModal(true); aux.setVisible(true); } });
		subListModPers.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ListarPersonal aux = new ListarPersonal(); aux.setModal(true); aux.setVisible(true); } });
		subBajaPers.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { DarBajaPersonal aux = new DarBajaPersonal(); aux.setModal(true); aux.setVisible(true); } });

		final JButton btnGCli = crearBotonMenu("> Gestión de Clientes", 203, 40, false);
		final JButton subRegCli = crearBotonMenu("   Registrar Cliente / Contrato", 203, 30, true);
		final JButton subListCli = crearBotonMenu("   Listar y Modificar", 203, 30, true);
		final JButton subSuspender = crearBotonMenu("   Suspender / Activar Cliente", 203, 30, true); 
		final JButton subAlertas = crearBotonMenu("   Clientes en Alerta", 203, 30, true);
		final JButton subHistorial = crearBotonMenu("   Historial Pagos / Facturas", 203, 30, true);

		panelContenedorMenu.add(btnGCli);
		panelContenedorMenu.add(subRegCli);
		panelContenedorMenu.add(subListCli);
		panelContenedorMenu.add(subSuspender); 
		panelContenedorMenu.add(subAlertas);
		panelContenedorMenu.add(subHistorial);

		subRegCli.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { RegistrarCliente aux = new RegistrarCliente(); aux.setModal(true); aux.setVisible(true); } });
		subListCli.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ListarClientes aux = new ListarClientes(); aux.setModal(true); aux.setVisible(true); } });
		subSuspender.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { SuspenderCliente aux = new SuspenderCliente(); aux.setModal(true); aux.setVisible(true); } });
		subAlertas.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ClientesEnAlerta aux = new ClientesEnAlerta(); aux.setModal(true); aux.setVisible(true); } });
		subHistorial.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { HistorialCliente histo = new HistorialCliente(); histo.setModal(true); histo.setVisible(true); } });

		btnGCli.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        menuClientesAbierto = !menuClientesAbierto;
		        btnGCli.setText(menuClientesAbierto ? "v Gestión de Clientes" : "> Gestión de Clientes");
		        subRegCli.setVisible(menuClientesAbierto);
		        subListCli.setVisible(menuClientesAbierto);
		        subSuspender.setVisible(menuClientesAbierto); 
		        subAlertas.setVisible(menuClientesAbierto);
		        subHistorial.setVisible(menuClientesAbierto);
		        panelContenedorMenu.revalidate();
		    }
		});

		final JButton btnGServ = crearBotonMenu("> Gestión de Servicios", 203, 40, false);
		final JButton subCrearPlanes = crearBotonMenu("   Crear Planes", 203, 30, true);
		final JButton subListarMod = crearBotonMenu("   Listar y Modificar", 203, 30, true);
		final JButton subDesactivar = crearBotonMenu("   Desactivar Planes", 203, 30, true);
		final JButton subRecarga = crearBotonMenu("   Recarga", 203, 30, true);

		panelContenedorMenu.add(btnGServ);
		panelContenedorMenu.add(subCrearPlanes);
		panelContenedorMenu.add(subListarMod);
		panelContenedorMenu.add(subDesactivar);
		panelContenedorMenu.add(subRecarga);
		
		subCrearPlanes.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				CrearPlanes aux = new CrearPlanes();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subListarMod.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ListarServicios aux = new ListarServicios(rolUsuario); aux.setModal(true); aux.setVisible(true); } });
		subDesactivar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { DesactivarServicios aux = new DesactivarServicios(); aux.setModal(true); aux.setVisible(true); } });
		subRecarga.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { RegRecarga recarga = new RegRecarga(); recarga.setModal(true); recarga.setVisible(true); } });

		btnGServ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuReportesAbierto = !menuReportesAbierto; 
				btnGServ.setText(menuReportesAbierto ? "v Gestión de Servicios" : "> Gestión de Servicios");
				subCrearPlanes.setVisible(menuReportesAbierto);
				subListarMod.setVisible(menuReportesAbierto);
				subDesactivar.setVisible(menuReportesAbierto);
				subRecarga.setVisible(menuReportesAbierto);
				panelContenedorMenu.revalidate();
			}
		});

		final JButton btnReportes = crearBotonMenu("> Reportes Empresa", 203, 40, false);
		final JButton subFinanzas = crearBotonMenu("   Finanzas", 203, 30, true);
		final JButton subCalidad = crearBotonMenu("   Métricas de Calidad", 203, 30, true);
		final JButton subTickets = crearBotonMenu("   Panel de Tickets", 203, 30, true);
		final JButton subRanking = crearBotonMenu("   Ranking de Personal", 203, 30, true);
		final JButton subTopServ = crearBotonMenu("   Plan más contratado", 203, 30, true);
		final JButton subZonasInst = crearBotonMenu("   Instalaciones por Zona", 203, 30, true);
		final JButton subValoraciones = crearBotonMenu("   Valoraciones de Clientes", 203, 30, true);

		panelContenedorMenu.add(btnReportes);
		panelContenedorMenu.add(subFinanzas);
		panelContenedorMenu.add(subCalidad);
		panelContenedorMenu.add(subTickets);
		panelContenedorMenu.add(subRanking);
		panelContenedorMenu.add(subTopServ);
		panelContenedorMenu.add(subZonasInst);
		panelContenedorMenu.add(subValoraciones);

		btnReportes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuReportesAbierto = !menuReportesAbierto;
				btnReportes.setText(menuReportesAbierto ? "v Reportes Empresa" : "> Reportes Empresa");
				subFinanzas.setVisible(menuReportesAbierto);
				subCalidad.setVisible(menuReportesAbierto);
				subTickets.setVisible(menuReportesAbierto);
				subRanking.setVisible(menuReportesAbierto);
				subTopServ.setVisible(menuReportesAbierto);
				subZonasInst.setVisible(menuReportesAbierto);
				subValoraciones.setVisible(menuReportesAbierto);
				panelContenedorMenu.revalidate();
			}
		});
		subRanking.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				rankingPersonal aux = new rankingPersonal();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		subFinanzas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				totalGeneradoReporte aux = new totalGeneradoReporte();
				
				aux.setModal(true);
				aux.setVisible(true);
						
			}
		});
		subCalidad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ReporteValoraciones aux = new ReporteValoraciones();
				aux.setModal(true);
				aux.setVisible(true);
				
			}
		});
		subTickets.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reporteTicket aux = new reporteTicket();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});
		
		JButton btnRespaldo = new JButton("Generar Respaldo");
	    btnRespaldo.setBounds(10, altoPantalla - 150, 203, 40); 
	    btnRespaldo.setBackground(new Color(0, 102, 204)); 
	    btnRespaldo.setForeground(Color.WHITE);
	    btnRespaldo.setFont(new Font("Arial", Font.BOLD, 13));
	    btnRespaldo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    try {
			        sfd = new Socket("127.0.0.1", 7000);
			        
			        File file = new File("Alticee.dat"); 
			        DataInputStream aux = new DataInputStream(new FileInputStream(file));
			        SalidaSocket = new DataOutputStream(sfd.getOutputStream());
			        
			        int Byte;
			        while((Byte = aux.read()) != -1) {
			            SalidaSocket.write(Byte);
			        }
			        
			        SalidaSocket.flush();
			        aux.close();
			        SalidaSocket.close();
			        sfd.close();
			        
			        JOptionPane.showMessageDialog(null, "Respaldo enviado. Cerrando sistema...", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			        dispose(); 

			    } catch (IOException ioe) {
			        JOptionPane.showMessageDialog(null, "Error: " + ioe.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			    }
			}
	    });
				
	    ((Container)this.getContentPane().getComponent(0)).add(btnRespaldo);
		JButton btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(10, altoPantalla - 100, 203, 40);
		btnLogout.setBackground(new Color(220, 53, 69));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { 
				dispose(); 
				}
		});
		((Container)this.getContentPane().getComponent(0)).add(btnLogout);
	}

	private void crearMenuComercial(int altoPantalla,String rolusuario) {
		final JButton btnVentas = crearBotonMenu("> Gestión de Ventas", 203, 40, false);
		final JButton subNuevaVenta = crearBotonMenu("   Registrar Cliente / Contrato", 203, 30, true);
		final JButton subVentasRealizadas = crearBotonMenu("   Ventas Realizadas", 203, 30, true);
		final JButton subMisComisiones = crearBotonMenu("   Mis Comisiones", 203, 30, true);

		panelContenedorMenu.add(btnVentas);
		panelContenedorMenu.add(subNuevaVenta);
		panelContenedorMenu.add(subVentasRealizadas);
		panelContenedorMenu.add(subMisComisiones);

		btnVentas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuVentasOpen = !menuVentasOpen;
				btnVentas.setText(menuVentasOpen ? "v Gestión de Ventas" : "> Gestión de Ventas");
				subNuevaVenta.setVisible(menuVentasOpen);
				subVentasRealizadas.setVisible(menuVentasOpen);
				subMisComisiones.setVisible(menuVentasOpen);
				panelContenedorMenu.revalidate();
			}
		});
		
		subNuevaVenta.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { RegistrarCliente aux = new RegistrarCliente(); aux.setModal(true); aux.setVisible(true); } });
		
		subVentasRealizadas.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				verVentasRealizadas aux = new verVentasRealizadas();
				aux.setModal(true);
				aux.setVisible(true); }	
			});

		subMisComisiones.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				MisComisiones aux = new MisComisiones();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		
		final JButton btnGCliCom = crearBotonMenu("> Gestión de Clientes", 203, 40, false);
		final JButton subListCliCom = crearBotonMenu("   Listar y Modificar", 203, 30, true);
		final JButton subSuspenderCom = crearBotonMenu("   Suspender Cliente", 203, 30, true);

		panelContenedorMenu.add(btnGCliCom);
		panelContenedorMenu.add(subListCliCom);
		panelContenedorMenu.add(subSuspenderCom);

		btnGCliCom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuClientesAbierto = !menuClientesAbierto;
				btnGCliCom.setText(menuClientesAbierto ? "v Gestión de Clientes" : "> Gestión de Clientes");
				subListCliCom.setVisible(menuClientesAbierto);
				subSuspenderCom.setVisible(menuClientesAbierto);
				panelContenedorMenu.revalidate();
			}
		});

		subListCliCom.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ListarClientes aux = new ListarClientes(); aux.setModal(true); aux.setVisible(true); } });
		subSuspenderCom.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { SuspenderCliente aux = new SuspenderCliente(); aux.setModal(true); aux.setVisible(true); } });

		final JButton btnGServCom = crearBotonMenu("> Gestión de Servicios", 203, 40, false);
		final JButton subRecargasCom = crearBotonMenu("   Recargas", 203, 30, true);
		final JButton subCatalogoCom = crearBotonMenu("   Catálogo de Servicios", 203, 30, true);

		panelContenedorMenu.add(btnGServCom);
		panelContenedorMenu.add(subRecargasCom);
		panelContenedorMenu.add(subCatalogoCom);

		btnGServCom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuServOpenCom = !menuServOpenCom;
				btnGServCom.setText(menuServOpenCom ? "v Gestión de Servicios" : "> Gestión de Servicios");
				subRecargasCom.setVisible(menuServOpenCom);
				subCatalogoCom.setVisible(menuServOpenCom);
				panelContenedorMenu.revalidate();
			}
		});

		subRecargasCom.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { RegRecarga recarga = new RegRecarga(); recarga.setModal(true); recarga.setVisible(true); } });
		subCatalogoCom.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { ListarServicios aux = new ListarServicios(rolusuario); aux.setModal(true); aux.setVisible(true); } });

		JButton btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(10, altoPantalla - 100, 203, 40);
		btnLogout.setBackground(new Color(220, 53, 69));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { dispose(); }
		});
		((Container)this.getContentPane().getComponent(0)).add(btnLogout);
	}

	private void crearMenuCliente(int altoPantalla, String rolUsuario) {
		
		final JButton btnInfo = crearBotonMenu("> Mi Información", 203, 40, false);
		final JButton subContrato = crearBotonMenu("   Mi contrato", 203, 30, true);
		final JButton subEstado = crearBotonMenu("   Estado de Cuenta", 203, 30, true);
		final JButton subPagar = crearBotonMenu("   Pagar Contrato", 203, 30, true);
		final JButton subFacturas = crearBotonMenu("   Mis facturas", 203, 30, true);
		final JButton subMinutos = crearBotonMenu("   Consumo de Minutos", 203, 30, true);
		final JButton subMetodoPago = crearBotonMenu("   Método de pago", 203, 30, true);

		panelContenedorMenu.add(btnInfo);
		panelContenedorMenu.add(subContrato); panelContenedorMenu.add(subEstado);
		panelContenedorMenu.add(subPagar); panelContenedorMenu.add(subFacturas);
		panelContenedorMenu.add(subMinutos); panelContenedorMenu.add(subMetodoPago);
		
		subContrato.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				MiContrato aux = new MiContrato();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subEstado.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				EstadoCuenta aux = new EstadoCuenta();
				aux.setModal(true);
				aux.setVisible(true); }	
			});

		
		subMetodoPago.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				MetodoPago aux = new MetodoPago();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subPagar.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				PagarContrato aux = new PagarContrato();
				aux.setModal(true);
				aux.setVisible(true); }	
			});

		subFacturas.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				MisFacturas aux = new MisFacturas();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subMinutos.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				ConsumoMin aux = new ConsumoMin();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuInfoClienteOpen = !menuInfoClienteOpen;
				btnInfo.setText(menuInfoClienteOpen ? "v Mi Información" : "> Mi Información");
				subContrato.setVisible(menuInfoClienteOpen); subEstado.setVisible(menuInfoClienteOpen);
				subPagar.setVisible(menuInfoClienteOpen); subFacturas.setVisible(menuInfoClienteOpen);
				subMinutos.setVisible(menuInfoClienteOpen); subMetodoPago.setVisible(menuInfoClienteOpen);
				panelContenedorMenu.revalidate();
			}
		});

		final JButton btnSoporte = crearBotonMenu("> Soporte Técnico", 203, 40, false);
		final JButton subTicket = crearBotonMenu("   Generar Nuevo Ticket", 203, 30, true);

		panelContenedorMenu.add(btnSoporte); panelContenedorMenu.add(subTicket);

		
		subTicket.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				GenerarTicket aux = new GenerarTicket();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		btnSoporte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuSoporteClienteOpen = !menuSoporteClienteOpen;
				btnSoporte.setText(menuSoporteClienteOpen ? "v Soporte Técnico" : "> Soporte Técnico");
				subTicket.setVisible(menuSoporteClienteOpen);
				panelContenedorMenu.revalidate();
			}
		});

		final JButton btnVal = crearBotonMenu("> Valoraciones", 203, 40, false);
		final JButton subHacerVal = crearBotonMenu("   Hacer valoración", 203, 30, true);

		panelContenedorMenu.add(btnVal); panelContenedorMenu.add(subHacerVal);
		
		subHacerVal.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				HacerValoracion aux = new HacerValoracion();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		

		btnVal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuValoracionClienteOpen = !menuValoracionClienteOpen;
				btnVal.setText(menuValoracionClienteOpen ? "v Valoraciones" : "> Valoraciones");
				subHacerVal.setVisible(menuValoracionClienteOpen);
				panelContenedorMenu.revalidate();
			}
		});

		JButton btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(10, altoPantalla - 100, 203, 40);
		btnLogout.setBackground(new Color(220, 53, 69));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { dispose(); }
		});
		((Container)this.getContentPane().getComponent(0)).add(btnLogout);
	}

	// --- MÉTODO AGREGADO PARA EL ROL TÉCNICO ---
	private void crearMenuTecnico(int altoPantalla, String rolUsuario) {
		// --- 1. GESTIÓN DE TRABAJO ---
		final JButton btnTrabajo = crearBotonMenu("> Gestión de Trabajo", 203, 40, false);
		final JButton subInfo = crearBotonMenu("   Mi información", 203, 30, true);
		final JButton subOrdenes = crearBotonMenu("   Ordenes de servicio", 203, 30, true);

		panelContenedorMenu.add(btnTrabajo);
		panelContenedorMenu.add(subInfo);
		panelContenedorMenu.add(subOrdenes);
		
		subInfo.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				Mi_informacionTecnico aux = new Mi_informacionTecnico();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subOrdenes.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				OrdenesServicio aux = new OrdenesServicio();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		
		

		btnTrabajo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuTrabajoTecnicoOpen = !menuTrabajoTecnicoOpen;
				btnTrabajo.setText(menuTrabajoTecnicoOpen ? "v Gestión de Trabajo" : "> Gestión de Trabajo");
				subInfo.setVisible(menuTrabajoTecnicoOpen);
				subOrdenes.setVisible(menuTrabajoTecnicoOpen);
				panelContenedorMenu.revalidate();
			}
		});

		// --- 2. GESTIÓN DE RENDIMIENTO ---
		final JButton btnRendimiento = crearBotonMenu("> Gestión de Rendimiento", 203, 40, false);
		final JButton subEstadisticas = crearBotonMenu("   Mis estadísticas", 203, 30, true);
		final JButton subHoras = crearBotonMenu("   Horas extras", 203, 30, true);
		final JButton subBono = crearBotonMenu("   Reclamar bono", 203, 30, true);

		panelContenedorMenu.add(btnRendimiento);
		panelContenedorMenu.add(subEstadisticas);
		panelContenedorMenu.add(subHoras);
		panelContenedorMenu.add(subBono);
		
		subEstadisticas.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				MisEstadisticas aux = new MisEstadisticas();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		
		subHoras.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				HorasExtras aux = new HorasExtras();
				aux.setModal(true);
				aux.setVisible(true); }	
			});
		
		subBono.addActionListener(new ActionListener() 
		{
			@Override public void actionPerformed(ActionEvent e) 
		{
				ReclamarBono aux = new ReclamarBono();
				aux.setModal(true);
				aux.setVisible(true); }	
			});

		btnRendimiento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuRendimientoTecnicoOpen = !menuRendimientoTecnicoOpen;
				btnRendimiento.setText(menuRendimientoTecnicoOpen ? "v Gestión de Rendimiento" : "> Gestión de Rendimiento");
				subEstadisticas.setVisible(menuRendimientoTecnicoOpen);
				subHoras.setVisible(menuRendimientoTecnicoOpen);
				subBono.setVisible(menuRendimientoTecnicoOpen);
				panelContenedorMenu.revalidate();
			}
		});

		JButton btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(10, altoPantalla - 100, 203, 40);
		btnLogout.setBackground(new Color(220, 53, 69));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		((Container)this.getContentPane().getComponent(0)).add(btnLogout);
	}

	private JButton crearBotonMenu(String texto, int w, int h, boolean esSubmenu) {
		JButton btn = new JButton(texto);
		btn.setPreferredSize(new Dimension(w, h));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBackground(esSubmenu ? new Color(230, 230, 230) : new Color(245, 245, 245));
		btn.setFont(new Font("Arial", esSubmenu ? Font.PLAIN : Font.BOLD, 13));
		btn.setBorder(new EmptyBorder(0, 10, 0, 0));
		if (esSubmenu) btn.setVisible(false);
		return btn;
	}

	private void estilarBotonSuperior(JToggleButton btn) {
		btn.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setEnabled(false);
		UIManager.put("ToggleButton.disabledText", Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setOpaque(false);
	}

	private void actualizarBordesBotones() {
		JToggleButton[] botones = {tglbtnAdmin, tglbtnTecnico, tglbtnComercial, tglbtnCliente};
		for (JToggleButton b : botones) {
			if (b.isSelected()) {
				b.setBorder(new MatteBorder(0, 0, 3, 0, Color.WHITE));
				b.setBorderPainted(true);
			} else {
				b.setBorder(null);
			}
		}
	}

	private void configurarVistaSegunRol(String rol) {
		tglbtnAdmin.setSelected(rol.equalsIgnoreCase("Administrador"));
		tglbtnTecnico.setSelected(rol.equalsIgnoreCase("Tecnico"));
		tglbtnComercial.setSelected(rol.equalsIgnoreCase("Comercial"));
		tglbtnCliente.setSelected(rol.equalsIgnoreCase("Cliente"));
		actualizarBordesBotones();
	}
}