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
import Logico.Valoracion;

public class ReporteValoraciones extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	public ReporteValoraciones() {
		setTitle("Altice - Listado de Valoraciones de Clientes");
		setModal(true);
		setSize(750, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 750, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("FEEDBACK Y CALIFICACIONES RECIBIDAS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 695, 340);
		contentPanel.add(scrollPane);

		String[] headers = {"ID", "Cliente", "Puntuación", "Comentario", "Fecha"};
		model = new DefaultTableModel(null, headers) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		table = new JTable(model);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(300); 

		scrollPane.setViewportView(table);

		cargarTabla();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(245, 245, 245));
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

	private void cargarTabla() {
		model.setRowCount(0);
		for (Valoracion v : Altice.getInstance().getListaValoraciones()) {

			String estrellasStr = "";
			for(int i = 0; i < v.getCantidadEstrellas(); i++) {
				estrellasStr += "⭐";
			}

			Object[] fila = {
					v.getIdValoracion(),
					v.getElCliente().getNombreCliente() + " " + v.getElCliente().getApellidoCliente(),
					estrellasStr,
					v.getComentario(),
					v.getFecha().toString()
			};
			model.addRow(fila);
		}
	}
}