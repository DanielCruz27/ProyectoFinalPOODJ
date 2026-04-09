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

        // --- CABECERA AZUL ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 500, 40);
        contentPanel.add(panelHeader);
        
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO EMPLEADO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
        panelHeader.add(lblTitulo);

        // --- CAMPOS GENERALES ---
        JLabel lblId = new JLabel("ID Empleado:");
        lblId.setBounds(30, 60, 100, 14);
        contentPanel.add(lblId);

        txtIdentificador = new JTextField("P-" + Altice.getInstance().getCodigotPersonal());     
        txtIdentificador.setEditable(false);
        txtIdentificador.setBounds(30, 80, 120, 25);
        txtIdentificador.setBackground(new Color(245, 245, 245));
        contentPanel.add(txtIdentificador);

        JLabel lblCed = new JLabel("Cédula:");
        lblCed.setBounds(240, 60, 100, 14);
        contentPanel.add(lblCed);

        txtCedula = new JTextField();
        txtCedula.setBounds(240, 80, 200, 25);
        contentPanel.add(txtCedula);

        JLabel lblNom = new JLabel("Nombre:");
        lblNom.setBounds(30, 120, 100, 14);
        contentPanel.add(lblNom);

        txtNombre = new JTextField();
        txtNombre.setBounds(30, 140, 200, 25);
        contentPanel.add(txtNombre);

        JLabel lblApe = new JLabel("Apellido:");
        lblApe.setBounds(240, 120, 100, 14);
        contentPanel.add(lblApe);

        txtApellido = new JTextField();
        txtApellido.setBounds(240, 140, 200, 25);
        contentPanel.add(txtApellido);

        JLabel lblSal = new JLabel("Salario Base:");
        lblSal.setBounds(30, 180, 100, 14);
        contentPanel.add(lblSal);

        spnSalarioBase = new JSpinner(new SpinnerNumberModel(27489.60, 15000.0, 500000.0, 500.0));
        spnSalarioBase.setBounds(30, 200, 120, 25);
        contentPanel.add(spnSalarioBase);

        // --- SELECTOR DE ROL ---
        JPanel panelRoles = new JPanel();
        panelRoles.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Tipo de Empleado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 102, 204)));
        panelRoles.setBackground(Color.WHITE);
        panelRoles.setBounds(30, 240, 420, 60);
        contentPanel.add(panelRoles);
        panelRoles.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        rbAdministrativo = new JRadioButton("Administrativo");
        rbTecnico = new JRadioButton("Técnico");
        rbComercial = new JRadioButton("Comercial");
        rbAdministrativo.setBackground(Color.WHITE);
        rbTecnico.setBackground(Color.WHITE);
        rbComercial.setBackground(Color.WHITE);
        
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbAdministrativo); grupo.add(rbTecnico); grupo.add(rbComercial);
        panelRoles.add(rbAdministrativo); panelRoles.add(rbTecnico); panelRoles.add(rbComercial);

     // --- PANEL DINÁMICO (ESTILIZADO) ---
        panelEspecializado = new JPanel();
        panelEspecializado.setBackground(new Color(252, 252, 252)); // Blanco humo
        
        // Creamos un borde con título azul para que sepa qué está llenando
        TitledBorder bordeTitulo = BorderFactory.createTitledBorder(
            new LineBorder(new Color(0, 102, 204), 1, true), " Datos Específicos según Rol ");
        bordeTitulo.setTitleFont(new Font("Arial", Font.BOLD, 12));
        bordeTitulo.setTitleColor(new Color(0, 102, 204));
        
        panelEspecializado.setBorder(bordeTitulo);
        panelEspecializado.setBounds(30, 310, 420, 100); // Un poco más alto para que respire
        contentPanel.add(panelEspecializado);
        panelEspecializado.setLayout(null);
        panelEspecializado.setVisible(false);

        // --- COMPONENTES DEL PANEL (CON ESTILO) ---
        cmbTipo = new JComboBox<>(new String[]{"<<Tipo de Técnico>>", "Instalacion", "Planta externa", "Infraestructura", "Soporte tecnico"});
        cmbZona = new JComboBox<>(new String[]{"<<Zona Asignada>>", "Metropolitana", "Norte", "Sur", "Este"});
        chkbxLicencia = new JCheckBox("Licencia al día");
        chkbxLicencia.setBackground(new Color(252, 252, 252));
        chkbxLicencia.setFont(new Font("Arial", Font.PLAIN, 12));
        
        cmbDepartamento = new JComboBox<>(new String[]{"<<Departamento>>", "Base de Datos", "Redes", "Finanzas", "Seguridad"});

        // --- EVENTOS (FORMA TRADICIONAL) ---
        ActionListener rolListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelEspecializado.removeAll();
                
                if(rbTecnico.isSelected()){
                    panelEspecializado.setVisible(true);
                    // Los posicionamos con cuidado dentro del panel
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
                    lblDepto.setBounds(15, 25, 200, 14);
                    lblDepto.setFont(new Font("Arial", Font.ITALIC, 11));
                    
                    cmbDepartamento.setBounds(15, 45, 375, 25);
                    
                    panelEspecializado.add(lblDepto);
                    panelEspecializado.add(cmbDepartamento);
                } 
                else if(rbComercial.isSelected()) {
                    // El comercial no tiene datos extra, pero ponemos un mensaje de info
                    panelEspecializado.setVisible(true);
                    JLabel lblInfo = new JLabel("No se requiere información adicional para el área comercial.");
                    lblInfo.setBounds(15, 40, 380, 14);
                    lblInfo.setForeground(Color.GRAY);
                    panelEspecializado.add(lblInfo);
                }

                // Forzamos a Java a que vuelva a dibujar el panel
                panelEspecializado.revalidate();
                panelEspecializado.repaint();
            }
        };

        rbAdministrativo.addActionListener(rolListener);
        rbTecnico.addActionListener(rolListener);
        rbComercial.addActionListener(rolListener);

        // --- PANEL USUARIO ---
        JLabel lblPass = new JLabel("Contraseña inicial:");
        lblPass.setBounds(30, 420, 150, 14);
        contentPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(30, 445, 200, 25);
        contentPanel.add(txtPassword);

        // --- BOTONES ---
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnRegistrar = new JButton("Registrar Empleado");
        btnRegistrar.setBackground(new Color(0, 153, 51));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRegistro();
            }
        });
        buttonPane.add(btnRegistrar);

        JButton btnCancel = new JButton("Cancelar");
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
            // 1. VALIDACIÓN DE CAMPOS VACÍOS
            if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || 
                txtCedula.getText().trim().isEmpty() || new String(txtPassword.getPassword()).isEmpty()) {
                throw new Exception("Todos los campos obligatorios deben estar llenos.");
            }

            // 2. VALIDACIÓN DE NOMBRE Y APELLIDO (Solo letras)
            if (!txtNombre.getText().trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                throw new Exception("El nombre solo debe contener letras.");
            }
            if (!txtApellido.getText().trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                throw new Exception("El apellido solo debe contener letras.");
            }

            // 3. VALIDACIÓN DE CÉDULA (Solo números)
            if (!txtCedula.getText().trim().matches("\\d+")) {
                throw new Exception("La cédula debe contener solo números (sin guiones).");
            }

            // 4. VALIDACIÓN DE ROL
            if (!rbAdministrativo.isSelected() && !rbTecnico.isSelected() && !rbComercial.isSelected()) {
                throw new Exception("Debe seleccionar un tipo de empleado.");
            }

            // Datos ya validados
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String cedula = txtCedula.getText().trim();
            
            // 5. VALIDACIÓN DE SALARIO
            float salario;
            try {
                salario = Float.parseFloat(spnSalarioBase.getValue().toString());
                if (salario <= 0) throw new Exception();
            } catch (Exception e) {
                throw new Exception("El salario base debe ser un monto numérico positivo.");
            }

            // --- PROCESO DE REGISTRO ---
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
                                       cmbTipo.getSelectedItem().toString(), cmbZona.getSelectedItem().toString(), 
                                       chkbxLicencia.isSelected(), 0, 0.0f, 0);
                
            } else if (rbComercial.isSelected()) {
                nuevoEmp = new Comercial(txtIdentificador.getText(), nombre, apellido, cedula, salario, nuevoUser, 0, 0);
            }

            if (nuevoEmp != null) {
                Altice.getInstance().RegistarPersonal(nuevoEmp);
                JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.\nUsuario: " + userStr, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // --- LIMPIEZA PARA SEGUIR REGISTRANDO ---
                limpiarCampos();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        // Limpiamos los textos
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtPassword.setText("");
        
        txtIdentificador.setText("P-" + Altice.getInstance().getCodigotPersonal());        
        spnSalarioBase.setValue(27489.6);
        cmbDepartamento.setSelectedIndex(0);
        cmbTipo.setSelectedIndex(0);
        cmbZona.setSelectedIndex(0);
        chkbxLicencia.setSelected(false);
        
        rbAdministrativo.setSelected(false);
        rbTecnico.setSelected(false);
        rbComercial.setSelected(false);
        panelEspecializado.setVisible(false);
        
        txtCedula.requestFocus();
    }
}
