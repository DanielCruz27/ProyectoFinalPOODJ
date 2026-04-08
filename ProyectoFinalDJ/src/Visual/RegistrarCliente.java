package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logico.Altice;
import Logico.Cliente;
import Logico.MetodoDePago;
import Logico.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField txtPassword;
	private JComboBox cbxVivienda;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDireccion;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarCliente dialog = new RegistrarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarCliente() {
		setTitle("Registrar Cliente");
		setResizable(false);
		setBounds(100, 100, 450, 385);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel IdCliente = new JLabel("Codigo");
		IdCliente.setBounds(15, 16, 69, 20);
		contentPanel.add(IdCliente);
		
		txtCodigo = new JTextField();
		txtCodigo.setText("C-"+ Altice.codigoCliente);
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(15, 39, 146, 26);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(15, 101, 146, 26);
		contentPanel.add(txtNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(15, 81, 69, 20);
		contentPanel.add(lblNombre);
		
		JLabel Apellido = new JLabel("Apellido");
		Apellido.setBounds(176, 81, 69, 20);
		contentPanel.add(Apellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(176, 101, 146, 26);
		contentPanel.add(txtApellido);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(15, 215, 307, 26);
		contentPanel.add(txtEmail);
		
		JLabel Email = new JLabel("Email");
		Email.setBounds(15, 193, 69, 20);
		contentPanel.add(Email);
		
		cbxVivienda = new JComboBox();
		cbxVivienda.setModel(new DefaultComboBoxModel(new String[] {"<<Selecionar>>", "Metropolitana", "Norte", "Sur", "Este"}));
		cbxVivienda.setBounds(176, 38, 146, 28);
		contentPanel.add(cbxVivienda);
		
		JLabel lblZonaVivienda = new JLabel("Zona Vivienda");
		lblZonaVivienda.setBounds(176, 16, 196, 20);
		contentPanel.add(lblZonaVivienda);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(15, 265, 307, 26);
		contentPanel.add(txtPassword);
		
		JLabel Contrasea = new JLabel("Contraseña");
		Contrasea.setBounds(15, 243, 230, 20);
		contentPanel.add(Contrasea);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(15, 156, 307, 26);
		contentPanel.add(txtDireccion);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(15, 132, 91, 20);
		contentPanel.add(lblDireccion);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					Usuario user = new Usuario(txtEmail.getText(), txtPassword.getPassword().toString());
					if(!Altice.getInstance().buscarUsuario(user.getNombreUsuario())) {	
				Cliente client = new Cliente(txtCodigo.getText(),txtNombre.getText(), txtApellido.getText(),txtEmail.getText(), txtDireccion.getText(), user,cbxVivienda.getSelectedItem().toString(), 0 ,false ,null, 0, 0, null, null);
				Altice.getInstance().InsertaCliente(client);
				JOptionPane.showMessageDialog(null,"Registro Exitoso", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
				clean();
						}else {
							JOptionPane.showMessageDialog(null,"Email ya esta en uso", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
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
		// TODO Auto-generated method stub
		txtApellido.setText("");
		txtNombre.setText("");
		txtEmail.setText("");
		txtApellido.setText("");
		cbxVivienda.setSelectedIndex(0);
		txtCodigo.setText("C-"+Altice.codigoCliente);
		
		
		
	}
}
