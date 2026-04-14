package Visual;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class MetodoPago extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableMetodos;
	private DefaultTableModel model;
	private Cliente clienteLogueado;

	private JTextField txtNumTarjeta, txtCVV, txtFechaVenc;
	private JComboBox<String> cbxTipoTarjeta;

	private JTextField txtNumCuenta, txtBanco;
	private JComboBox<String> cbxTipoCuenta;

	private JComboBox<String> cbxTipoPrincipal;
	private JPanel panelDinamico;
	private CardLayout cardLayout;

	public MetodoPago() {
		setTitle("Altice - Gestión de Pagos");
		setModal(true);
		setSize(550, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 550, 40);
		contentPanel.add(panelHeader);
		panelHeader.add(new JLabel("CONFIGURAR MÉTODOS DE PAGO") {{
			setForeground(Color.WHITE);
			setFont(new Font("Arial", Font.BOLD, 14));
		}});

		JLabel lblTipo = new JLabel("Seleccione Tipo de Método:");
		lblTipo.setBounds(25, 55, 200, 14);
		contentPanel.add(lblTipo);

		cbxTipoPrincipal = new JComboBox<>(new String[] {"Tarjeta", "Cuenta", "Efectivo"});
		cbxTipoPrincipal.setBounds(25, 75, 150, 25);
		contentPanel.add(cbxTipoPrincipal);

		cardLayout = new CardLayout();
		panelDinamico = new JPanel(cardLayout);
		panelDinamico.setBounds(25, 110, 480, 130);
		panelDinamico.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY), " Datos del Método "));
		contentPanel.add(panelDinamico);

		JPanel pnlTarjeta = new JPanel(null);
		pnlTarjeta.add(new JLabel("Número:")).setBounds(10, 20, 80, 14);
		txtNumTarjeta = new JTextField(); 
		pnlTarjeta.add(txtNumTarjeta).setBounds(10, 40, 150, 25);

		pnlTarjeta.add(new JLabel("Venc. (dd/mm/yyyy):")).setBounds(170, 20, 120, 14);
		txtFechaVenc = new JTextField(); 
		pnlTarjeta.add(txtFechaVenc).setBounds(170, 40, 100, 25);

		pnlTarjeta.add(new JLabel("  Tipo:")).setBounds(280, 20, 80, 14);
		cbxTipoTarjeta = new JComboBox<>(new String[] {"Visa", "MasterCard"});
		pnlTarjeta.add(cbxTipoTarjeta).setBounds(280, 40, 100, 25);

		pnlTarjeta.add(new JLabel("CVV:")).setBounds(390, 20, 50, 14);
		txtCVV = new JTextField();
		pnlTarjeta.add(txtCVV).setBounds(390, 40, 60, 25);

		JPanel pnlCuenta = new JPanel(null);
		pnlCuenta.add(new JLabel("Número de Cuenta:")).setBounds(10, 20, 150, 14);
		txtNumCuenta = new JTextField(); 
		pnlCuenta.add(txtNumCuenta).setBounds(10, 40, 180, 25);

		pnlCuenta.add(new JLabel("Tipo:")).setBounds(200, 20, 100, 14);
		cbxTipoCuenta = new JComboBox<>(new String[] {"Ahorro", "Corriente"});
		pnlCuenta.add(cbxTipoCuenta).setBounds(200, 40, 120, 25);

		pnlCuenta.add(new JLabel("Banco:")).setBounds(330, 20, 100, 14);
		txtBanco = new JTextField();
		pnlCuenta.add(txtBanco).setBounds(330, 40, 130, 25);

		JPanel pnlEfectivo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlEfectivo.add(new JLabel("El pago se realizará en efectivo en nuestras oficinas."));

		panelDinamico.add(pnlTarjeta, "Tarjeta");
		panelDinamico.add(pnlCuenta, "Cuenta");
		panelDinamico.add(pnlEfectivo, "Efectivo");

		cbxTipoPrincipal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelDinamico, (String)cbxTipoPrincipal.getSelectedItem());
			}
		});
		JButton btnAdd = new JButton("Vincular Método");
		btnAdd.setBackground(new Color(0, 153, 51));
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBounds(185, 250, 160, 30);
		btnAdd.addActionListener(e -> agregarMetodo());
		contentPanel.add(btnAdd);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 300, 480, 180);
		contentPanel.add(scrollPane);
		model = new DefaultTableModel(null, new String[] {"Tipo", "Identificador", "Detalle"}) {
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		tableMetodos = new JTable(model);
		scrollPane.setViewportView(tableMetodos);

		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			actualizarTabla();
		}
	}

	private void actualizarTabla() {
		model.setRowCount(0);
		if (clienteLogueado != null) {
			for (MetodoDePago m : clienteLogueado.getMisMetodos()) {
				String tipo = m.getClass().getSimpleName();
				model.addRow(new Object[] { tipo, m.getIdMetodo(), m.toString() });
			}
		}
	}

	private void agregarMetodo() {
		String seleccion = (String) cbxTipoPrincipal.getSelectedItem();
		String titular = clienteLogueado.getNombreCliente() + " " + clienteLogueado.getApellidoCliente();
		String id = "MET-" + (clienteLogueado.getMisMetodos().size() + 1);
		MetodoDePago nuevo = null;

		try {
			if (seleccion.equals("Tarjeta")) {
				if (txtNumTarjeta.getText().isEmpty() || txtCVV.getText().isEmpty() || txtFechaVenc.getText().isEmpty()) {
					throw new Exception("Por favor, complete todos los campos de la tarjeta.");
				}

				double num = Double.parseDouble(txtNumTarjeta.getText());
				int cvv = Integer.parseInt(txtCVV.getText());

				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaVenc = LocalDate.parse(txtFechaVenc.getText(), fmt);
				LocalDate hoy = LocalDate.now();

				if (fechaVenc.isBefore(hoy) || fechaVenc.isEqual(hoy)) {
					throw new Exception("La tarjeta está vencida. Debe expirar después de la fecha actual (" + hoy.format(fmt) + ").");
				}

				nuevo = new Tarjeta(titular, id, num, fechaVenc, (String)cbxTipoTarjeta.getSelectedItem(), cvv);

			} else if (seleccion.equals("Cuenta")) {
				if (txtNumCuenta.getText().isEmpty() || txtBanco.getText().isEmpty()) {
					throw new Exception("Por favor, complete los campos de la cuenta bancaria.");
				}
				double numC = Double.parseDouble(txtNumCuenta.getText());
				nuevo = new Cuenta(titular, id, numC, (String)cbxTipoCuenta.getSelectedItem(), txtBanco.getText());

			} else {
				nuevo = new Efectivo(titular, id, "DOP", 0.0f);
			}

			if (nuevo != null) {
				clienteLogueado.addMetodoPago(nuevo);
				actualizarTabla();
				JOptionPane.showMessageDialog(this, "Método de pago " + seleccion + " guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				limpiarCamposDinamicos();
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Error: Asegúrese de ingresar solo números en los campos de Tarjeta/Cuenta/CVV.", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use el formato día/mes/año (Ejemplo: 25/12/2030).", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void limpiarCamposDinamicos() {
		txtNumTarjeta.setText("");
		txtCVV.setText("");
		txtFechaVenc.setText("");
		txtNumCuenta.setText("");
		txtBanco.setText("");
	}
}