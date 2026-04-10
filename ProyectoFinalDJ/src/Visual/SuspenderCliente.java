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

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SuspenderCliente dialog = new SuspenderCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SuspenderCliente() {
		setTitle("Altice - Suspensión de Clientes");
		setSize(850, 500);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA ROJA (Estilo Dar de Baja) ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(204, 0, 0)); 
		panelHeader.setBounds(0, 0, 850, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("SUSPENSIÓN MANUAL DE SERVICIOS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE CLIENTES ACTIVOS ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 500, 350);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Cédula", "Zona"};
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
			new LineBorder(Color.GRAY, 1, true), " Detalles del Contrato ", 
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 13), Color.BLACK);
		panelInfo.setBorder(borde);
		panelInfo.setBounds(540, 60, 270, 350);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		lblID = new JLabel("ID: -"); 
		lblID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblID.setBounds(20, 40, 230, 20); 
		panelInfo.add(lblID);
		
		lblNombreCompleto = new JLabel("Cliente: -"); 
		lblNombreCompleto.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNombreCompleto.setBounds(20, 80, 230, 20); 
		panelInfo.add(lblNombreCompleto);
		
		lblCedula = new JLabel("Cédula: -"); 
		lblCedula.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblCedula.setBounds(20, 120, 230, 20); 
		panelInfo.add(lblCedula);
		
		lblEstado = new JLabel("Estado: -"); 
		lblEstado.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		lblEstado.setBounds(20, 160, 230, 20); 
		panelInfo.add(lblEstado);

		JButton btnSuspender = new JButton("SUSPENDER CLIENTE");
		btnSuspender.setBackground(new Color(220, 53, 69));
		btnSuspender.setForeground(Color.WHITE);
		btnSuspender.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnSuspender.setBounds(35, 250, 200, 40);
		btnSuspender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutarSuspension();
			}
		});
		panelInfo.add(btnSuspender);

		// --- EVENTOS ---
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
		btnCerrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		buttonPane.add(btnCerrar);

		cargarTablaActivos();
	}

	private void cargarTablaActivos() {
		model.setRowCount(0);
		for (Cliente c : Altice.getInstance().getListaClientes()) {
			// Solo mostramos los que NO están suspendidos (estadoCliente == false)
			if (!c.isEstadoCliente()) { 
				model.addRow(new Object[]{c.getIdCliente(), c.getNombreCliente() + " " + c.getApellidoCliente(), c.getCedula(), c.getZonaVivienda()});
			}
		}
	}

	private void actualizarSeleccion() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarCliente(id);
			if (seleccionado != null) {
				lblID.setText("ID: " + seleccionado.getIdCliente());
				lblNombreCompleto.setText("Cliente: " + seleccionado.getNombreCliente() + " " + seleccionado.getApellidoCliente());
				lblCedula.setText("Cédula: " + seleccionado.getCedula());
				lblEstado.setText("Estado: ACTIVO");
				lblEstado.setForeground(new Color(0, 153, 51));
			}
		}
	}

	private void ejecutarSuspension() {
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, 
			"¿Está seguro que desea suspender a " + seleccionado.getNombreCliente() + "?\nEsta acción inhabilitará sus servicios contratados.",
			"Confirmar Suspensión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirm == JOptionPane.YES_OPTION) {
			seleccionado.setEstadoCliente(true); // true = suspendido
			JOptionPane.showMessageDialog(this, "El cliente ha sido suspendido con éxito.");
			seleccionado = null;
			lblID.setText("ID: -"); lblNombreCompleto.setText("Cliente: -"); lblCedula.setText("Cédula: -"); lblEstado.setText("Estado: -");
			cargarTablaActivos();
		}
	}
}