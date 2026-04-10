package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class DesactivarServicios extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JLabel lblID, lblNombre, lblTipo, lblEstado;
	private Servicio seleccionado = null;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			DesactivarServicios dialog = new DesactivarServicios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DesactivarServicios() {
		setTitle("Altice - Desactivar Planes del Catálogo");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA ROJA (ALERTA) ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(204, 0, 0)); 
		panelHeader.setBounds(0, 0, 800, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("DESACTIVAR / REVICTIVAR PLANES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 450, 350);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Estado"};
		model = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		scrollPane.setViewportView(table);

		// --- PANEL INFO ---
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(250, 250, 250));
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(Color.GRAY, 1, true), " Estado del Plan ", 
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 13), Color.BLACK);
		panelInfo.setBorder(borde);
		panelInfo.setBounds(490, 60, 280, 350);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		lblID = new JLabel("ID: -"); 
		lblID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblID.setBounds(20, 40, 240, 20); panelInfo.add(lblID);
		
		lblNombre = new JLabel("Plan: -"); 
		lblNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNombre.setBounds(20, 80, 240, 20); panelInfo.add(lblNombre);
		
		lblTipo = new JLabel("Tipo: -"); 
		lblTipo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblTipo.setBounds(20, 120, 240, 20); panelInfo.add(lblTipo);

		lblEstado = new JLabel("Estado actual: -"); 
		lblEstado.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		lblEstado.setBounds(20, 160, 240, 20); panelInfo.add(lblEstado);

		JButton btnAccion = new JButton("CAMBIAR ESTADO");
		btnAccion.setBackground(new Color(0, 102, 204));
		btnAccion.setForeground(Color.WHITE);
		btnAccion.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnAccion.setBounds(40, 250, 200, 40);
		btnAccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutarCambio();
			}
		});
		panelInfo.add(btnAccion);

		// --- BOTÓN CERRAR ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		buttonPane.add(btnCerrar);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizarSeleccion();
			}
		});

		cargarTabla();
	}

	private void cargarTabla() {
		model.setRowCount(0);
		for (Servicio s : Altice.getInstance().getCatalogoServicio()) {
			String estado = s.isEstadoDelServicio() ? "Activo" : "Inactivo";
			model.addRow(new Object[]{s.getIdServicio(), s.getNombreServicio(), estado});
		}
	}

	private void actualizarSeleccion() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarServicioById(id);
			if (seleccionado != null) {
				lblID.setText("ID: " + seleccionado.getIdServicio());
				lblNombre.setText("Plan: " + seleccionado.getNombreServicio());
				lblTipo.setText("Tipo: " + (seleccionado instanceof PlanMovil ? "Móvil" : "Hogar"));
				lblEstado.setText("Estado: " + (seleccionado.isEstadoDelServicio() ? "ACTIVO" : "DESACTIVADO"));
				lblEstado.setForeground(seleccionado.isEstadoDelServicio() ? new Color(0, 153, 51) : Color.RED);
			}
		}
	}

	private void ejecutarCambio() {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un plan.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String msj = seleccionado.isEstadoDelServicio() ? "¿Desactivar este plan?" : "¿Reactivar este plan?";
		int confirm = JOptionPane.showConfirmDialog(this, msj, "Confirmar", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			Altice.getInstance().cambiarEstadoServicio(seleccionado.getIdServicio());
			JOptionPane.showMessageDialog(this, "Operación realizada con éxito.");
			cargarTabla();
			actualizarSeleccion();
		}
	}
}
