package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Altice;
import Logico.Cliente;
import Logico.Pago;
import Logico.Tarjeta;
import Logico.Efectivo;
import Logico.Cuenta;

public class MisFacturas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableFacturas;
	private DefaultTableModel model;
	private Cliente clienteLogueado;

	public MisFacturas() {
		setTitle("Altice - Mis Facturas Pagadas");
		setModal(true);
		setSize(600, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER AZUL ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 600, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("HISTORIAL DE FACTURAS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA DE FACTURAS ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 545, 300);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID Factura", "Fecha", "Método", "ITBIS", "Total Pagado"};
		model = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableFacturas = new JTable(model);
		tableFacturas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tableFacturas.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(tableFacturas);

		// --- CARGAR DATOS DEL CLIENTE ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			cargarMisFacturas();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(245, 245, 245));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 11));
				// Estilo tradicional sin lambdas
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCerrar);
			}
		}
	}

	private void cargarMisFacturas() {
		model.setRowCount(0);
		
		// Verificamos que el cliente y su lista de pagos existan
		if (clienteLogueado != null && clienteLogueado.getMisPagos() != null) {
			for (Pago p : clienteLogueado.getMisPagos()) {
				
				// 1. Identificar el nombre del método de pago para que no salga el objeto raro
				String nombreMetodo = "Otro";
				if (p.getMetodoUtilizado() instanceof Tarjeta) {
					nombreMetodo = "Tarjeta";
				} else if (p.getMetodoUtilizado() instanceof Efectivo) {
					nombreMetodo = "Efectivo";
				} else if (p.getMetodoUtilizado() instanceof Cuenta) {
					nombreMetodo = "Transferencia";
				}

				// 2. Formatear los montos con RD$ y 2 decimales
				Object[] fila = {
					p.getIdFactura(),
					p.getFechaEmision().toString(),
					nombreMetodo,
					"RD$ " + String.format("%.2f", p.getItbis()),
					"RD$ " + String.format("%.2f", p.getMontoTotal())
				};
				model.addRow(fila);
			}
		}
	}
}
