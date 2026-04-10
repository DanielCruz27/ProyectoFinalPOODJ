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

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientesEnAlerta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Object[] raw;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientesEnAlerta dialog = new ClientesEnAlerta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ClientesEnAlerta() {
		setResizable(false);
		setBounds(100, 100, 610, 484);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				model = new DefaultTableModel();

				String headers[] = { "Cedula", "Nombre", "Estado", "Pendiente", "Pagos Atrasados"};
				table.setModel(model);
				model.setColumnIdentifiers(headers);
				scrollPane.setViewportView(table);
				table.setAutoCreateRowSorter(true);
				table.getTableHeader().setReorderingAllowed(false);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton BtnCerrar = new JButton("Cerrar");
				BtnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				BtnCerrar.setActionCommand("OK");
				buttonPane.add(BtnCerrar);
				getRootPane().setDefaultButton(BtnCerrar);
			}
		}
		loadClientesAtrasados();
	}

	private void loadClientesAtrasados() {
		model.setRowCount(0);
		raw = new Object[table.getColumnCount()];
		for (Cliente client : Altice.getInstance().getListaClientes()) {
			if(client.getCantidadAtrasos() > 1) {
				raw[0] = client.getCedula();
				raw[1] = client.getNombreCliente();
				if (client.isEstadoCliente()) {
					raw[2] = "Activo";
				} else {
					raw[2] = "Inactivo";
				}
				raw[3] = "RD$" + client.getDeudaPendiente();
				raw[4] = client.getCantidadAtrasos();
				model.addRow(raw);

			}
		}
		
	}
}
