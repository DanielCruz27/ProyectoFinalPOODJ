package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;
import java.util.ArrayList;

public class HistorialCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel model;
	private JTable table;
	private JComboBox<String> cbxCedulas;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			HistorialCliente dialog = new HistorialCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HistorialCliente() {
		setTitle("Altice - Historial de Pagos y Facturación");
		setResizable(false);
		setSize(700, 550);
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
		panelHeader.setBounds(0, 0, 700, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("HISTORIAL DE PAGOS Y FACTURAS ACUMULADAS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JLabel lblCed = new JLabel("Seleccione Cédula:");
		lblCed.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblCed.setBounds(25, 60, 130, 14);
		contentPanel.add(lblCed);

		// --- COMBO BOX DE CÉDULAS ---
		cbxCedulas = new JComboBox<String>();
		cbxCedulas.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		cbxCedulas.setBounds(150, 57, 220, 25);
		llenarComboClientes();
		contentPanel.add(cbxCedulas);
		
		// Evento para que cargue la tabla automáticamente al seleccionar
		cbxCedulas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarHistorial();
			}
		});

		// --- TABLA ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 100, 635, 340);
		contentPanel.add(scrollPane);
		
		String headers[] = {"ID Factura", "Subtotal", "Método", "ITBIS (18%)", "Monto Pagado"};
		model = new DefaultTableModel(null, headers) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		scrollPane.setViewportView(table);

		// --- BOTONES INFERIORES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(245, 245, 245));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);
		
		// Cargar datos por defecto si hay clientes
		cargarHistorial();
	}

	private void llenarComboClientes() {
		cbxCedulas.removeAllItems();
		cbxCedulas.addItem("<< Seleccione un cliente >>");
		for (Cliente c : Altice.getInstance().getListaClientes()) {
			cbxCedulas.addItem(c.getCedula() + " - " + c.getNombreCliente() + " " + c.getApellidoCliente());
		}
	}

	private void cargarHistorial() {
	    model.setRowCount(0);
	    // Validamos que no sea el índice 0 ("<< Seleccione... >>")
	    if (cbxCedulas.getSelectedIndex() <= 0) return;

	    String seleccion = cbxCedulas.getSelectedItem().toString();
	    String cedula = seleccion.split(" - ")[0]; 
	    
	    Cliente client = Altice.getInstance().buscarClienteByCedula(cedula);
	    
	    if (client != null && client.getMisPagos() != null) {
	        for (Pago p : client.getMisPagos()) {
	            
	            // 1. Identificar el método de pago
	            String metodo = "Otro";
	            if (p.getMetodoUtilizado() instanceof Efectivo) {
	                metodo = "Efectivo";
	            } else if (p.getMetodoUtilizado() instanceof Tarjeta) {
	                metodo = "Tarjeta";
	            } else if (p.getMetodoUtilizado() instanceof Cuenta) {
	                metodo = "Transferencia";
	            }

	            // 2. Calcular subtotal (Monto Total - ITBIS)
	            // Usamos p.getItbis() porque así se llama en tu clase Pago
	            float subtotal = p.getMontoTotal() - p.getItbis();

	            // 3. Agregar a la tabla con los nombres exactos de tus getters
	            model.addRow(new Object[]{
	                p.getIdFactura(), // Coincide con tu getIdFactura()
	                "RD$ " + String.format("%.2f", subtotal),
	                metodo,
	                "RD$ " + String.format("%.2f", p.getItbis()), // Coincide con tu getItbis()
	                "RD$ " + String.format("%.2f", p.getMontoTotal())
	            });
	        }
	    }
	}
}
