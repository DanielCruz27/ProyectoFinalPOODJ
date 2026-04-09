package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class DarBajaPersonal extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JLabel lblNombreCompleto, lblCedula, lblID, lblRol;
	private Personal seleccionado = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DarBajaPersonal dialog = new DarBajaPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DarBajaPersonal() {
		setTitle("Altice - Dar de Baja Personal");
		setSize(850, 500);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA ROJA ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(204, 0, 0)); 
		panelHeader.setBounds(0, 0, 850, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("DAR DE BAJA PERSONAL");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE PERSONAL ACTIVO ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 500, 350);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Apellido", "Rol"};
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

		// --- PANEL DE CONFIRMACIÓN ---
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(250, 250, 250));
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(Color.GRAY, 1, true), " Confirmación de Baja ", 
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 13), Color.BLACK);
		panelInfo.setBorder(borde);
		panelInfo.setBounds(540, 60, 270, 350);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		lblID = new JLabel("ID: -"); 
		lblID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblID.setBounds(20, 40, 230, 20); 
		panelInfo.add(lblID);
		
		lblNombreCompleto = new JLabel("Nombre: -"); 
		lblNombreCompleto.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNombreCompleto.setBounds(20, 80, 230, 20); 
		panelInfo.add(lblNombreCompleto);
		
		lblCedula = new JLabel("Cédula: -"); 
		lblCedula.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblCedula.setBounds(20, 120, 230, 20); 
		panelInfo.add(lblCedula);
		
		lblRol = new JLabel("Rol: -"); 
		lblRol.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblRol.setBounds(20, 160, 230, 20); 
		panelInfo.add(lblRol);

		JButton btnConfirmar = new JButton("CONFIRMAR BAJA");
		btnConfirmar.setBackground(new Color(220, 53, 69));
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnConfirmar.setBounds(35, 250, 200, 40);
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutarBaja();
			}
		});
		panelInfo.add(btnConfirmar);

		// --- EVENTOS ---
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizarSeleccion();
			}
		});

		// Botón cerrar en la parte inferior
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				dispose(); 
			}
		});
		buttonPane.add(btnCerrar);

		cargarTablaActivos();
	}

	private void cargarTablaActivos() {
		model.setRowCount(0);
		for (Personal p : Altice.getInstance().getListaEmpleados()) {
			if (p.getEstado() == 1) { 
				String rol = p.getClass().getSimpleName();
				model.addRow(new Object[]{p.getIdEmpleado(), p.getNombre(), p.getApellido(), rol});
			}
		}
	}

	private void actualizarSeleccion() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarEmpleadoPorId(id);
			if (seleccionado != null) {
				lblID.setText("ID: " + seleccionado.getIdEmpleado());
				lblNombreCompleto.setText("Nombre: " + seleccionado.getNombre() + " " + seleccionado.getApellido());
				lblCedula.setText("Cédula: " + seleccionado.getCedula());
				lblRol.setText("Rol: " + seleccionado.getClass().getSimpleName());
			}
		}
	}

	private void ejecutarBaja() {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, 
			"¿Está seguro que desea dar de baja a " + seleccionado.getNombre() + "?\nEsta acción revocará su acceso al sistema.",
			"Confirmar Desactivación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirm == JOptionPane.YES_OPTION) {
			seleccionado.setEstado(0); 
			JOptionPane.showMessageDialog(this, "El empleado ha sido desactivado con éxito.");
			seleccionado = null;
			lblID.setText("ID: -"); lblNombreCompleto.setText("Nombre: -"); lblCedula.setText("Cédula: -"); lblRol.setText("Rol: -");
			cargarTablaActivos();
		}
	}
}