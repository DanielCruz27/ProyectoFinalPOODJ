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
	private JLabel lblNombreCompleto, lblCedula, lblID, lblRol, lblEstado;
	private Personal seleccionado = null;
	private JButton btnAccion;

	

	public DarBajaPersonal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DarBajaPersonal.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Dar de Baja / Reactivar");
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

		JLabel lblTitulo = new JLabel("DAR DE BAJA / REACTIVAR PERSONAL");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 500, 350);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Rol", "Estado"};
		model = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		scrollPane.setViewportView(table);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(250, 250, 250));
		TitledBorder borde = BorderFactory.createTitledBorder(
				new LineBorder(Color.GRAY, 1, true), " Control de Empleado ", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 13), Color.BLACK);
		panelInfo.setBorder(borde);
		panelInfo.setBounds(540, 60, 270, 350);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		lblID = new JLabel("ID: -"); 
		lblID.setBounds(20, 40, 230, 20); 
		panelInfo.add(lblID);

		lblNombreCompleto = new JLabel("Nombre: -"); 
		lblNombreCompleto.setBounds(20, 70, 230, 20); 
		panelInfo.add(lblNombreCompleto);

		lblCedula = new JLabel("Cédula: -"); 
		lblCedula.setBounds(20, 100, 230, 20); 
		panelInfo.add(lblCedula);

		lblRol = new JLabel("Rol: -"); 
		lblRol.setBounds(20, 130, 230, 20);
		panelInfo.add(lblRol);

		lblEstado = new JLabel("Estado: -");
		lblEstado.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		lblEstado.setBounds(20, 160, 230, 20); 
		panelInfo.add(lblEstado);

		btnAccion = new JButton("SELECCIONE");
		btnAccion.setEnabled(false);
		btnAccion.setForeground(Color.WHITE);
		btnAccion.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnAccion.setBounds(35, 250, 215, 40);
		btnAccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cambiarEstado();
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

		cargarTablaPersonal();
	}

	private void cargarTablaPersonal() {
		model.setRowCount(0);
		for (Personal p : Altice.getInstance().getListaEmpleados()) {
			String estado = (p.getEstado() == 1) ? "ACTIVO" : "DE BAJA";
			model.addRow(new Object[]{p.getIdEmpleado(), p.getNombre() + " " + p.getApellido(), p.getClass().getSimpleName(), estado});
		}
	}

	private void actualizarSeleccion() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarEmpleadoPorId(id);
			if (seleccionado != null) {
				lblID.setText("ID: " + seleccionado.getIdEmpleado());
				lblNombreCompleto.setText("Nombre: " + seleccionado.getNombre());
				lblCedula.setText("Cédula: " + seleccionado.getCedula());
				lblRol.setText("Rol: " + seleccionado.getClass().getSimpleName());

				btnAccion.setEnabled(true);
				if (seleccionado.getEstado() == 1) {
					lblEstado.setText("Estado: ACTIVO");
					lblEstado.setForeground(new Color(0, 153, 51));
					btnAccion.setText("DAR DE BAJA");
					btnAccion.setBackground(new Color(220, 53, 69));
				} else {
					lblEstado.setText("Estado: DE BAJA");
					lblEstado.setForeground(Color.RED);
					btnAccion.setText("REACTIVAR Y RESETEAR");
					btnAccion.setBackground(new Color(0, 153, 51));
				}
			}
		}
	}

	private void cambiarEstado() {
		if (seleccionado.getEstado() == 1) {
			int confirm = JOptionPane.showConfirmDialog(this, "¿Dar de baja a " + seleccionado.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				seleccionado.setEstado(0);
				JOptionPane.showMessageDialog(this, "Empleado desactivado.");
			}
		} else {
			int confirm = JOptionPane.showConfirmDialog(this, 
					"¿Reactivar a " + seleccionado.getNombre() + "?\nSe resetearán sus comisiones y métricas a cero.", 
					"Confirmar Reactivación", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				resetearAtributos(seleccionado);
				seleccionado.setEstado(1);
				JOptionPane.showMessageDialog(this, "Empleado reactivado con métricas en cero.");
			}
		}
		cargarTablaPersonal();
		limpiar();
	}

	private void resetearAtributos(Personal p) {
		if (p instanceof Comercial) {
			Comercial c = (Comercial) p;
			c.setVentasRealizadas(0);
			c.setComisiones(0);
		} else if (p instanceof Tecnico) {
			Tecnico t = (Tecnico) p;
			t.setHorasExtrasTrabajadas(0);
			t.setCantidadInstalaciones(0);
		}
	}

	private void limpiar() {
		lblID.setText("ID: -"); lblNombreCompleto.setText("Nombre: -"); 
		lblCedula.setText("Cédula: -"); lblRol.setText("Rol: -"); lblEstado.setText("Estado: -");
		btnAccion.setEnabled(false);
		seleccionado = null;
	}
}