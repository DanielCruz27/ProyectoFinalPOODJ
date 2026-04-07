package Visual;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SistemaPrincipal frame = new SistemaPrincipal("administrador");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SistemaPrincipal(String rolUsuario) {
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

		if (rolUsuario.equalsIgnoreCase("Administrador")) {
			crearMenuAdministrador(altoPantalla);
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
		// --- C. SECCIÓN DE VALORACIONES ---
        int yValoraciones = txtNoticia.getY() + txtNoticia.getHeight() + 40;
        JLabel lblTitVal = new JLabel("VALORACIONES DE NUESTROS CLIENTES");
        lblTitVal.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        lblTitVal.setForeground(new Color(0, 102, 204)); // Azul Altice
        lblTitVal.setBounds(30, yValoraciones, 500, 30);
        panelContenidoFijo.add(lblTitVal);

        // --- LÓGICA PARA CARGAR EL LOGO DE PERSONA (REUTILIZADA) ---
        ImageIcon iconoPersona = null;
        try {
            // Asegúrate de que el nombre del archivo coincida (ej: LogoPersona.png)
            ImageIcon imgOriginalPersona = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/LogoValoraciones.png")); 
            Image imgEscaladaPersona = imgOriginalPersona.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            iconoPersona = new ImageIcon(imgEscaladaPersona);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo de persona, usando respaldo.");
        }

        // --- NUEVA LÓGICA PARA CARGAR LA IMAGEN DE 5 ESTRELLAS ---
        ImageIcon iconoEstrellas = null;
        try {
            // Reemplaza "Icono5Estrellas.png" con el nombre real de tu archivo de imagen
            ImageIcon imgOriginalEstrellas = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/Estrellas.jpg")); 
            // Escalamos a 80x15 para que quepa bien en el diseño
            Image imgEscaladaEstrellas = imgOriginalEstrellas.getImage().getScaledInstance(80, 15, Image.SCALE_SMOOTH);
            iconoEstrellas = new ImageIcon(imgEscaladaEstrellas);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen de 5 estrellas.");
        }

        // Valoración 1: Leury
        JPanel pVal1 = new JPanel(null);
        pVal1.setBackground(Color.WHITE);
        pVal1.setBorder(new LineBorder(new Color(235, 235, 235), 1));
        pVal1.setBounds(30, yValoraciones + 45, anchoBarraSuperior - 80, 100); // Aumentamos la altura a 100
        
        JLabel icon1 = new JLabel(iconoPersona); 
        if (iconoPersona == null) icon1.setText("👤"); 
        icon1.setBounds(15, 15, 45, 45); pVal1.add(icon1);
        
        JLabel nom1 = new JLabel("Leury Castillo"); nom1.setFont(new Font("Arial", Font.BOLD, 14));
        nom1.setBounds(75, 15, 200, 20); pVal1.add(nom1);

        // --- AGREGAMOS LAS ESTRELLAS EN LUGAR DEL TEXTO ---
        JLabel lblEstrellas1 = new JLabel(iconoEstrellas);
        lblEstrellas1.setBounds(75, 35, 80, 15); // Posicionamos debajo del nombre
        pVal1.add(lblEstrellas1);
        
        JLabel com1 = new JLabel("<html><i>\"Sinceramente, la mejor empresa que existe. Tienen planes muy buenos precio-calidad. Altice es lo mejor.\"</i></html>");
        com1.setBounds(75, 50, anchoBarraSuperior - 170, 45); pVal1.add(com1); // Movimos el comentario hacia abajo
        panelContenidoFijo.add(pVal1);

        // Valoración 2: José
        JPanel pVal2 = new JPanel(null);
        pVal2.setBackground(Color.WHITE);
        pVal2.setBorder(new LineBorder(new Color(235, 235, 235), 1));
        pVal2.setBounds(30, pVal1.getY() + 115, anchoBarraSuperior - 80, 100); // Ajustamos la posición Y y altura
        
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
        pVal3.setBounds(30, pVal2.getY() + 115, anchoBarraSuperior - 80, 100); // Ajustamos la posición Y y altura
        
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

	private void crearMenuAdministrador(int altoPantalla) {
		// --- 1. DASHBOARD ---
		JButton btnDash = crearBotonMenu("Dashboard", 203, 40, false);
		panelContenedorMenu.add(btnDash);

		// --- 2. GESTIÓN DE PERSONAL ---
		final JButton btnGPers = crearBotonMenu("> Gestión de Personal", 203, 40, false);
		final JButton subRegPers = crearBotonMenu("   Registrar Personal", 203, 30, true);
		final JButton subListPers = crearBotonMenu("   Listado de Personal", 203, 30, true);
		final JButton subZonas = crearBotonMenu("   Asignación de Zonas", 203, 30, true);

		panelContenedorMenu.add(btnGPers);
		panelContenedorMenu.add(subRegPers);
		panelContenedorMenu.add(subListPers);
		panelContenedorMenu.add(subZonas);

		
		
		btnGPers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPersonalAbierto = !menuPersonalAbierto;
				btnGPers.setText(menuPersonalAbierto ? "v Gestión de Personal" : "> Gestión de Personal");
				subRegPers.setVisible(menuPersonalAbierto);
				subListPers.setVisible(menuPersonalAbierto);
				subZonas.setVisible(menuPersonalAbierto);
				panelContenedorMenu.revalidate();
			}
		});
		subRegPers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RegistrarPersonal aux = new RegistrarPersonal();
				aux.setModal(true);
				aux.setVisible(true);
			}
		});

		// --- 3. GESTIÓN DE CLIENTES ---
		final JButton btnGCli = crearBotonMenu("> Gestión de Clientes", 203, 40, false);
		final JButton subRegCli = crearBotonMenu("   Registrar Cliente", 203, 30, true);
		final JButton subListCli = crearBotonMenu("   Listado Clientes", 203, 30, true);
		final JButton subAlertas = crearBotonMenu("   Clientes en Alerta", 203, 30, true);
		final JButton subHistorial = crearBotonMenu("   Historial Pagos/Contratos", 203, 30, true);

		panelContenedorMenu.add(btnGCli);
		panelContenedorMenu.add(subRegCli);
		panelContenedorMenu.add(subListCli);
		panelContenedorMenu.add(subAlertas);
		panelContenedorMenu.add(subHistorial);

		btnGCli.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuClientesAbierto = !menuClientesAbierto;
				btnGCli.setText(menuClientesAbierto ? "v Gestión de Clientes" : "> Gestión de Clientes");
				subRegCli.setVisible(menuClientesAbierto);
				subListCli.setVisible(menuClientesAbierto);
				subAlertas.setVisible(menuClientesAbierto);
				subHistorial.setVisible(menuClientesAbierto);
				panelContenedorMenu.revalidate();
			}
		});

		// --- 4. GESTIÓN DE SERVICIOS Y PLANES ---
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

		final boolean[] menuServOpen = {false}; 
		btnGServ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuServOpen[0] = !menuServOpen[0];
				btnGServ.setText(menuServOpen[0] ? "v Gestión de Servicios" : "> Gestión de Servicios");
				subCrearPlanes.setVisible(menuServOpen[0]);
				subListarMod.setVisible(menuServOpen[0]);
				subDesactivar.setVisible(menuServOpen[0]);
				subRecarga.setVisible(menuServOpen[0]);
				panelContenedorMenu.revalidate();
			}
		});
		// --- 5. REPORTES DE LA EMPRESA (BI) ---
				final JButton btnReportes = crearBotonMenu("> Reportes Empresa", 203, 40, false);
				final JButton subFinanzas = crearBotonMenu("   Finanzas", 203, 30, true);
				final JButton subCalidad = crearBotonMenu("   Métricas de Calidad", 203, 30, true);
				final JButton subTickets = crearBotonMenu("   Panel de Tickets", 203, 30, true);
				final JButton subRanking = crearBotonMenu("   Ranking de Personal", 203, 30, true);
				final JButton subTopServ = crearBotonMenu("   Plan más contratado", 203, 30, true);
				final JButton subZonasInst = crearBotonMenu("   Instalaciones por Zona", 203, 30, true);
				final JButton subValoraciones = crearBotonMenu("   Valoraciones de Clientes", 203, 30, true); // <--- NUEVO

				panelContenedorMenu.add(btnReportes);
				panelContenedorMenu.add(subFinanzas);
				panelContenedorMenu.add(subCalidad);
				panelContenedorMenu.add(subTickets);
				panelContenedorMenu.add(subRanking);
				panelContenedorMenu.add(subTopServ);
				panelContenedorMenu.add(subZonasInst);
				panelContenedorMenu.add(subValoraciones); // <--- AGREGADO AL PANEL

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
						subValoraciones.setVisible(menuReportesAbierto); // <--- CONTROL DE VISIBILIDAD
						panelContenedorMenu.revalidate();
					}
				});

		// --- BOTÓN CERRAR SESIÓN ---
		JButton btnLogout = new JButton("Cerrar Sesión");
		btnLogout.setBounds(10, altoPantalla - 100, 203, 40);
		btnLogout.setBackground(new Color(220, 53, 69));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.setFocusPainted(false);
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
		tglbtnAdmin.setSelected(false);
		tglbtnTecnico.setSelected(false);
		tglbtnComercial.setSelected(false);
		tglbtnCliente.setSelected(false);

		if (rol.equalsIgnoreCase("Administrador")) tglbtnAdmin.setSelected(true);
		else if (rol.equalsIgnoreCase("Técnico")) tglbtnTecnico.setSelected(true);
		else if (rol.equalsIgnoreCase("Comercial")) tglbtnComercial.setSelected(true);
		else if (rol.equalsIgnoreCase("Cliente")) tglbtnCliente.setSelected(true);

		actualizarBordesBotones();
	}
}