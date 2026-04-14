package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class SuspenderCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JLabel lblNombreCompleto, lblCedula, lblID, lblEstado;
	private Cliente seleccionado = null;
	private JButton btnAccion; 


	public SuspenderCliente() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SuspenderCliente.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Suspender / Activar Cliente");
		setSize(850, 500);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204)); 
		panelHeader.setBounds(0, 0, 850, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("SUSPENDER / REACTIVAR CLIENTES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 500, 350);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Cédula", "Estado"};
		model = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setBackground(new Color(150, 150, 150));
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		scrollPane.setViewportView(table);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(250, 250, 250));
		TitledBorder borde = BorderFactory.createTitledBorder(
				new LineBorder(Color.GRAY, 1, true), " Gestión de Estado ", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 13), Color.BLACK);
		panelInfo.setBorder(borde);
		panelInfo.setBounds(540, 60, 270, 350);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		lblID = new JLabel("ID: -"); 
		lblID.setBounds(20, 40, 230, 20); 
		panelInfo.add(lblID);

		lblNombreCompleto = new JLabel("Cliente: -"); 
		lblNombreCompleto.setBounds(20, 80, 230, 20); 
		panelInfo.add(lblNombreCompleto);

		lblCedula = new JLabel("Cédula: -"); 
		lblCedula.setBounds(20, 120, 230, 20); 
		panelInfo.add(lblCedula);

		lblEstado = new JLabel("Estado: -"); 
		lblEstado.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		lblEstado.setBounds(20, 160, 230, 20); 
		panelInfo.add(lblEstado);

		btnAccion = new JButton("SELECCIONE CLIENTE");
		btnAccion.setEnabled(false);
		btnAccion.setForeground(Color.WHITE);
		btnAccion.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnAccion.setBounds(35, 250, 200, 40);
		btnAccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (seleccionado.isEstadoCliente()) { 
					ejecutarSuspension();
				} else { 
					ejecutarReactivacion(); 
				}
			}
		});
		panelInfo.add(btnAccion);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizarSeleccion();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		buttonPane.add(btnCerrar);

		cargarTablaClientes();
	}

	private void cargarTablaClientes() {
		model.setRowCount(0);
		for (Cliente c : Altice.getInstance().getListaClientes()) {
			String est = c.isEstadoCliente() ? "ACTIVO" : "SUSPENDIDO";
			model.addRow(new Object[]{c.getIdCliente(), c.getNombreCliente() + " " + c.getApellidoCliente(), c.getCedula(), est});
		}
	}

	private void actualizarSeleccion() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarCliente(id);
			if (seleccionado != null) {
				lblID.setText("ID: " + seleccionado.getIdCliente());
				lblNombreCompleto.setText("Cliente: " + seleccionado.getNombreCliente());
				lblCedula.setText("Cédula: " + seleccionado.getCedula());

				btnAccion.setEnabled(true);
				if (seleccionado.isEstadoCliente()) { 
					lblEstado.setText("Estado: ACTIVO");
					lblEstado.setForeground(new Color(0, 153, 51));
					btnAccion.setText("SUSPENDER CLIENTE");
					btnAccion.setBackground(new Color(204, 0, 0));
				} else {
					lblEstado.setText("Estado: SUSPENDIDO");
					lblEstado.setForeground(Color.RED);
					btnAccion.setText("REACTIVAR CLIENTE");
					btnAccion.setBackground(new Color(0, 153, 51));
				}
			}
		}
	}

	private void ejecutarSuspension() {
		int confirm = JOptionPane.showConfirmDialog(this, 
				"¿Desea suspender a " + seleccionado.getNombreCliente() + "?",
				"Confirmar", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			seleccionado.setEstadoCliente(true);
			JOptionPane.showMessageDialog(this, "Cliente suspendido.");
			cargarTablaClientes();
			limpiarLabels();
		}
	}

	private void ejecutarReactivacion() {
		String deuda = Altice.getInstance().comprobarSiHayDeuda(seleccionado.getIdCliente());

		if (deuda.equalsIgnoreCase("Si")) {
			JOptionPane.showMessageDialog(this, 
					"No se puede reactivar: El cliente tiene deudas pendientes.\nDebe saldar su cuenta primero.", 
					"Bloqueo Financiero", JOptionPane.ERROR_MESSAGE);
		} else {
			int confirm = JOptionPane.showConfirmDialog(this, 
					"¿Desea reactivar los servicios de " + seleccionado.getNombreCliente() + "?",
					"Confirmar", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				seleccionado.setEstadoCliente(false); 
				JOptionPane.showMessageDialog(this, "El cliente ha sido reactivado satisfactoriamente.");
				cargarTablaClientes();
				limpiarLabels();
			}
		}
	}

	private void limpiarLabels() {
		lblID.setText("ID: -"); lblNombreCompleto.setText("Cliente: -"); 
		lblCedula.setText("Cédula: -"); lblEstado.setText("Estado: -");
		btnAccion.setEnabled(false);
		btnAccion.setText("SELECCIONE CLIENTE");
		btnAccion.setBackground(Color.GRAY);
		seleccionado = null;
	}
}