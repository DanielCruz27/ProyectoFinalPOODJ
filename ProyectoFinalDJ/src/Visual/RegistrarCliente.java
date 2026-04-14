package Visual;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class RegistrarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo, txtNombre, txtApellido, txtDireccion, txtEmail, txtCedula; 
	private JPasswordField txtPassword;
	private JComboBox<String> cbxVivienda, cbxPlanes;
	private JTable tableServicios;
	private DefaultTableModel tableModel;
	private JLabel lblTotal;

	private ArrayList<Servicio> serviciosParaContrato = new ArrayList<Servicio>();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			RegistrarCliente dialog = new RegistrarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarCliente() {
		setTitle("Altice - Registro de Cliente y Contratación");
		setModal(true);
		setResizable(false);
		setSize(650, 780); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 650, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("REGISTRO DE CLIENTE Y CONTRATO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JLabel lblId = new JLabel("Código:");
		lblId.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblId.setBounds(30, 60, 100, 14);
		contentPanel.add(lblId);

		txtCodigo = new JTextField("C-" + Altice.getInstance().getCodigoCliente());
		txtCodigo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(30, 80, 120, 25);
		contentPanel.add(txtCodigo);

		JLabel lblZona = new JLabel("Zona de Vivienda:");
		lblZona.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblZona.setBounds(180, 60, 150, 14);
		contentPanel.add(lblZona);

		cbxVivienda = new JComboBox<String>();
		cbxVivienda.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		cbxVivienda.setModel(new DefaultComboBoxModel<String>(new String[] {"<<Seleccionar>>", "Metropolitana", "Norte", "Sur", "Este"}));
		cbxVivienda.setBounds(180, 80, 180, 25);
		contentPanel.add(cbxVivienda);

		JLabel lblNom = new JLabel("Nombre:");
		lblNom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNom.setBounds(30, 120, 100, 14);
		contentPanel.add(lblNom);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtNombre.setBounds(30, 140, 250, 25);
		contentPanel.add(txtNombre);

		JLabel lblApe = new JLabel("Apellido:");
		lblApe.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblApe.setBounds(310, 120, 100, 14);
		contentPanel.add(lblApe);

		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtApellido.setBounds(310, 140, 250, 25);
		contentPanel.add(txtApellido);

		JLabel lblCed = new JLabel("Cédula:");
		lblCed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblCed.setBounds(30, 180, 100, 14);
		contentPanel.add(lblCed);

		txtCedula = new JTextField();
		txtCedula.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtCedula.setBounds(30, 200, 250, 25);
		contentPanel.add(txtCedula);

		JLabel lblDir = new JLabel("Dirección Completa:");
		lblDir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblDir.setBounds(310, 180, 200, 14);
		contentPanel.add(lblDir);

		txtDireccion = new JTextField();
		txtDireccion.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtDireccion.setBounds(310, 200, 250, 25);
		contentPanel.add(txtDireccion);

		JLabel lblEmail = new JLabel("Correo Electrónico:");
		lblEmail.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblEmail.setBounds(30, 240, 150, 14);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtEmail.setBounds(30, 260, 250, 25);
		contentPanel.add(txtEmail);

		JLabel lblPass = new JLabel("Contraseña de Acceso:");
		lblPass.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblPass.setBounds(310, 240, 200, 14);
		contentPanel.add(lblPass);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(310, 260, 250, 25);
		contentPanel.add(txtPassword);

		JPanel panelVenta = new JPanel();
		panelVenta.setBackground(new Color(250, 250, 250));
		TitledBorder bordeVenta = new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Configuración del Contrato ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelVenta.setBorder(bordeVenta);
		panelVenta.setBounds(30, 330, 580, 320);
		contentPanel.add(panelVenta);
		panelVenta.setLayout(null);

		cbxPlanes = new JComboBox<String>();
		cbxPlanes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		llenarComboPlanes();
		cbxPlanes.setBounds(20, 50, 380, 25);
		panelVenta.add(cbxPlanes);

		JButton btnAgregar = new JButton("Añadir al Contrato");
		btnAgregar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 11));
		btnAgregar.setBounds(410, 50, 160, 25);
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarServicio();
			}
		});
		panelVenta.add(btnAgregar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 90, 530, 180);
		panelVenta.add(scrollPane);

		String[] col = {"ID", "Servicio", "Tipo", "Mensualidad"};
		tableModel = new DefaultTableModel(null, col) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tableServicios = new JTable(tableModel);
		tableServicios.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		scrollPane.setViewportView(tableServicios);

		lblTotal = new JLabel("Total a Pagar: RD$ 0.00");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		lblTotal.setBounds(300, 285, 250, 20);
		panelVenta.add(lblTotal);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Finalizar Registro");
		btnRegistrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		btnRegistrar.setBackground(new Color(0, 153, 51));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				realizarRegistro();
			}
		});
		buttonPane.add(btnRegistrar);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancel);
	}

	private void llenarComboPlanes() {
		cbxPlanes.addItem("<< Seleccionar Plan Activo >>");
		for (Servicio s : Altice.getInstance().getCatalogoServicio()) {
			if (s.isEstadoDelServicio()) {
				cbxPlanes.addItem(s.getIdServicio() + " - " + s.getNombreServicio());
			}
		}
	}

	private void agregarServicio() {
		if (cbxPlanes.getSelectedIndex() > 0) {
			String id = cbxPlanes.getSelectedItem().toString().split(" - ")[0];
			Servicio s = Altice.getInstance().buscarServicioById(id);
			if (s != null && !serviciosParaContrato.contains(s)) {
				serviciosParaContrato.add(s);
				actualizarTabla();
			}
		}
	}

	private void actualizarTabla() {
		tableModel.setRowCount(0);
		float total = 0;
		for (Servicio s : serviciosParaContrato) {
			String tipo = (s instanceof PlanMovil) ? "Móvil" : "Hogar";
			tableModel.addRow(new Object[]{s.getIdServicio(), s.getNombreServicio(), tipo, s.getPrecioBase()});
			total += s.getPrecioBase();
		}
		lblTotal.setText("Total a Pagar: RD$ " + total);
	}

	private void realizarRegistro() {
		try {
			if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || 
					txtDireccion.getText().trim().isEmpty() || txtCedula.getText().trim().isEmpty() ||
					new String(txtPassword.getPassword()).isEmpty()) {
				throw new Exception("Todos los campos obligatorios deben estar llenos.");
			}

			if (cbxVivienda.getSelectedIndex() <= 0) {
				throw new Exception("Debe asignar una zona de vivienda al cliente.");
			}

			String nombre = txtNombre.getText().trim();
			String apellido = txtApellido.getText().trim();
			String userStr = nombre.toLowerCase().replace(" ", "") + "." + apellido.toLowerCase().replace(" ", "");

			if (!Altice.getInstance().buscarUsuario(userStr)) {
				Usuario user = new Usuario(userStr, new String(txtPassword.getPassword()));

				Cliente client = new Cliente(
						txtCodigo.getText(),            
						nombre,      
						apellido,    
						txtEmail.getText().trim(),       
						txtDireccion.getText().trim(),   
						txtCedula.getText().trim(),      
						user,                                           
						cbxVivienda.getSelectedItem().toString(), 
						0,                                              
						true,                                          
						null,                                           
						0.0f,                                           
						0,                                              
						new ArrayList<Pago>(),          
						null                            
						);

				Altice.getInstance().InsertaCliente(client);

				String idVendedor = "V-000";
				if (Altice.getInstance().getUsuarioLogueado() instanceof Personal) {
					idVendedor = ((Personal)Altice.getInstance().getUsuarioLogueado()).getIdEmpleado();
				}

				for (Servicio s : serviciosParaContrato) {
					Altice.getInstance().contratarServicio(client.getIdCliente(), s.getIdServicio(), idVendedor);
				}

				JOptionPane.showMessageDialog(this, "Registro Exitoso.\nUsuario: " + userStr, "Éxito", JOptionPane.INFORMATION_MESSAGE);
				clean();
			} else {
				JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
		}
	}

	protected void clean() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText(""); 
		txtDireccion.setText("");
		txtEmail.setText("");
		txtPassword.setText("");
		cbxVivienda.setSelectedIndex(0);
		cbxPlanes.setSelectedIndex(0);
		txtCodigo.setText("C-" + Altice.getInstance().getCodigoCliente());
		serviciosParaContrato.clear();
		tableModel.setRowCount(0);
		lblTotal.setText("Total a Pagar: RD$ 0.00");
		txtNombre.requestFocus();
	}
}