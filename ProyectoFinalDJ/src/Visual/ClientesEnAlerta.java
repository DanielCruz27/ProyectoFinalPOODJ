package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.Altice;
import Logico.Cliente;

public class ClientesEnAlerta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ClientesEnAlerta dialog = new ClientesEnAlerta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientesEnAlerta() {
		setTitle("Altice - Monitor de Clientes en Alerta");
		setResizable(false);
		setSize(800, 500); 
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204)); 
		panelHeader.setBounds(0, 0, 800, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("CLIENTES EN ALERTA");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 745, 340);
		contentPanel.add(scrollPane);

		String headers[] = { "Cédula", "Nombre", "Estado", "Deuda Pendiente", "Atrasos"};
		model = new DefaultTableModel(null, headers) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setBackground(new Color(150, 150, 150));
		table.getTableHeader().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		table.getTableHeader().setForeground(Color.BLACK);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(245, 245, 245));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar ");
		btnCerrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadClientesAtrasados();
	}

	private void loadClientesAtrasados() {
		model.setRowCount(0);
		for (Cliente client : Altice.getInstance().getListaClientes()) {
			int atrasos = Altice.getInstance().calcularAtrasosReales(client);
			float deudaDinero = Altice.getInstance().calcularMontoDeudaReal(client);

			if (atrasos > 1 || deudaDinero > 0) {
				Object[] row = new Object[5];
				row[0] = client.getCedula();
				row[1] = client.getNombreCliente() + " " + client.getApellidoCliente();
				row[2] = (atrasos > 2) ? "Suspendido" : "Activo";
				row[3] = "RD$ " + deudaDinero;
				row[4] = atrasos;
				model.addRow(row);
			}
		}
	}
}