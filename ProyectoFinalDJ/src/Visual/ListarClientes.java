package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;
import java.util.ArrayList;

public class ListarClientes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableClientes;
	private DefaultTableModel modelClientes;
	private JTextField txtNombre, txtApellido, txtEmail, txtDireccion, txtUsuarioAuto, txtCedula;
	private JComboBox<String> cbxZona, cbxNuevosPlanes;
	private JList<String> listPlanes;
	private DefaultListModel<String> listModel;
	private JPanel panelEditar;
	private Cliente seleccionado = null;
	private JButton btnGuardar, btnAgregarPlan, btnEliminarPlan;

	

	public ListarClientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListarClientes.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Listar y Modificar");
		setSize(1250, 700);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 1250, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("LISTADO DE CLIENTES Y MODIFICACIÓN");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setBounds(20, 60, 850, 550);
		contentPanel.add(scrollTable);

		String[] columnas = {"ID", "Nombre", "Zona", "Fecha Reg.", "Servicios", "Puntos", "Deuda", "Atrasos", "Estado"};
		modelClientes = new DefaultTableModel(null, columnas) {

			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { 
				return false;
			}
		};
		tableClientes = new JTable(modelClientes);
		tableClientes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		tableClientes.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		tableClientes.getTableHeader().setBackground(new Color(0, 102, 204));
		tableClientes.getTableHeader().setForeground(Color.WHITE);
		tableClientes.setRowHeight(22);
		scrollTable.setViewportView(tableClientes);

		panelEditar = new JPanel();
		panelEditar.setBackground(Color.WHITE);
		panelEditar.setLayout(null);
		TitledBorder borde = BorderFactory.createTitledBorder(
				new LineBorder(new Color(0, 102, 204), 1, true), " Detalles del Cliente ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelEditar.setBorder(borde);
		panelEditar.setBounds(890, 60, 330, 550);
		contentPanel.add(panelEditar);

		JLabel l1 = new JLabel("Nombre:");
		l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l1.setBounds(20, 30, 80, 14); 
		panelEditar.add(l1);
		txtNombre = new JTextField(); 
		txtNombre.setEditable(false);
		txtNombre.setBounds(20, 48, 140, 25); 
		panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Apellido:");
		l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l2.setBounds(175, 30, 80, 14); 
		panelEditar.add(l2);
		txtApellido = new JTextField(); 
		txtApellido.setEditable(false);
		txtApellido.setBounds(175, 48, 140, 25); 
		panelEditar.add(txtApellido);

		JLabel lCed = new JLabel("Cédula:");
		lCed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		lCed.setBounds(20, 78, 100, 14); 
		panelEditar.add(lCed);
		txtCedula = new JTextField(); 
		txtCedula.setEditable(false);
		txtCedula.setBackground(new Color(245, 245, 245));
		txtCedula.setBounds(20, 95, 295, 25); 
		panelEditar.add(txtCedula);

		JLabel lUsuario = new JLabel("Usuario de Acceso:");
		lUsuario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 11));
		lUsuario.setBounds(20, 125, 150, 14); 
		panelEditar.add(lUsuario);
		txtUsuarioAuto = new JTextField(); 
		txtUsuarioAuto.setEditable(false);
		txtUsuarioAuto.setBackground(new Color(245, 245, 245));
		txtUsuarioAuto.setBounds(20, 142, 295, 25);
		panelEditar.add(txtUsuarioAuto);

		JLabel l3 = new JLabel("Dirección:");
		l3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l3.setBounds(20, 175, 100, 14); 
		panelEditar.add(l3);
		txtDireccion = new JTextField();
		txtDireccion.setBounds(20, 192, 295, 25); 
		panelEditar.add(txtDireccion);

		JLabel l4 = new JLabel("Zona:");
		l4.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l4.setBounds(20, 222, 100, 14); 
		panelEditar.add(l4);
		cbxZona = new JComboBox<String>(new String[]{"Metropolitana", "Norte", "Sur", "Este"});
		cbxZona.setBounds(20, 240, 140, 25); 
		panelEditar.add(cbxZona);

		JLabel l5 = new JLabel("Email:");
		l5.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l5.setBounds(175, 222, 100, 14); 
		panelEditar.add(l5);
		txtEmail = new JTextField();
		txtEmail.setBounds(175, 240, 140, 25); 
		panelEditar.add(txtEmail);

		JSeparator sep = new JSeparator();
		sep.setBounds(20, 275, 300, 2); 
		panelEditar.add(sep);

		JLabel l6 = new JLabel("Planes Activos:");
		l6.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		l6.setBounds(20, 282, 300, 14); 
		panelEditar.add(l6);

		listModel = new DefaultListModel<String>();
		listPlanes = new JList<String>(listModel);
		JScrollPane scrollP = new JScrollPane(listPlanes);
		scrollP.setBounds(20, 300, 295, 80); 
		panelEditar.add(scrollP);

		btnEliminarPlan = new JButton("Quitar Plan Seleccionado");
		btnEliminarPlan.setFont(new Font("Arial Narrow", Font.BOLD, 11));
		btnEliminarPlan.setBounds(20, 385, 295, 23);
		btnEliminarPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { quitarPlan(); }
		});
		panelEditar.add(btnEliminarPlan);

		JLabel l7 = new JLabel("Vender Nuevo Plan:");
		l7.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l7.setBounds(20, 415, 200, 14); 
		panelEditar.add(l7);

		cbxNuevosPlanes = new JComboBox<String>();
		llenarComboPlanes();
		cbxNuevosPlanes.setBounds(20, 432, 200, 25);
		panelEditar.add(cbxNuevosPlanes);

		btnAgregarPlan = new JButton("Añadir");
		btnAgregarPlan.setBounds(230, 432, 85, 25);
		btnAgregarPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { agregarNuevoPlan(); }
		});
		panelEditar.add(btnAgregarPlan);

		btnGuardar = new JButton("GUARDAR CAMBIOS");
		btnGuardar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(65, 490, 200, 35);
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { guardarCambios(); }
		});
		panelEditar.add(btnGuardar);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		buttonPane.add(btnCerrar);

		toggleCampos(false);
		tableClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { cargarDatosSeleccionado(); }
		});
		cargarTabla();
	}

	private void cargarTabla() {
		modelClientes.setRowCount(0);
		for (Cliente c : Altice.getInstance().getListaClientes()) {
			int totalServ = Altice.getInstance().contarCantServiciosDeCliente(c.getIdCliente());

			int atrasosReales = Altice.getInstance().calcularAtrasosReales(c);
			float montoDeuda = Altice.getInstance().calcularMontoDeudaReal(c);

			String fechaReg = "N/A";
			if (c.getMiContrato() != null) {
				fechaReg = c.getMiContrato().getFechaFirma().toString();
			}

			String deudaStr = (montoDeuda > 0) ? "RD$ " + montoDeuda : "No";
			String estado = (atrasosReales > 2 || !c.isEstadoCliente()) ? "Suspendido" : "Activo";

			modelClientes.addRow(new Object[]{
					c.getIdCliente(), 
					c.getNombreCliente() + " " + c.getApellidoCliente(), 
					c.getZonaVivienda(), 
					fechaReg,
					totalServ,
					c.getPuntosAcumulados(),
					deudaStr,
					atrasosReales,
					estado
			});
		}
	}

	private void cargarDatosSeleccionado() {
		int fila = tableClientes.getSelectedRow();
		if (fila >= 0) {
			String id = (String) tableClientes.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarCliente(id);

			if (seleccionado != null) {
				int atrasosActuales = Altice.getInstance().calcularAtrasosReales(seleccionado);


				boolean estaSuspendido = (atrasosActuales > 2 || !seleccionado.isEstadoCliente());

				txtNombre.setText(seleccionado.getNombreCliente());
				txtApellido.setText(seleccionado.getApellidoCliente());
				txtEmail.setText(seleccionado.getEmailCliente());
				txtDireccion.setText(seleccionado.getDireccionCliente());
				txtCedula.setText(seleccionado.getCedula());
				txtUsuarioAuto.setText(seleccionado.getMiCuenta().getNombreUsuario());
				cbxZona.setSelectedItem(seleccionado.getZonaVivienda());

				actualizarListaPlanes();

				if (estaSuspendido) {
					toggleCampos(false); 
					JOptionPane.showMessageDialog(this, "CLIENTE SUSPENDIDO: Verifique deudas o estado administrativo.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					toggleCampos(true);
				}
			}
		}
	}

	private void llenarComboPlanes() {
		cbxNuevosPlanes.removeAllItems();
		cbxNuevosPlanes.addItem("<< Planes >>");
		for (Servicio s : Altice.getInstance().getCatalogoServicio()) {
			if (s.isEstadoDelServicio()) {
				cbxNuevosPlanes.addItem(s.getIdServicio() + " - " + s.getNombreServicio());
			}
		}
	}

	private void actualizarListaPlanes() {
		listModel.clear();
		if (seleccionado != null && seleccionado.getMiContrato() != null) {
			for (Servicio s : seleccionado.getMiContrato().getMisServicios()) {
				String infoPlan = s.getIdServicio() + " - " + s.getNombreServicio();

				if (s instanceof PlanMovil) {
					infoPlan += " [Tel: " + ((PlanMovil) s).getNumeroTelefonico() + "]";
				} else if (s instanceof PlanHogar) {
					infoPlan += " [Tel: " + ((PlanHogar) s).getNumeroTelefonico() + "]";
				}

				listModel.addElement(infoPlan);
			}
		}
	}

	private void agregarNuevoPlan() {
		if (seleccionado != null) {
			float deuda = Altice.getInstance().calcularMontoDeudaReal(seleccionado);
			if (deuda > 0) {
				JOptionPane.showMessageDialog(this, "Error: El cliente tiene deuda. No puede contratar más.");
				return;
			}
			if (cbxNuevosPlanes.getSelectedIndex() > 0) {
				String idPlan = cbxNuevosPlanes.getSelectedItem().toString().split(" - ")[0];
				String idVend = (Altice.getInstance().getUsuarioLogueado() instanceof Personal) ? 
						((Personal) Altice.getInstance().getUsuarioLogueado()).getIdEmpleado() : "V-000";

				Altice.getInstance().contratarServicio(seleccionado.getIdCliente(), idPlan, idVend);
				actualizarListaPlanes();
				cargarTabla();
			}
		}
	}

	private void quitarPlan() {
		int index = listPlanes.getSelectedIndex();
		if (seleccionado != null && seleccionado.getMiContrato() != null && index >= 0) {
			String idServicio = listModel.getElementAt(index).split(" - ")[0];

			ArrayList<Servicio> servicios = seleccionado.getMiContrato().getMisServicios();
			for (int i = 0; i < servicios.size(); i++) {
				if (servicios.get(i).getIdServicio().equals(idServicio)) {
					servicios.remove(i);
					JOptionPane.showMessageDialog(this, "Plan removido exitosamente.");
					actualizarListaPlanes();
					cargarTabla();
					return;
				}
			}
		}
	}

	private void toggleCampos(boolean valor) {
		txtDireccion.setEnabled(valor); cbxZona.setEnabled(valor);
		txtEmail.setEnabled(valor); btnGuardar.setEnabled(valor);
		btnAgregarPlan.setEnabled(valor); btnEliminarPlan.setEnabled(valor);
		cbxNuevosPlanes.setEnabled(valor);
	}

	private void guardarCambios() {
		try {
			seleccionado.setDireccionCliente(txtDireccion.getText());
			seleccionado.setZonaVivienda(cbxZona.getSelectedItem().toString());
			seleccionado.setEmailCliente(txtEmail.getText());
			JOptionPane.showMessageDialog(this, "Información actualizada.");
			cargarTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar.");
		}
	}
}