package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Altice;
import Logico.Cliente;
import Logico.Contrato;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class verVentasRealizadas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Object[] raw;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			verVentasRealizadas dialog = new verVentasRealizadas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public verVentasRealizadas() {
		setBounds(100, 100, 450, 300);
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
				table = new JTable();
				model = new DefaultTableModel();
				
				String headers[] = { "Cliente", "Servicios", "Pagos", "Firmas"};
				table.setModel(model);
				model.setColumnIdentifiers(headers);
				scrollPane.setViewportView(table);
				table.setAutoCreateRowSorter(true);
				table.getTableHeader().setReorderingAllowed(false);
				scrollPane.setViewportView(table);
				loadContrato();
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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

	private void loadContrato() {

			model.setRowCount(0);
			raw = new Object[table.getColumnCount()];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			ArrayList<Contrato> contra = Altice.getInstance().buscarContratoByUser();
			
			for(Contrato aux : contra) {
				raw[0] = aux.getElTitular().getCedula();
				raw[1] = aux.getMisServicios().size();
				raw[2] = aux.getHistorialDePagos().size();
				raw[3] = aux.getFechaFirma().format(formatter);
				model.addRow(raw);
				
				
			}
	
	}

}

