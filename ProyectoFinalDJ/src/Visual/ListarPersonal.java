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
	private JTextField txtNombre, txtApellido, txtSalario, txtCedula, txtUsuario; // <--- Agregado txtUsuario
	private JComboBox<String> cmbEspecial; 
	private JComboBox<String> cmbZona;     
	private JCheckBox chkLicencia;         
	private JLabel lblEspecial, lblZ; 
	private JPanel panelEditar; 
	private Personal seleccionado = null;

	private JLabel lblDatoExtra1, lblDatoExtra2, lblDatoExtra3;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ListarPersonal dialog = new ListarPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarPersonal() {
		setTitle("Altice - Gestión de Personal");
		setSize(900, 720); // Aumentamos un poco más el alto para que quepa todo nítido
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
		panelHeader.setBounds(0, 0, 900, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("LISTADO Y MODIFICACIÓN DE PERSONAL");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 550, 560);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Apellido", "Rol", "Salario", "Estado"}; 
		model = new DefaultTableModel(null, columnas) {
		    private static final long serialVersionUID = 1L;
		    @Override
		    public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.getTableHeader().setBackground(new Color(0, 102, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		scrollPane.setViewportView(table);

		// --- PANEL DE EDICIÓN ---
		panelEditar = new JPanel(); 
		panelEditar.setBackground(Color.WHITE);
		panelEditar.setLayout(null);
		TitledBorder borde = BorderFactory.createTitledBorder(
			new LineBorder(new Color(0, 102, 204), 1, true), " Detalles del Empleado ",
			TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelEditar.setBorder(borde);
		panelEditar.setBounds(590, 60, 280, 560);
		contentPanel.add(panelEditar);

		// Campos básicos
		JLabel l1 = new JLabel("Nombre:"); l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l1.setBounds(20, 25, 80, 14); panelEditar.add(l1);
		txtNombre = new JTextField(); txtNombre.setEditable(false); txtNombre.setBounds(20, 42, 240, 25); panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Apellido:"); l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l2.setBounds(20, 72, 80, 14); panelEditar.add(l2);
		txtApellido = new JTextField(); txtApellido.setEditable(false); txtApellido.setBounds(20, 89, 240, 25); panelEditar.add(txtApellido);

		JLabel lCed = new JLabel("Cédula:"); lCed.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lCed.setBounds(20, 119, 150, 14); panelEditar.add(lCed);
		txtCedula = new JTextField(); txtCedula.setEditable(false); txtCedula.setBackground(new Color(245, 245, 245));
		txtCedula.setBounds(20, 136, 240, 25); panelEditar.add(txtCedula);

		// --- CAMPO USUARIO (Solo lectura) ---
		JLabel lUser = new JLabel("Usuario del Sistema:"); lUser.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lUser.setBounds(20, 166, 150, 14); panelEditar.add(lUser);
		txtUsuario = new JTextField(); 
		txtUsuario.setEditable(false); 
		txtUsuario.setBackground(new Color(245, 245, 245)); 
		txtUsuario.setBounds(20, 183, 240, 25); panelEditar.add(txtUsuario);

		JLabel l3 = new JLabel("Salario Base:"); l3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		l3.setBounds(20, 213, 120, 14); panelEditar.add(l3);
		txtSalario = new JTextField(); txtSalario.setBounds(20, 230, 240, 25); panelEditar.add(txtSalario);

		lblEspecial = new JLabel("Dato Especial:"); lblEspecial.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblEspecial.setBounds(20, 260, 200, 14); panelEditar.add(lblEspecial);
		cmbEspecial = new JComboBox<>(); cmbEspecial.setBounds(20, 277, 240, 25); panelEditar.add(cmbEspecial);

		lblZ = new JLabel("Zona Asignada:"); lblZ.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblZ.setBounds(20, 307, 150, 14); panelEditar.add(lblZ);
		cmbZona = new JComboBox<>(new String[]{"Metropolitana", "Norte", "Sur", "Este"});
		cmbZona.setBounds(20, 324, 240, 25); panelEditar.add(cmbZona);

		chkLicencia = new JCheckBox("Licencia al día");
		chkLicencia.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		chkLicencia.setBackground(Color.WHITE);
		chkLicencia.setBounds(20, 354, 200, 25); panelEditar.add(chkLicencia);

		// ETIQUETAS DE RENDIMIENTO
		lblDatoExtra1 = new JLabel("Extra 1"); lblDatoExtra1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lblDatoExtra1.setBounds(25, 385, 240, 20); lblDatoExtra1.setVisible(false); panelEditar.add(lblDatoExtra1);

		lblDatoExtra2 = new JLabel("Extra 2"); lblDatoExtra2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lblDatoExtra2.setBounds(25, 410, 240, 20); lblDatoExtra2.setVisible(false); panelEditar.add(lblDatoExtra2);

		lblDatoExtra3 = new JLabel("Extra 3"); lblDatoExtra3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lblDatoExtra3.setBounds(25, 435, 240, 20); lblDatoExtra3.setVisible(false); panelEditar.add(lblDatoExtra3);

		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(50, 500, 180, 35);
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCambios();
			}
		});
		panelEditar.add(btnGuardar);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dispose());
		buttonPane.add(btnCerrar);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarDatosSeleccionado();
			}
		});

		lblEspecial.setVisible(false);
		cmbEspecial.setVisible(false);
		lblZ.setVisible(false);
		cmbZona.setVisible(false);
		chkLicencia.setVisible(false);
		cargarTabla();
	}

	private void cargarTabla() {
	    model.setRowCount(0);
	    for (Personal p : Altice.getInstance().getListaEmpleados()) {
	        String rol = p.getClass().getSimpleName();
	        String estadoTexto = (p.getEstado() == 1) ? "Activo" : "Inactivo";
	        model.addRow(new Object[]{p.getIdEmpleado(), p.getNombre(), p.getApellido(), rol, p.getSalarioBase(), estadoTexto});
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
	            txtCedula.setText(seleccionado.getCedula());
	            txtUsuario.setText(seleccionado.getMiCuenta().getNombreUsuario()); // <--- Mostrar el usuario
	            txtSalario.setText(String.valueOf(seleccionado.getSalarioBase()));
	            cmbEspecial.removeAllItems();

	            lblDatoExtra1.setVisible(false);
	            lblDatoExtra2.setVisible(false);
	            lblDatoExtra3.setVisible(false);

	            if (seleccionado instanceof Administrativo) {
	                lblEspecial.setText("Departamento:");
	                lblEspecial.setVisible(true); cmbEspecial.setVisible(true);
	                cmbEspecial.setModel(new DefaultComboBoxModel<>(new String[]{"Base de Datos", "Redes", "Finanzas", "Seguridad"}));
	                cmbEspecial.setSelectedItem(((Administrativo) seleccionado).getDepartamento());
	                lblZ.setVisible(false); cmbZona.setVisible(false); chkLicencia.setVisible(false);
	            } 
	            else if (seleccionado instanceof Tecnico) {
	                Tecnico t = (Tecnico) seleccionado;
	                lblEspecial.setText("Tipo de Técnico:");
	                lblEspecial.setVisible(true); cmbEspecial.setVisible(true);
	                cmbEspecial.setModel(new DefaultComboBoxModel<>(new String[]{"Instalacion", "Planta externa", "Infraestructura", "Soporte tecnico"}));
	                cmbEspecial.setSelectedItem(t.getTipoTecnico());
	                
	                lblZ.setVisible(true); cmbZona.setVisible(true);
	                cmbZona.setSelectedItem(t.getZonAsignada());
	                chkLicencia.setVisible(true);
	                chkLicencia.setSelected(t.isLicencia());

	                lblDatoExtra1.setText("H. Extras Acum.: " + t.getHorasExtrasTrabajadas());
	                lblDatoExtra2.setText("Cant. Instalaciones: " + t.getCantidadInstalaciones());
	                lblDatoExtra3.setText("Bono Acumulado: RD$ " + (t.getCantidadInstalaciones() * 250));
	                lblDatoExtra1.setVisible(true); lblDatoExtra2.setVisible(true); lblDatoExtra3.setVisible(true);
	            } 
	            else if (seleccionado instanceof Comercial) {
	                Comercial c = (Comercial) seleccionado;
	                lblEspecial.setVisible(false); cmbEspecial.setVisible(false);
	                lblZ.setVisible(false); cmbZona.setVisible(false); chkLicencia.setVisible(false);

	                lblDatoExtra1.setText("Ventas Realizadas: " + c.getVentasRealizadas());
	                lblDatoExtra2.setText("Comisión Acum.: RD$ " + c.getComisiones());
	                lblDatoExtra1.setVisible(true); lblDatoExtra2.setVisible(true);
	            }
	            
	            panelEditar.revalidate();
	            panelEditar.repaint();
	        }
	    }
	}

	private void guardarCambios() {
		try {
			if (seleccionado == null) throw new Exception("Seleccione un empleado.");
			seleccionado.setSalarioBase(Float.parseFloat(txtSalario.getText()));

			if (seleccionado instanceof Administrativo) {
				((Administrativo) seleccionado).setDepartamento(cmbEspecial.getSelectedItem().toString());
			} else if (seleccionado instanceof Tecnico) {
				((Tecnico) seleccionado).setTipoTecnico(cmbEspecial.getSelectedItem().toString());
				((Tecnico) seleccionado).setZonAsignada(cmbZona.getSelectedItem().toString());
				((Tecnico) seleccionado).setLicencia(chkLicencia.isSelected());
			}

			JOptionPane.showMessageDialog(this, "Datos actualizados.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			cargarTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}