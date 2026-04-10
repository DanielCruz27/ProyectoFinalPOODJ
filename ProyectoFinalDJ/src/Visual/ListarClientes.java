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
	// Agregado txtCedula a la lista de componentes
	private JTextField txtNombre, txtApellido, txtEmail, txtDireccion, txtUsuarioAuto, txtCedula;
	private JComboBox<String> cbxZona, cbxNuevosPlanes;
	private JList<String> listPlanes;
	private DefaultListModel<String> listModel;
	private JPanel panelEditar;
	private Cliente seleccionado = null;
	private JButton btnGuardar, btnAgregarPlan, btnEliminarPlan;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ListarClientes dialog = new ListarClientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarClientes() {
		setTitle("Altice - Gestión Integral de Clientes");
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 1200, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("LISTADO DE CLIENTES Y MODIFICACIÓN");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE CLIENTES ---
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setBounds(20, 60, 800, 550);
		contentPanel.add(scrollTable);

		String[] columnas = {"ID", "Nombre", "Zona", "Servicios", "Puntos", "Deuda", "Atrasos", "Estado"};
		modelClientes = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tableClientes = new JTable(modelClientes);
		tableClientes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		tableClientes.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		tableClientes.getTableHeader().setBackground(new Color(0, 102, 204));
		tableClientes.getTableHeader().setForeground(Color.WHITE);
		scrollTable.setViewportView(tableClientes);

		// --- PANEL DE EDICIÓN ---
		panelEditar = new JPanel();
		panelEditar.setBackground(Color.WHITE);
		panelEditar.setLayout(null);
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(new Color(0, 102, 204), 1, true), " Detalles del Cliente ",
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelEditar.setBorder(borde);
		panelEditar.setBounds(840, 60, 330, 550);
		contentPanel.add(panelEditar);

		// --- CAMPOS DE TEXTO ---
		JLabel l1 = new JLabel("Nombre:");
		l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l1.setBounds(20, 30, 80, 14); panelEditar.add(l1);
		txtNombre = new JTextField(); txtNombre.setEditable(false);
		txtNombre.setBounds(20, 48, 140, 25); panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Apellido:");
		l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l2.setBounds(175, 30, 80, 14); panelEditar.add(l2);
		txtApellido = new JTextField(); txtApellido.setEditable(false);
		txtApellido.setBounds(175, 48, 140, 25); panelEditar.add(txtApellido);

		// --- NUEVO: CÉDULA (Solo Lectura) ---
		JLabel lCed = new JLabel("Cédula:");
		lCed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		lCed.setBounds(20, 78, 100, 14); panelEditar.add(lCed);
		txtCedula = new JTextField(); txtCedula.setEditable(false);
		txtCedula.setBackground(new Color(245, 245, 245));
		txtCedula.setBounds(20, 95, 295, 25); panelEditar.add(txtCedula);

		JLabel lUsuario = new JLabel("Usuario de Acceso:");
		lUsuario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 11));
		lUsuario.setBounds(20, 125, 150, 14); panelEditar.add(lUsuario);
		txtUsuarioAuto = new JTextField(); txtUsuarioAuto.setEditable(false);
		txtUsuarioAuto.setBackground(new Color(245, 245, 245));
		txtUsuarioAuto.setBounds(20, 142, 295, 25); panelEditar.add(txtUsuarioAuto);

		JLabel l3 = new JLabel("Dirección:");
		l3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l3.setBounds(20, 175, 100, 14); panelEditar.add(l3);
		txtDireccion = new JTextField();
		txtDireccion.setBounds(20, 192, 295, 25); panelEditar.add(txtDireccion);

		JLabel l4 = new JLabel("Zona:");
		l4.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l4.setBounds(20, 222, 100, 14); panelEditar.add(l4);
		cbxZona = new JComboBox<String>(new String[]{"Metropolitana", "Norte", "Sur", "Este"});
		cbxZona.setBounds(20, 240, 140, 25); panelEditar.add(cbxZona);

		JLabel l5 = new JLabel("Email:");
		l5.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l5.setBounds(175, 222, 100, 14); panelEditar.add(l5);
		txtEmail = new JTextField();
		txtEmail.setBounds(175, 240, 140, 25); panelEditar.add(txtEmail);

		// --- SECCIÓN PLANES ---
		JSeparator sep = new JSeparator();
		sep.setBounds(20, 275, 300, 2); panelEditar.add(sep);

		JLabel l6 = new JLabel("Planes Activos:");
		l6.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		l6.setBounds(20, 282, 300, 14); panelEditar.add(l6);

		listModel = new DefaultListModel<String>();
		listPlanes = new JList<String>(listModel);
		JScrollPane scrollP = new JScrollPane(listPlanes);
		scrollP.setBounds(20, 300, 295, 80); panelEditar.add(scrollP);

		btnEliminarPlan = new JButton("Quitar Plan Seleccionado");
		btnEliminarPlan.setFont(new Font("Arial Narrow", Font.BOLD, 11));
		btnEliminarPlan.setBounds(20, 385, 295, 23);
		btnEliminarPlan.addActionListener(e -> quitarPlan());
		panelEditar.add(btnEliminarPlan);

		JLabel l7 = new JLabel("Vender Nuevo Plan:");
		l7.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l7.setBounds(20, 415, 200, 14); panelEditar.add(l7);

		cbxNuevosPlanes = new JComboBox<String>();
		llenarComboPlanes();
		cbxNuevosPlanes.setBounds(20, 432, 200, 25); panelEditar.add(cbxNuevosPlanes);

		btnAgregarPlan = new JButton("Añadir");
		btnAgregarPlan.setBounds(230, 432, 85, 25);
		btnAgregarPlan.addActionListener(e -> agregarNuevoPlan());
		panelEditar.add(btnAgregarPlan);

		btnGuardar = new JButton("GUARDAR CAMBIOS");
		btnGuardar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(65, 490, 200, 35);
		btnGuardar.addActionListener(e -> guardarCambios());
		panelEditar.add(btnGuardar);

		// BOTONES PIE
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dispose());
		buttonPane.add(btnCerrar);

		toggleCampos(false);
		tableClientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { cargarDatosSeleccionado(); }
		});
		cargarTabla();
	}

	// Dentro de cargarTabla() en ListarClientes.java
	private void cargarTabla() {
	    modelClientes.setRowCount(0);
	    for (Cliente c : Altice.getInstance().getListaClientes()) {
	        int totalServ = Altice.getInstance().contarCantServiciosDeCliente(c.getIdCliente());
	        String deuda = Altice.getInstance().comprobarSiHayDeuda(c.getIdCliente());
	        int atrasos = (int) c.getCantidadAtrasos(); 
	        
	        // --- CORRECCIÓN AQUÍ ---
	        // Si tiene más de 2 atrasos O si el booleano estadoCliente es true (suspensión manual)
	        String estado = (atrasos > 2 || c.isEstadoCliente()) ? "Suspendido" : "Activo";

	        modelClientes.addRow(new Object[]{
	            c.getIdCliente(), 
	            c.getNombreCliente() + " " + c.getApellidoCliente(), 
	            c.getZonaVivienda(), 
	            totalServ,
	            c.getPuntosAcumulados(),
	            deuda,
	            atrasos,
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
	            toggleCampos(true);
	            
	            txtNombre.setText(seleccionado.getNombreCliente());
	            txtApellido.setText(seleccionado.getApellidoCliente());
	            
	            // --- CADA COSA EN SU SITIO AHORA ---
	            txtEmail.setText(seleccionado.getEmailCliente());      // Email con Email
	            txtDireccion.setText(seleccionado.getDireccionCliente()); // Direccion con Direccion
	            txtCedula.setText(seleccionado.getCedula());           // Cedula con Cedula
	            
	            txtUsuarioAuto.setText(seleccionado.getMiCuenta().getNombreUsuario());
	            cbxZona.setSelectedItem(seleccionado.getZonaVivienda());

	            actualizarListaPlanes();
	        }
	    }
	}

	// ... (Los métodos llenarComboPlanes, actualizarListaPlanes, agregarNuevoPlan, quitarPlan y toggleCampos se mantienen igual)
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
		for (Contrato con : seleccionado.getMisContratos()) {
			for (Servicio s : con.getMisServicios()) {
				String tel = "";
				if (s instanceof PlanMovil) tel = " [Tel: " + ((PlanMovil)s).getNumeroTelefonico() + "]";
				if (s instanceof PlanHogar) tel = " [Tel: " + ((PlanHogar)s).getNumeroTelefonico() + "]";
				listModel.addElement(s.getIdServicio() + " - " + s.getNombreServicio() + tel);
			}
		}
	}

	private void agregarNuevoPlan() {
		if (seleccionado != null && cbxNuevosPlanes.getSelectedIndex() > 0) {
			String idPlan = cbxNuevosPlanes.getSelectedItem().toString().split(" - ")[0];
			String idVend = (Altice.getInstance().getUsuarioLogueado() != null) ? Altice.getInstance().getUsuarioLogueado().getIdEmpleado() : "V-000";
			Altice.getInstance().contratarServicio(seleccionado.getIdCliente(), idPlan, idVend);
			JOptionPane.showMessageDialog(this, "Nuevo plan contratado.");
			actualizarListaPlanes();
			cargarTabla();
		}
	}

	private void quitarPlan() {
		int index = listPlanes.getSelectedIndex();
		if (seleccionado != null && index >= 0) {
			String idServicio = listModel.getElementAt(index).split(" - ")[0];
			for (Contrato con : seleccionado.getMisContratos()) {
				for (int i = 0; i < con.getMisServicios().size(); i++) {
					if (con.getMisServicios().get(i).getIdServicio().equals(idServicio)) {
						con.getMisServicios().remove(i);
						JOptionPane.showMessageDialog(this, "Plan removido.");
						actualizarListaPlanes();
						cargarTabla();
						return;
					}
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