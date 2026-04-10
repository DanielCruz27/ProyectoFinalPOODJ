package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Altice;
import Logico.Cliente;
import Logico.Contrato;
import Logico.Cuenta;
import Logico.Efectivo;
import Logico.Pago;
import Logico.Tarjeta;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
public class HistorialCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCedula;
	private Object[] raw;
	private DefaultTableModel model;
	private JTable table;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HistorialCliente dialog = new HistorialCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public HistorialCliente() {
		setResizable(false);
		setBounds(100, 100, 542, 491);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(68, 18, 86, 20);
		contentPanel.add(txtCedula);
		txtCedula.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cedula");
		lblNewLabel.setBounds(20, 21, 48, 14);
		contentPanel.add(lblNewLabel);
		
		// Tus botones ya creados
		JRadioButton rbtnContrato = new JRadioButton("Historial Contrato");
		rbtnContrato.setBounds(10, 56, 144, 23);
		contentPanel.add(rbtnContrato);

		JRadioButton rbtnPagos = new JRadioButton("Historial Pagos");
		rbtnPagos.setBounds(183, 56, 168, 23);
		contentPanel.add(rbtnPagos);

		// --- AQUÍ CREAS EL GRUPO ---
		ButtonGroup grupoHistorial = new ButtonGroup();
		grupoHistorial.add(rbtnContrato);
		grupoHistorial.add(rbtnPagos);

		// OPCIONAL: Seleccionar uno por defecto para que nunca empiece vacío
		rbtnContrato.setSelected(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 100, 526, 319);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		
		if(rbtnContrato.isSelected()) {
			String headers[] = {"Cantidad de servicios", "Vendedor","Cantidad Pagos","Emision"};
			
			table.setModel(model);
			model.setColumnIdentifiers(headers);
			
		}
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Cliente client = Altice.getInstance().buscarClienteByCedula(txtCedula.getText());
					if (rbtnContrato.isSelected()) {
			            String headers[] = {"Servicios", "Vendedor", "Pagos", "Emision"};
			            model.setColumnIdentifiers(headers);
			            loadContrato(client);
			            
			        } else if (rbtnPagos.isSelected()) {
			            String headers[] = {"Codigo", "Monto", "Metodo", "ITBIS"};
			            model.setColumnIdentifiers(headers);
			            loadPago(client);
			        }
			    }
				
			});
			btnBuscar.setActionCommand("Cancel");
			buttonPane.add(btnBuscar);
			{
				JButton btnCerrar = new JButton("Cancel");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCerrar.setActionCommand("Cancel");
				buttonPane.add(btnCerrar);
			}
		}
	}

	protected void loadPago(Cliente client) {
		model.setRowCount(0);
		raw = new Object[table.getColumnCount()];
		ArrayList<Pago> pago = client.getMisPagos();
		for(Pago aux: pago) {
			raw[0] = aux.getIdFactura();
			raw[1] = "RD$" +aux.getMontoTotal();
			if(aux.getMetodoUtilizado() instanceof Efectivo) {
				raw[2] = "Efectivo";
			}else if(aux.getMetodoUtilizado() instanceof Tarjeta){
				raw[2] = "Tarjeta";
			}else if(aux.getMetodoUtilizado() instanceof Cuenta) {
				raw[2] = "Transferencia";
			}
			raw[3] = "RD$" + aux.getItbis();
			model.addRow(raw);
		}
		
	}

	protected void loadContrato(Cliente client) {
		model.setRowCount(0);
		raw = new Object[table.getColumnCount()];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//	String headers[] = {"Servicios", "Vendedor", "Pagos", "Emision"};

		ArrayList<Contrato> contra = client.getMisContratos();
		
		for(Contrato aux : contra) {
			raw[0] = aux.getMisServicios().size();
			raw[1] = aux.getVendedor().getNombre();
			raw[2] = aux.getHistorialDePagos().size();
			raw[3] = aux.getFechaFirma().format(formatter);
			model.addRow(raw);
			
			
		}
		
		
	}
}
