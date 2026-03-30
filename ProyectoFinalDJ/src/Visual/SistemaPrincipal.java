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
	private JTextField textFieldHora;
	
	// Declaramos los botones como globales para poder manipularlos por Rol
	private JToggleButton tglbtnAdmin, tglbtnTecnico, tglbtnComercial, tglbtnCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Simulación de inicio con rol de Administrador
					SistemaPrincipal frame = new SistemaPrincipal("Administrador");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
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
	    panelLateral.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	    panelLateral.setBounds(0, 0, 223, altoPantalla); 
	    contentPane.add(panelLateral);
	    
	    // --- 2. PANEL SUPERIOR ---
	    JPanel panelSuperior = new JPanel();
	    panelSuperior.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	    int anchoBarraSuperior = anchoPantalla - 223;
	    panelSuperior.setBounds(223, 0, anchoBarraSuperior, 68);
	    contentPane.add(panelSuperior);
	    panelSuperior.setLayout(null);
	    
	    // GRUPO DE BOTONES (Lógica de Roles)
	    ButtonGroup grupoRoles = new ButtonGroup();

	    tglbtnAdmin = new JToggleButton("Administrador");
	    tglbtnAdmin.setBounds(10, 20, 160, 30);
	    estilarBotonSuperior(tglbtnAdmin);
	    grupoRoles.add(tglbtnAdmin);
	    panelSuperior.add(tglbtnAdmin);

	    tglbtnTecnico = new JToggleButton("Técnico");
	    tglbtnTecnico.setBounds(180, 20, 135, 30);
	    estilarBotonSuperior(tglbtnTecnico);
	    grupoRoles.add(tglbtnTecnico);
	    panelSuperior.add(tglbtnTecnico);

	    tglbtnComercial = new JToggleButton("Comercial");
	    tglbtnComercial.setBounds(325, 20, 140, 30);
	    estilarBotonSuperior(tglbtnComercial);
	    grupoRoles.add(tglbtnComercial);
	    panelSuperior.add(tglbtnComercial);

	    tglbtnCliente = new JToggleButton("Cliente");
	    tglbtnCliente.setBounds(475, 20, 135, 30);
	    estilarBotonSuperior(tglbtnCliente);
	    grupoRoles.add(tglbtnCliente);
	    panelSuperior.add(tglbtnCliente);
	    
	    // --- RELOJ ---
	    textFieldHora = new JTextField();
	    textFieldHora.setEditable(false); 
	    textFieldHora.setHorizontalAlignment(SwingConstants.CENTER); 
	    textFieldHora.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
	    textFieldHora.setBounds(anchoBarraSuperior - 280, 26, 135, 19); // Posición responsiva
	    panelSuperior.add(textFieldHora);
	    textFieldHora.setColumns(10);
	    
	    // --- LOGO DE USUARIO ---
	    ImageIcon iconoOriginal = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/LogoUsuario.png"));
	    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(95, 48, Image.SCALE_SMOOTH);
	    JLabel lblNewLabelLogo = new JLabel(new ImageIcon(imagenEscalada));
	    lblNewLabelLogo.setBounds(anchoBarraSuperior - 120, 10, 95, 48); 
	    panelSuperior.add(lblNewLabelLogo);
	    
	    // --- FONDO BARRA SUPERIOR (Debe ir de último para no tapar lo anterior) ---
	    JLabel lblNewLabelFondoBarraSuperior = new JLabel("");
	    ImageIcon imgBarra = new ImageIcon(SistemaPrincipal.class.getResource("/Recursos/FondoBarraSuperior.jpg"));
	    Image imgBarraEscalada = imgBarra.getImage().getScaledInstance(anchoBarraSuperior, 68, Image.SCALE_SMOOTH);
	    lblNewLabelFondoBarraSuperior.setIcon(new ImageIcon(imgBarraEscalada));
	    lblNewLabelFondoBarraSuperior.setBounds(0, 0, anchoBarraSuperior, 68);
	    panelSuperior.add(lblNewLabelFondoBarraSuperior);
	    
	    // INICIAR RELOJ
	    Timer timerHora = new Timer(1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            textFieldHora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
	        }
	    });
	    timerHora.start();
	    
	    // --- ACTIVAR ROL LOGUEADO ---
	    configurarVistaSegunRol(rolUsuario);
	}
	
	private void estilarBotonSuperior(JToggleButton btn) {
	    btn.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
	    btn.setForeground(Color.WHITE);
	    btn.setFocusPainted(false);
	    btn.setBorderPainted(false);
	    btn.setContentAreaFilled(false);
	    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    // Este listener mantiene el borde inferior si el botón está seleccionado
	    btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            actualizarBordesBotones();
	        }
	    });
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
	    if (rol.equalsIgnoreCase("Administrador")) {
	        tglbtnAdmin.setSelected(true);
	    } else if (rol.equalsIgnoreCase("Técnico")) {
	        tglbtnTecnico.setSelected(true);
	        tglbtnAdmin.setEnabled(false);
	    } else if (rol.equalsIgnoreCase("Comercial")) {
	        tglbtnComercial.setSelected(true);
	        tglbtnAdmin.setEnabled(false);
	    } else if (rol.equalsIgnoreCase("Cliente")) {
	        tglbtnCliente.setSelected(true);
	        tglbtnAdmin.setVisible(false);
	        tglbtnTecnico.setVisible(false);
	        tglbtnComercial.setVisible(false);
	    }
	    actualizarBordesBotones();
	}
}