package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

import Logico.Administrativo;
import Logico.Altice;
import Logico.Comercial;
import Logico.Tecnico;
import Logico.Usuario;

import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class RegistrarPersonal extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtIdentificador;
	private JTextField txtCedula;
	private JRadioButton rbAdministrativo;
	private JRadioButton rbTecnico;
	private JRadioButton rbComercial;
	private JComboBox cmbTipo;
	private JPanel panel_Tecnico;
	private JPasswordField txtPassword;
	private JPanel panel_Comercial;
	private JPanel panel_Administrativo;
	private JPanel panel_Usuario;
	private JButton btnRegistrar;
	private JComboBox cmbZona;
	private JSpinner spnSalarioBase;
	private JComboBox cmbDepartamento;
	private JCheckBox chkbxLicencia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarPersonal dialog = new RegistrarPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarPersonal() {
		setTitle("Registrar Personal");
		setResizable(false);
		setBounds(100, 100, 456, 470);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtNombre = new JTextField();
		txtNombre.setBounds(10, 74, 96, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(10, 59, 48, 14);
		contentPanel.add(lblNewLabel);

		txtIdentificador = new JTextField();
		txtIdentificador.setText("P-" + Altice.codigoPersonal);
		txtIdentificador.setEnabled(false);
		txtIdentificador.setColumns(10);
		txtIdentificador.setBounds(10, 28, 96, 20);
		contentPanel.add(txtIdentificador);

		JLabel Codigo = new JLabel("Codigo");
		Codigo.setBounds(10, 11, 48, 14);
		contentPanel.add(Codigo);

		txtCedula = new JTextField();
		txtCedula.setColumns(10);
		txtCedula.setBounds(127, 28, 96, 20);
		contentPanel.add(txtCedula);

		JLabel lblCedula = new JLabel("Cedula");
		lblCedula.setBounds(127, 11, 48, 14);
		contentPanel.add(lblCedula);

		spnSalarioBase = new JSpinner();
		spnSalarioBase.setModel(new SpinnerNumberModel(Float.valueOf(1), Float.valueOf(1), null, Float.valueOf(1)));
		spnSalarioBase.setBounds(127, 74, 96, 20);
		contentPanel.add(spnSalarioBase);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 105, 416, 66);
		contentPanel.add(panel);
		panel.setLayout(null);

		rbAdministrativo = new JRadioButton("Administrativo");
		rbAdministrativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbAdministrativo.isSelected()) {
					rbComercial.setSelected(false);
					rbTecnico.setSelected(false);
					panel_Administrativo.setVisible(true);
					btnRegistrar.setEnabled(true);
				}
				if (!rbAdministrativo.isSelected()) {
					panel_Tecnico.setVisible(false);
					rbAdministrativo.setSelected(false);
					btnRegistrar.setEnabled(false);

				}
			}
		});
		rbAdministrativo.setBounds(6, 22, 110, 22);
		panel.add(rbAdministrativo);

		rbTecnico = new JRadioButton("Tecnico");
		rbTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbTecnico.isSelected()) {
					rbComercial.setSelected(false);
					rbAdministrativo.setSelected(false);
					panel_Tecnico.setVisible(true);
					btnRegistrar.setEnabled(true);

				}
				if (!rbTecnico.isSelected()) {
					rbTecnico.setSelected(false);
					btnRegistrar.setEnabled(false);

				}
			}
		});
		rbTecnico.setBounds(181, 22, 110, 22);
		panel.add(rbTecnico);

		rbComercial = new JRadioButton("Comercial");
		rbComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbComercial.isSelected()) {
					rbAdministrativo.setSelected(false);
					rbTecnico.setSelected(false);
					panel_Comercial.setVisible(true);
					panel_Administrativo.setVisible(false);
					panel_Tecnico.setVisible(false);
					btnRegistrar.setEnabled(true);

				}
				if (!rbComercial.isSelected()) {
					panel_Comercial.setVisible(false);
					btnRegistrar.setEnabled(false);

				}
			}
		});
		rbComercial.setBounds(315, 22, 95, 22);
		panel.add(rbComercial);

		JLabel lblSalariobase = new JLabel("SalarioBase");
		lblSalariobase.setBounds(127, 59, 110, 14);
		contentPanel.add(lblSalariobase);

		panel_Tecnico = new JPanel();
		panel_Tecnico.setVisible(false);
		panel_Tecnico.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_Tecnico.setBounds(10, 199, 416, 78);
		contentPanel.add(panel_Tecnico);
		panel_Tecnico.setLayout(null);

		cmbTipo = new JComboBox();
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] { "<<Seleccionar>>", "Instalacion ", "Planta externa",
				"Infraestructura", "Soporte Tecnico" }));
		cmbTipo.setBounds(10, 31, 114, 22);
		panel_Tecnico.add(cmbTipo);

		JLabel Tipo = new JLabel("Tipo");
		Tipo.setBounds(10, 11, 86, 14);
		panel_Tecnico.add(Tipo);

		cmbZona = new JComboBox();
		cmbZona.setModel(
				new DefaultComboBoxModel(new String[] { "<<Selecionar>>", "Metropolitana", "Norte", "Sur", "Este" }));
		cmbZona.setBounds(141, 31, 114, 22);
		panel_Tecnico.add(cmbZona);

		JLabel lblZona = new JLabel("Zona");
		lblZona.setBounds(138, 11, 86, 14);
		panel_Tecnico.add(lblZona);

		chkbxLicencia = new JCheckBox("Licencia");
		chkbxLicencia.setBounds(284, 31, 132, 22);
		panel_Tecnico.add(chkbxLicencia);

		panel_Usuario = new JPanel();
		panel_Usuario.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_Usuario.setBounds(10, 288, 416, 83);
		contentPanel.add(panel_Usuario);
		panel_Usuario.setLayout(null);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 36, 144, 20);
		panel_Usuario.add(txtPassword);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(10, 11, 131, 14);
		panel_Usuario.add(lblContrasea);

		panel_Administrativo = new JPanel();
		panel_Administrativo.setVisible(false);
		panel_Administrativo.setBounds(10, 199, 416, 78);
		contentPanel.add(panel_Administrativo);
		panel_Administrativo.setLayout(null);
		panel_Administrativo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		cmbDepartamento = new JComboBox();
		cmbDepartamento.setModel(new DefaultComboBoxModel(
				new String[] { "<<Seleccionar>>", "Base de Datos", "Redes", "Finanzas", "Seguridad" }));
		cmbDepartamento.setBounds(10, 31, 231, 22);
		panel_Administrativo.add(cmbDepartamento);

		JLabel Departamento_1 = new JLabel("Departamento");
		Departamento_1.setBounds(10, 11, 86, 14);
		panel_Administrativo.add(Departamento_1);

		panel_Comercial = new JPanel();
		panel_Comercial.setVisible(false);
		panel_Comercial.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_Comercial.setBounds(10, 199, 416, 71);
		contentPanel.add(panel_Comercial);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nombreUsuario = txtNombre.getText() + "_" + Altice.codigoPersonal;
						Usuario user = new Usuario(nombreUsuario, new String(txtPassword.getPassword()));
						if (rbAdministrativo.isSelected()) {
							Administrativo admin = new Administrativo(txtIdentificador.getText(), txtNombre.getText(),
									txtCedula.getText(), ((float) spnSalarioBase.getValue()), user,
									cmbDepartamento.getSelectedItem().toString());
							Altice.getInstance().RegistarPersonal(admin);
						} else if (rbTecnico.isSelected()) {

							Tecnico tec = new Tecnico(txtIdentificador.getText(), txtNombre.getText(),
									txtCedula.getText(), ((float) spnSalarioBase.getValue()), user,
									cmbTipo.getSelectedItem().toString(), cmbZona.getSelectedItem().toString(),
									chkbxLicencia.isSelected(), 0, 0, 0);
							Altice.getInstance().RegistarPersonal(tec);
						} else if (rbComercial.isSelected()) {
							Comercial comercial = new Comercial(txtIdentificador.getText(), txtNombre.getText(),
									txtCedula.getText(), ((float) spnSalarioBase.getValue()), user, 0, 0);
							Altice.getInstance().RegistarPersonal(comercial);
						}

						JOptionPane.showMessageDialog(null, "Nombre de usuario es: " + nombreUsuario, "Confirmacion",
								JOptionPane.INFORMATION_MESSAGE);
						clean();
					}
				});
				btnRegistrar.setEnabled(false);
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void clean() {
		txtNombre.setText("");
		txtCedula.setText("");
		txtPassword.setText("");

		txtIdentificador.setText("P-" + Altice.codigoPersonal);

		spnSalarioBase.setValue(Float.valueOf(1));
		rbAdministrativo.setSelected(false);
		rbTecnico.setSelected(false);
		rbComercial.setSelected(false);
		btnRegistrar.setEnabled(false);

		cmbDepartamento.setSelectedIndex(0);
		cmbTipo.setSelectedIndex(0);
		cmbZona.setSelectedIndex(0);

		chkbxLicencia.setSelected(false);

		panel_Administrativo.setVisible(false);
		panel_Tecnico.setVisible(false);
		panel_Comercial.setVisible(false);
	}

}
