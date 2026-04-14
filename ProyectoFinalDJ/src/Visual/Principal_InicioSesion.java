package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Logico.Altice;
import Logico.Cliente;
import Logico.Personal;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal_InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				boolean cargado = false;
				String nombreRespaldoHoy = "Altice_Respaldo_" + java.time.LocalDate.now() + ".dat";
				File archivoPrincipal = new File("Alticee.dat");

				try {
					if (archivoPrincipal.exists()) {
						FileInputStream fileIn = new FileInputStream(archivoPrincipal);
						ObjectInputStream objectIn = new ObjectInputStream(fileIn);
						Altice.setInstance((Altice) objectIn.readObject());
						objectIn.close();
						fileIn.close();
						cargado = true;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al leer Alticee.dat: " + e.getMessage(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
				}

				if (!cargado) {
					try {
						File archivoRespaldo = new File(nombreRespaldoHoy);
						String rutaEscritorio = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + nombreRespaldoHoy;
						File archivoRespaldoDesktop = new File(rutaEscritorio);

						File archivoALeer = null;
						if (archivoRespaldo.exists()) {
							archivoALeer = archivoRespaldo;
						} else if (archivoRespaldoDesktop.exists()) {
							archivoALeer = archivoRespaldoDesktop;
						}

						if (archivoALeer != null) {
							FileInputStream fileIn = new FileInputStream(archivoALeer);
							ObjectInputStream objectIn = new ObjectInputStream(fileIn);
							Altice.setInstance((Altice) objectIn.readObject());
							objectIn.close();
							fileIn.close();
							cargado = true;

						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No se pudo cargar el respaldo: " + ex.getMessage(), "Error de Respaldo", JOptionPane.ERROR_MESSAGE);
					}
				}

				if (!cargado) {
					JOptionPane.showMessageDialog(null, "No se encontró base de datos ni respaldo. El sistema iniciará vacío.", "Inicio Limpio", JOptionPane.WARNING_MESSAGE);
				}

				try {
					Principal_InicioSesion frame = new Principal_InicioSesion();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error crítico al iniciar la interfaz: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	public Principal_InicioSesion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal_InicioSesion.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Altice - Inicio de Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int anchoPantalla = dim.width;
		int altoPantalla = dim.height - 40; 
		setSize(anchoPantalla, altoPantalla);
		setLocationRelativeTo(null); 
		setExtendedState(JFrame.MAXIMIZED_BOTH); 

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(null); 
		contentPane.add(panelPrincipal, BorderLayout.CENTER);

		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new LineBorder(new Color(0, 102, 204), 4, true)); 
		panelLogin.setBackground(new Color(255, 255, 255, 245)); 

		int anchoLogin = 480;
		int altoLogin = 550;
		int x = (anchoPantalla - anchoLogin) / 2;
		int y = (altoPantalla - altoLogin) / 2;

		panelLogin.setBounds(x, y, anchoLogin, altoLogin);
		panelPrincipal.add(panelLogin);
		panelLogin.setLayout(null);

		JLabel lblLogo = new JLabel("");
		ImageIcon iconoOriginal = new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/LogoAltice.jpg"));
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(imagenEscalada));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(140, 20, 200, 120);
		panelLogin.add(lblLogo);

		JLabel lblTitulo = new JLabel("Iniciar Sesión");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 22));
		lblTitulo.setBounds(0, 155, 480, 30);
		panelLogin.add(lblTitulo);

		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblUser.setBounds(65, 210, 100, 20);
		panelLogin.add(lblUser);

		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtUser.setBorder(new LineBorder(new Color(180, 180, 180)));
		txtUser.setBounds(65, 235, 350, 30);
		panelLogin.add(txtUser);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblPass.setBounds(65, 285, 120, 20);
		panelLogin.add(lblPass);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBorder(new LineBorder(new Color(180, 180, 180)));
		passwordField.setBounds(65, 310, 350, 30);
		panelLogin.add(passwordField);

		JButton btnAcceder = new JButton("Acceder");
		btnAcceder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcceder.setBackground(new Color(0, 102, 204));
		btnAcceder.setForeground(Color.WHITE);
		btnAcceder.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		btnAcceder.setBounds(165, 375, 150, 40);
		btnAcceder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String user = txtUser.getText();
				String password = new String(passwordField.getPassword());
				Object logueado = Altice.getInstance().verificarAccesoUniversal(user, password);

				if (logueado != null) {
					String rol = "";
					if (logueado instanceof Personal) {
						Personal emp = (Personal) logueado;
						Altice.getInstance().setUsuarioLogueado(emp);
						rol = emp.getRol(); 
					} else if (logueado instanceof Cliente) {
						Altice.getInstance().setUsuarioLogueado(logueado);
						rol = "Cliente";
					}
					SistemaPrincipal sistema = new SistemaPrincipal(rol);
					sistema.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Altice - Error", JOptionPane.ERROR_MESSAGE);
				}

				/*SistemaPrincipal sistema = new SistemaPrincipal("administrador");
				sistema.setVisible(true);
				dispose();*/
			}
		});
		panelLogin.add(btnAcceder);

		JLabel lblOlvido = new JLabel("<html><u>¿Has olvidado tu contraseña?</u></html>");
		lblOlvido.setHorizontalAlignment(SwingConstants.CENTER);
		lblOlvido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblOlvido.setForeground(new Color(0, 102, 204));
		lblOlvido.setFont(new Font("Arial", Font.PLAIN, 13));
		lblOlvido.setBounds(0, 440, 480, 20);
		lblOlvido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String usuarioABuscar = txtUser.getText().trim();

				if (usuarioABuscar.isEmpty()) {
					JOptionPane.showMessageDialog(Principal_InicioSesion.this, 
							"Por favor, escribe tu nombre de usuario en el campo 'Usuario' para recuperarlo.", 
							"Atención", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Cliente clienteEncontrado = null;
				for (Cliente c : Logico.Altice.getInstance().getListaClientes()) {
					if (c.getMiCuenta().getNombreUsuario().equalsIgnoreCase(usuarioABuscar)) {
						clienteEncontrado = c;
						break;
					}
				}

				if (clienteEncontrado != null) {
					JOptionPane.showMessageDialog(Principal_InicioSesion.this, 
							"RECUPERACIÓN DE DATOS\n\n" +
									"Usuario: " + clienteEncontrado.getMiCuenta().getNombreUsuario() + "\n" +
									"Contraseña: " + clienteEncontrado.getMiCuenta().getContraseña(), 
									"Recuperación Exitosa", JOptionPane.INFORMATION_MESSAGE);
				} else {
					Personal empEncontrado = null;
					for (Personal p : Logico.Altice.getInstance().getListaEmpleados()) {
						if (p.getMiCuenta().getNombreUsuario().equalsIgnoreCase(usuarioABuscar)) {
							empEncontrado = p;
							break;
						}
					}

					if (empEncontrado != null) {
						JOptionPane.showMessageDialog(Principal_InicioSesion.this, 
								"RECUPERACIÓN DE DATOS (PERSONAL)\n\n" +
										"Usuario: " + empEncontrado.getMiCuenta().getNombreUsuario() + "\n" +
										"Contraseña: " + empEncontrado.getMiCuenta().getContraseña(), 
										"Recuperación Exitosa", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(Principal_InicioSesion.this, 
								"El usuario '" + usuarioABuscar + "' no existe en el sistema.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panelLogin.add(lblOlvido);

		JLabel lblFondo = new JLabel("");
		try {
			ImageIcon iconFondo = new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/Altice_Inicio.png"));
			Image imgEscalada = iconFondo.getImage().getScaledInstance(anchoPantalla, altoPantalla, Image.SCALE_SMOOTH);
			lblFondo.setIcon(new ImageIcon(imgEscalada));
		} catch (Exception e) {
			lblFondo.setBackground(new Color(30, 30, 30));
			lblFondo.setOpaque(true);
		}
		lblFondo.setBounds(0, 0, anchoPantalla, altoPantalla);
		panelPrincipal.add(lblFondo);

		panelPrincipal.setComponentZOrder(lblFondo, panelPrincipal.getComponentCount() - 1);
	}
}