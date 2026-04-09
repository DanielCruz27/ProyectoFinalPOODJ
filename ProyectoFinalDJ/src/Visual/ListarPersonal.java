package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class ListarPersonal extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtNombre, txtApellido, txtSalario;
	private JComboBox<String> cmbEspecial; 
	private JComboBox<String> cmbZona;     
	private JCheckBox chkLicencia;         
	private JLabel lblEspecial;
	private Personal seleccionado = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarPersonal dialog = new ListarPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarPersonal() {
		setTitle("Altice - Gestión de Personal");
		setSize(900, 600);
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
		panelHeader.setBounds(0, 0, 900, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("LISTADO Y MODIFICACIÓN DE PERSONAL");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE PERSONAL ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 550, 430);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Apellido", "Rol", "Salario", "Estado"}; 

		model = new DefaultTableModel(null, columnas) {
		    private static final long serialVersionUID = 1L;
		    @Override
		    public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setBackground(new Color(0, 102, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		scrollPane.setViewportView(table);

		// --- PANEL DE EDICIÓN (Borde igual al de registrar) ---
		JPanel panelEditar = new JPanel();
		panelEditar.setBackground(Color.WHITE);
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(new Color(0, 102, 204), 1, true), " Detalles del Empleado ");
		borde.setTitleColor(new Color(0, 102, 204));
		borde.setTitleFont(new Font("Arial", Font.BOLD, 12));
		panelEditar.setBorder(borde);
		panelEditar.setBounds(590, 60, 280, 430);
		contentPanel.add(panelEditar);
		panelEditar.setLayout(null);

		// Campos de edición
		JLabel l1 = new JLabel("Nombre:"); l1.setBounds(20, 30, 80, 14); panelEditar.add(l1);
		txtNombre = new JTextField(); txtNombre.setEditable(false); txtNombre.setBounds(20, 50, 240, 25); panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Apellido:"); l2.setBounds(20, 85, 80, 14); panelEditar.add(l2);
		txtApellido = new JTextField(); txtApellido.setEditable(false); txtApellido.setBounds(20, 105, 240, 25); panelEditar.add(txtApellido);

		JLabel l3 = new JLabel("Salario Base:"); l3.setBounds(20, 145, 120, 14); panelEditar.add(l3);
		txtSalario = new JTextField(); txtSalario.setBounds(20, 165, 240, 25); panelEditar.add(txtSalario);

		lblEspecial = new JLabel("Dato Especial:"); lblEspecial.setBounds(20, 205, 200, 14); panelEditar.add(lblEspecial);
		cmbEspecial = new JComboBox<>(); cmbEspecial.setBounds(20, 225, 240, 25); panelEditar.add(cmbEspecial);

		JLabel lblZ = new JLabel("Zona Asignada:"); lblZ.setBounds(20, 265, 150, 14); panelEditar.add(lblZ);
		cmbZona = new JComboBox<>(new String[]{"Metropolitana", "Norte", "Sur", "Este"});
		cmbZona.setBounds(20, 285, 240, 25); panelEditar.add(cmbZona);

		chkLicencia = new JCheckBox("Licencia al día");
		chkLicencia.setBackground(Color.WHITE);
		chkLicencia.setBounds(20, 325, 200, 25); panelEditar.add(chkLicencia);

		// --- BOTONES ---
		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardar.setBounds(50, 380, 180, 30);
		panelEditar.add(btnGuardar);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		buttonPane.add(btnCerrar);

		// --- LOGICA DE EVENTOS ---
		
		// Evento al seleccionar fila
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarDatosSeleccionado();
			}
		});

		// Evento Guardar
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarCambios();
			}
		});

		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		cargarTabla();
	}

	private void cargarTabla() {
	    model.setRowCount(0);
	    for (Personal p : Altice.getInstance().getListaEmpleados()) {
	        String rol = "";
	        if (p instanceof Administrativo) rol = "Admin";
	        else if (p instanceof Tecnico) rol = "Técnico";
	        else if (p instanceof Comercial) rol = "Comercial";
	        
	        String estadoTexto = (p.getEstado() == 1) ? "Activo" : "Inactivo";
	        
	        Object[] fila = {
	            p.getIdEmpleado(), 
	            p.getNombre(), 
	            p.getApellido(), 
	            rol, 
	            p.getSalarioBase(),
	            estadoTexto
	        };
	        model.addRow(fila);
	    }
	}

	private void cargarDatosSeleccionado() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarEmpleadoPorId(id);
			
			if (seleccionado != null) {
				txtNombre.setText(seleccionado.getNombre());
				txtApellido.setText(seleccionado.getApellido());
				txtSalario.setText(String.valueOf(seleccionado.getSalarioBase()));
				
				// Resetear visibilidad
				cmbZona.setEnabled(false);
				chkLicencia.setEnabled(false);
				cmbEspecial.removeAllItems();

				if (seleccionado instanceof Administrativo) {
					lblEspecial.setText("Departamento:");
					cmbEspecial.setModel(new DefaultComboBoxModel<>(new String[]{"Base de Datos", "Redes", "Finanzas", "Seguridad"}));
					cmbEspecial.setSelectedItem(((Administrativo) seleccionado).getDepartamento());
				} 
				else if (seleccionado instanceof Tecnico) {
					lblEspecial.setText("Tipo de Técnico:");
					cmbEspecial.setModel(new DefaultComboBoxModel<>(new String[]{"Instalacion", "Planta externa", "Infraestructura", "Soporte tecnico"}));
					cmbEspecial.setSelectedItem(((Tecnico) seleccionado).getTipoTecnico());
					cmbZona.setEnabled(true);
					cmbZona.setSelectedItem(((Tecnico) seleccionado).getZonAsignada());
					chkLicencia.setEnabled(true);
					chkLicencia.setSelected(((Tecnico) seleccionado).isLicencia());
				} 
				else {
					lblEspecial.setText("N/A");
					cmbEspecial.setEnabled(false);
				}
			}
		}
	}

	private void guardarCambios() {
		try {
			if (seleccionado == null) throw new Exception("Seleccione un empleado de la tabla.");
			
			float nuevoSalario = Float.parseFloat(txtSalario.getText());
			seleccionado.setSalarioBase(nuevoSalario);

			if (seleccionado instanceof Administrativo) {
				((Administrativo) seleccionado).setDepartamento(cmbEspecial.getSelectedItem().toString());
			} 
			else if (seleccionado instanceof Tecnico) {
				((Tecnico) seleccionado).setTipoTecnico(cmbEspecial.getSelectedItem().toString());
				((Tecnico) seleccionado).setZonAsignada(cmbZona.getSelectedItem().toString());
				((Tecnico) seleccionado).setLicencia(chkLicencia.isSelected());
			}

			JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
			cargarTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
