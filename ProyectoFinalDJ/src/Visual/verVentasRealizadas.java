package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Altice;
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

	public verVentasRealizadas() {
		setTitle("Altice - Mis Ventas Realizadas");
		setModal(true);
		setSize(600, 400); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("RESUMEN DE CONTRATOS CERRADOS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				model = new DefaultTableModel() {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; 
					}
				};

				String headers[] = { "Cédula Cliente", "Nombre Cliente", "Cant. Servicios", "Fecha de Firma"};
				model.setColumnIdentifiers(headers);

				table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				table.getTableHeader().setReorderingAllowed(false);
				scrollPane.setViewportView(table);

				loadContrato();
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCerrar);
			}
		}
	}

	private void loadContrato() {
		model.setRowCount(0);
		raw = new Object[model.getColumnCount()];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		ArrayList<Contrato> contra = Altice.getInstance().buscarContratoByUser();

		for(Contrato aux : contra) {
			if (aux != null && aux.getElTitular() != null) {
				raw[0] = aux.getElTitular().getCedula();
				raw[1] = aux.getElTitular().getNombreCliente() + " " + aux.getElTitular().getApellidoCliente();
				raw[2] = aux.getMisServicios().size();
				raw[3] = aux.getFechaFirma().format(formatter);
				model.addRow(raw);
			}
		}
	}
}
