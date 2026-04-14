package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Logico.*;

public class RegistrarPersonal extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre, txtApellido, txtCedula, txtIdentificador;
	private JPasswordField txtPassword;
	private JSpinner spnSalarioBase;
	private JRadioButton rbAdministrativo, rbTecnico, rbComercial;
	private JComboBox<String> cmbTipo, cmbZona, cmbDepartamento;
	private JCheckBox chkbxLicencia;
	private JPanel panelEspecializado;
	private JButton btnRegistrar;

	public static void main(String[] args) {
		try {
			RegistrarPersonal dialog = new RegistrarPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarPersonal() {
		setTitle("Altice - Registro de Personal");
		setResizable(false);
		setModal(true);
		setSize(500, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 500, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO EMPLEADO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JLabel lblId = new JLabel("ID Empleado:");
		lblId.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblId.setBounds(30, 60, 100, 14);
		contentPanel.add(lblId);

		txtIdentificador = new JTextField("P-" + Altice.getInstance().getCodigotPersonal());
		txtIdentificador.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtIdentificador.setEditable(false);
		txtIdentificador.setBounds(30, 80, 120, 25);
		txtIdentificador.setBackground(new Color(245, 245, 245));
		contentPanel.add(txtIdentificador);

		JLabel lblCed = new JLabel("Cédula:");
		lblCed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblCed.setBounds(240, 60, 100, 14);
		contentPanel.add(lblCed);

		txtCedula = new JTextField();
		txtCedula.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtCedula.setBounds(240, 80, 200, 25);
		contentPanel.add(txtCedula);

		JLabel lblNom = new JLabel("Nombre:");
		lblNom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNom.setBounds(30, 120, 100, 14);
		contentPanel.add(lblNom);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtNombre.setBounds(30, 140, 200, 25);
		contentPanel.add(txtNombre);

		JLabel lblApe = new JLabel("Apellido:");
		lblApe.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblApe.setBounds(240, 120, 100, 14);
		contentPanel.add(lblApe);

		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtApellido.setBounds(240, 140, 200, 25);
		contentPanel.add(txtApellido);

		JLabel lblSal = new JLabel("Salario Base:");
		lblSal.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblSal.setBounds(30, 180, 100, 14);
		contentPanel.add(lblSal);

		spnSalarioBase = new JSpinner(new SpinnerNumberModel(27489.60, 15000.0, 500000.0, 500.0));
		spnSalarioBase.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		spnSalarioBase.setBounds(30, 200, 120, 25);
		contentPanel.add(spnSalarioBase);

		JPanel panelRoles = new JPanel();
		TitledBorder borderRoles = new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Tipo de Empleado", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelRoles.setBorder(borderRoles);
		panelRoles.setBackground(Color.WHITE);
		panelRoles.setBounds(30, 240, 420, 60);
		contentPanel.add(panelRoles);
		panelRoles.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

		rbAdministrativo = new JRadioButton("Administrativo");
		rbAdministrativo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		rbAdministrativo.setBackground(Color.WHITE);

		rbTecnico = new JRadioButton("Técnico");
		rbTecnico.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		rbTecnico.setBackground(Color.WHITE);

		rbComercial = new JRadioButton("Comercial");
		rbComercial.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		rbComercial.setBackground(Color.WHITE);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rbAdministrativo); grupo.add(rbTecnico); grupo.add(rbComercial);
		panelRoles.add(rbAdministrativo); panelRoles.add(rbTecnico); panelRoles.add(rbComercial);

		panelEspecializado = new JPanel();
		panelEspecializado.setBackground(new Color(252, 252, 252));
		TitledBorder bordeTitulo = BorderFactory.createTitledBorder(
				new LineBorder(new Color(0, 102, 204), 1, true), " Datos Específicos según Rol ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204));
		panelEspecializado.setBorder(bordeTitulo);
		panelEspecializado.setBounds(30, 310, 420, 100);
		contentPanel.add(panelEspecializado);
		panelEspecializado.setLayout(null);
		panelEspecializado.setVisible(false);

		cmbTipo = new JComboBox<>(new String[]{"<<Tipo de Técnico>>", "Instalacion", "Planta externa", "Infraestructura", "Soporte tecnico"});
		cmbTipo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));

		cmbZona = new JComboBox<>(new String[]{"<<Zona Asignada>>", "Metropolitana", "Norte", "Sur", "Este"});
		cmbZona.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));

		chkbxLicencia = new JCheckBox("Licencia al día");
		chkbxLicencia.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		chkbxLicencia.setBackground(new Color(252, 252, 252));

		cmbDepartamento = new JComboBox<>(new String[]{"<<Departamento>>", "Base de Datos", "Redes", "Finanzas", "Seguridad"});
		cmbDepartamento.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));

		ActionListener rolListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelEspecializado.removeAll();
				if(rbTecnico.isSelected()){
					panelEspecializado.setVisible(true);
					cmbTipo.setBounds(15, 30, 180, 25);
					cmbZona.setBounds(210, 30, 180, 25);
					chkbxLicencia.setBounds(15, 65, 150, 25);
					panelEspecializado.add(cmbTipo);
					panelEspecializado.add(cmbZona);
					panelEspecializado.add(chkbxLicencia);
				} 
				else if(rbAdministrativo.isSelected()){
					panelEspecializado.setVisible(true);
					JLabel lblDepto = new JLabel("Seleccione el área de trabajo:");
					lblDepto.setFont(new Font("Arial Rounded MT Bold", Font.ITALIC, 11));
					lblDepto.setBounds(15, 25, 200, 14);
					cmbDepartamento.setBounds(15, 45, 375, 25);
					panelEspecializado.add(lblDepto);
					panelEspecializado.add(cmbDepartamento);
				} 
				else if(rbComercial.isSelected()) {
					panelEspecializado.setVisible(true);
					JLabel lblInfo = new JLabel("No se requiere información adicional para el área comercial.");
					lblInfo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
					lblInfo.setBounds(15, 40, 380, 14);
					lblInfo.setForeground(Color.GRAY);
					panelEspecializado.add(lblInfo);
				}
				panelEspecializado.revalidate();
				panelEspecializado.repaint();
			}
		};

		rbAdministrativo.addActionListener(rolListener);
		rbTecnico.addActionListener(rolListener);
		rbComercial.addActionListener(rolListener);

		JLabel lblPass = new JLabel("Contraseña de Acceso:");
		lblPass.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblPass.setBounds(30, 420, 150, 14);
		contentPanel.add(lblPass);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(30, 445, 200, 25);
		contentPanel.add(txtPassword);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnRegistrar = new JButton("Registrar Empleado");
		btnRegistrar.setBackground(new Color(0, 153, 51));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				realizarRegistro();
			}
		});
		buttonPane.add(btnRegistrar);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancel);
	}

	private void realizarRegistro() {
		try {
			if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || 
					txtCedula.getText().trim().isEmpty() || new String(txtPassword.getPassword()).isEmpty()) {
				throw new Exception("Todos los campos obligatorios deben estar llenos.");
			}

			String nombre = txtNombre.getText().trim();
			String apellido = txtApellido.getText().trim();
			String cedula = txtCedula.getText().trim();
			float salario = Float.parseFloat(spnSalarioBase.getValue().toString());

			String userStr = nombre.toLowerCase() + "." + apellido.toLowerCase();
			Usuario nuevoUser = new Usuario(userStr, new String(txtPassword.getPassword()));
			Personal nuevoEmp = null;

			if (rbAdministrativo.isSelected()) {
				if (cmbDepartamento.getSelectedIndex() == 0) throw new Exception("Seleccione un departamento.");
				nuevoEmp = new Administrativo(txtIdentificador.getText(), nombre, apellido, cedula, salario, nuevoUser, 
						cmbDepartamento.getSelectedItem().toString());
			} else if (rbTecnico.isSelected()) {
				if (cmbTipo.getSelectedIndex() == 0 || cmbZona.getSelectedIndex() == 0) throw new Exception("Complete los datos del técnico.");
				nuevoEmp = new Tecnico(txtIdentificador.getText(), nombre, apellido, cedula, salario, nuevoUser, 
						cmbTipo.getSelectedItem().toString(), cmbZona.getSelectedItem().toString(), chkbxLicencia.isSelected(), 0, 0.0f, 0);
			} else if (rbComercial.isSelected()) {
				nuevoEmp = new Comercial(txtIdentificador.getText(), nombre, apellido, cedula, salario, nuevoUser, 0, 0);
			}

			if (nuevoEmp != null) {
				Altice.getInstance().RegistarPersonal(nuevoEmp);
				JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.\nUsuario: " + userStr, "Éxito", JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		txtPassword.setText("");
		txtIdentificador.setText("P-" + Altice.getInstance().getCodigotPersonal());
		spnSalarioBase.setValue(27489.6);
		panelEspecializado.setVisible(false);
		txtCedula.requestFocus();
	}
}