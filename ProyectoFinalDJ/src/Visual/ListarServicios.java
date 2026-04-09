package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class ListarServicios extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtNombre, txtPrecio;
	private JSpinner spnEspecial1, spnEspecial2; // Minutos/Velocidad o Redes/MinutosFijo
	private JLabel lblEspecial1, lblEspecial2, lblStreaming;
	private JTextArea txtStreaming; // Para ver la lista de streamings o redes
	private JPanel panelEditar;
	private Servicio seleccionado = null;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ListarServicios dialog = new ListarServicios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarServicios() {
		setTitle("Altice - Gestión de Catálogo de Servicios");
		setSize(950, 600);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA AZUL ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 950, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("CATÁLOGO DE PLANES Y SERVICIOS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE SERVICIOS ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 580, 430);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Tipo", "Precio", "Estado"}; 
		model = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setBackground(new Color(0, 102, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		scrollPane.setViewportView(table);

		// --- PANEL DE EDICIÓN ---
		panelEditar = new JPanel();
		panelEditar.setBackground(Color.WHITE);
		panelEditar.setLayout(null);
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(new Color(0, 102, 204), 1, true), " Detalles del Plan ",
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelEditar.setBorder(borde);
		panelEditar.setBounds(620, 60, 290, 430);
		contentPanel.add(panelEditar);

		JLabel l1 = new JLabel("Nombre del Plan:");
		l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		l1.setBounds(20, 30, 150, 14); panelEditar.add(l1);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtNombre.setBounds(20, 50, 250, 25); panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Precio Base:");
		l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		l2.setBounds(20, 85, 100, 14); panelEditar.add(l2);
		
		txtPrecio = new JTextField();
		txtPrecio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtPrecio.setBounds(20, 105, 120, 25); panelEditar.add(txtPrecio);

		lblEspecial1 = new JLabel("Dato 1:");
		lblEspecial1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblEspecial1.setBounds(20, 145, 200, 14); panelEditar.add(lblEspecial1);
		
		spnEspecial1 = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		spnEspecial1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		spnEspecial1.setBounds(20, 165, 100, 25); panelEditar.add(spnEspecial1);

		lblEspecial2 = new JLabel("Dato 2:");
		lblEspecial2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblEspecial2.setBounds(20, 205, 200, 14); panelEditar.add(lblEspecial2);
		
		spnEspecial2 = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		spnEspecial2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		spnEspecial2.setBounds(20, 225, 100, 25); panelEditar.add(spnEspecial2);

		lblStreaming = new JLabel("Inclusiones (Redes/Video):");
		lblStreaming.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblStreaming.setBounds(20, 265, 250, 14); panelEditar.add(lblStreaming);
		
		txtStreaming = new JTextArea();
		txtStreaming.setEditable(false);
		txtStreaming.setLineWrap(true);
		txtStreaming.setWrapStyleWord(true);
		txtStreaming.setBackground(new Color(245, 245, 245));
		txtStreaming.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		txtStreaming.setBounds(20, 285, 250, 60);
		panelEditar.add(txtStreaming);

		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(55, 375, 180, 35);
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCambios();
			}
		});
		panelEditar.add(btnGuardar);

		// --- BOTONES INFERIORES ---
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

		// --- EVENTO TABLA ---
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarDatosSeleccionado();
			}
		});

		cargarTabla();
	}

	private void cargarTabla() {
		model.setRowCount(0);
		for (Servicio s : Altice.getInstance().getCatalogoServicio()) {
			String tipo = (s instanceof PlanMovil) ? "Móvil" : "Hogar";
			String estado = s.isEstadoDelServicio() ? "Disponible" : "Descatalogado";
			model.addRow(new Object[]{s.getIdServicio(), s.getNombreServicio(), tipo, s.getPrecioBase(), estado});
		}
	}

	private void cargarDatosSeleccionado() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			// Debes tener un método buscarServicioById en Altice
			seleccionado = Altice.getInstance().buscarServicioById(id);

			if (seleccionado != null) {
				txtNombre.setText(seleccionado.getNombreServicio());
				txtPrecio.setText(String.valueOf(seleccionado.getPrecioBase()));

				if (seleccionado instanceof PlanMovil) {
					PlanMovil pm = (PlanMovil) seleccionado;
					lblEspecial1.setText("Minutos Incluidos:");
					spnEspecial1.setValue(pm.getMinutosIncluidos());
					
					lblEspecial2.setVisible(false);
					spnEspecial2.setVisible(false);
					
					lblStreaming.setText("Redes Sociales Incluidas:");
					txtStreaming.setText(pm.getRedesLibresIncluidas());
				} 
				else if (seleccionado instanceof PlanHogar) {
					PlanHogar ph = (PlanHogar) seleccionado;
					lblEspecial1.setText("Velocidad (Mbps):");
					spnEspecial1.setValue(ph.getVelocidadInternet());
					
					lblEspecial2.setText("Minutos Fijo:");
					lblEspecial2.setVisible(true);
					spnEspecial2.setVisible(true);
					spnEspecial2.setValue(ph.getMinutosTelefonoHogar());
					
					lblStreaming.setText("Streaming Incluido:");
					txtStreaming.setText(ph.getStreamingIncluido());
				}
				
				panelEditar.revalidate();
				panelEditar.repaint();
			}
		}
	}

	private void guardarCambios() {
		try {
			if (seleccionado == null) throw new Exception("Seleccione un plan de la tabla.");
			
			seleccionado.setNombreServicio(txtNombre.getText());
			seleccionado.setPrecioBase(Float.parseFloat(txtPrecio.getText()));

			if (seleccionado instanceof PlanMovil) {
				((PlanMovil) seleccionado).setMinutosLibres(Integer.parseInt(spnEspecial1.getValue().toString()));
			} 
			else if (seleccionado instanceof PlanHogar) {
				((PlanHogar) seleccionado).setVelocidadInternet(Integer.parseInt(spnEspecial1.getValue().toString()));
				((PlanHogar) seleccionado).setMinutosTelefonoHogar(Integer.parseInt(spnEspecial2.getValue().toString()));
			}

			JOptionPane.showMessageDialog(this, "Plan actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			cargarTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}