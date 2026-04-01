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
     // --- 1. PANEL LATERAL ---
        JPanel panelLateral = new JPanel();
        panelLateral.setOpaque(false); // <--- AÑADE ESTA LÍNEA
        panelLateral.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        panelLateral.setBounds(0, 0, 223, altoPantalla);
        contentPane.add(panelLateral);
        panelLateral.setLayout(null);

        // 1. PRIMERO: El Logo (debe estar arriba)
        JLabel lblLogoLateral = new JLabel("");
        ImageIcon imgLogoLat = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/AlticeMenuLogo.png"));
        Image logoLatEscalado = imgLogoLat.getImage().getScaledInstance(160, 70, Image.SCALE_SMOOTH);
        lblLogoLateral.setIcon(new ImageIcon(logoLatEscalado));
        lblLogoLateral.setBounds(31, 11, 160, 70);
        panelLateral.add(lblLogoLateral);

        // 2. SEGUNDO: El Contenedor de botones (debe ser transparente)
     // Contenedor para los botones del menú
        panelContenedorMenu = new JPanel();
        panelContenedorMenu.setOpaque(false); // <--- ESTO ES VITAL
        panelContenedorMenu.setBackground(new Color(0,0,0,0)); // Color totalmente transparente
        panelContenedorMenu.setBounds(0, 92, 223, altoPantalla - 200);
        panelLateral.add(panelContenedorMenu);
        panelContenedorMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Cargamos los botones (se agregan al contenedor transparente)
        if (rolUsuario.equalsIgnoreCase("Administrador")) {
            crearMenuAdministrador(altoPantalla);
        }
        
        // 3. TERCERO: El Fondo (DEBE AGREGARSE DESPUÉS DE LOS DEMÁS Y ENVIARSE AL FONDO)
        JLabel lblFondoLateral = new JLabel("");
        ImageIcon imgFondoLat = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/FondoDegradado.jpg"));
        Image fondoLatEscalado = imgFondoLat.getImage().getScaledInstance(223, altoPantalla, Image.SCALE_SMOOTH);
        lblFondoLateral.setIcon(new ImageIcon(fondoLatEscalado));
        lblFondoLateral.setBounds(0, 0, 223, altoPantalla);
        panelLateral.add(lblFondoLateral);

        // TRUCO FINAL: Forzar que el fondo sea la última capa
        panelLateral.setComponentZOrder(lblFondoLateral, panelLateral.getComponentCount() - 1);
        // --- 2. PANEL SUPERIOR ---
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        int anchoBarraSuperior = anchoPantalla - 223;
        panelSuperior.setBounds(223, 0, anchoBarraSuperior, 68);
        contentPane.add(panelSuperior);
        panelSuperior.setLayout(null);

        // Botones de Roles
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

        // Estado de Red
        ImageIcon iconoRedOriginal = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/IconoRed.png"));
        Image redEscalada = iconoRedOriginal.getImage().getScaledInstance(88, 43, Image.SCALE_SMOOTH);
        JLabel lblEstadoRed = new JLabel("Red: Operacional");
        lblEstadoRed.setIcon(new ImageIcon(redEscalada));
        lblEstadoRed.setForeground(new Color(144, 238, 144));
        lblEstadoRed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
        lblEstadoRed.setBounds(535, 15, 216, 43);
        panelSuperior.add(lblEstadoRed);

        // Sede y Hora
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

        // Logo Usuario
        ImageIcon iconoOriginal = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/LogoUsuario.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(95, 48, Image.SCALE_SMOOTH);
        JLabel lblNewLabelLogo = new JLabel(new ImageIcon(imagenEscalada));
        lblNewLabelLogo.setBounds(anchoBarraSuperior - 120, 10, 95, 48);
        panelSuperior.add(lblNewLabelLogo);

        // Fondo Barra Superior
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
        panelCuerpo.setLayout(null);

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
        final JButton subCatPlanes = crearBotonMenu("   Catálogo de Planes", 203, 30, true);
        
        panelContenedorMenu.add(btnGServ);
        panelContenedorMenu.add(subCatPlanes);

        final boolean[] menuServOpen = {false}; 
        btnGServ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuServOpen[0] = !menuServOpen[0];
                btnGServ.setText(menuServOpen[0] ? "v Gestión de Servicios" : "> Gestión de Servicios");
                subCatPlanes.setVisible(menuServOpen[0]);
                panelContenedorMenu.revalidate();
            }
        });

        // --- 5. REPORTES DE LA EMPRESA (BI) ---
        final JButton btnReportes = crearBotonMenu("> Reportes Empresa", 203, 40, false);
        final JButton subFinanzas = crearBotonMenu("   Finanzas", 203, 30, true);
        final JButton subCalidad = crearBotonMenu("   Métricas de Calidad", 203, 30, true);
        final JButton subTickets = crearBotonMenu("   Panel de Tickets", 203, 30, true);
        final JButton subRanking = crearBotonMenu("   Ranking de Personal", 203, 30, true);
        final JButton subTopServ = crearBotonMenu("   Plan más contratado", 203, 30, true);
        final JButton subZonasInst = crearBotonMenu("   Instalaciones por Zona", 203, 30, true);

        panelContenedorMenu.add(btnReportes);
        panelContenedorMenu.add(subFinanzas);
        panelContenedorMenu.add(subCalidad);
        panelContenedorMenu.add(subTickets);
        panelContenedorMenu.add(subRanking);
        panelContenedorMenu.add(subTopServ);
        panelContenedorMenu.add(subZonasInst);

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