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
	
	private JToggleButton tglbtnAdmin, tglbtnTecnico, tglbtnComercial, tglbtnCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SistemaPrincipal frame = new SistemaPrincipal("Administrador");//Cambiar esto cuando funcione el log in
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
	    
	    JPanel panelLateral = new JPanel();
	    panelLateral.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	    panelLateral.setBounds(0, 0, 223, altoPantalla); 
	    contentPane.add(panelLateral);
	    
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

	    lblEstadoRed.setBounds(599, 15, 216, 43); 
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
	    
	    Timer timerHora = new Timer(1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            textFieldHora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
	        }
	    });
	    timerHora.start();
	    
	    configurarVistaSegunRol(rolUsuario);
	}
	
	//USar al final cuando terminemos proyecto
		/*private ImageIcon obtenerIconoEscalado(String ruta, int ancho, int alto) {
		    ImageIcon iconoOriginal = new ImageIcon(SistemaPrincipal.class.getResource(ruta));
		    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		    return new ImageIcon(imagenEscalada);
		}*/
	
	private void estilarBotonSuperior(JToggleButton btn) {
	    btn.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
	    btn.setForeground(Color.WHITE);
	    
	    btn.setEnabled(false); 
	    
	    UIManager.put("ToggleButton.disabledText", Color.WHITE);
	    
	    btn.setFocusPainted(false);
	    btn.setBorderPainted(false);
	    btn.setContentAreaFilled(false);
	    btn.setOpaque(false);
	    
	    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

	    if (rol.equalsIgnoreCase("Administrador")) {
	        tglbtnAdmin.setSelected(true);
	    } else if (rol.equalsIgnoreCase("Técnico")) {
	        tglbtnTecnico.setSelected(true);
	    } else if (rol.equalsIgnoreCase("Comercial")) {
	        tglbtnComercial.setSelected(true);
	    } else if (rol.equalsIgnoreCase("Cliente")) {
	        tglbtnCliente.setSelected(true);
	    }
	    
	    actualizarBordesBotones();
	}
}