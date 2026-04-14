package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Logico.Altice;
import Logico.Tecnico;
import Logico.Ticket;

public class OrdenesServicio extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableTickets;
	private DefaultTableModel model;
	private Tecnico tecnicoLogueado;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			OrdenesServicio dialog = new OrdenesServicio();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OrdenesServicio() {
		setTitle("Altice - Mis Órdenes de Servicio");
		setModal(true);
		setSize(750, 450);
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

		JLabel lblTitulo = new JLabel("TICKETS ASIGNADOS POR ZONA Y ESPECIALIDAD");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 695, 280);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID Ticket", "Cliente", "Zona", "Área Atención", "Fecha Generado"};
		model = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		tableTickets = new JTable(model);
		tableTickets.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(tableTickets);

		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Tecnico) {
			tecnicoLogueado = (Tecnico) user;
			cargarTickets();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnFinalizar = new JButton("Finalizar Trabajo");
			btnFinalizar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
			btnFinalizar.setBackground(new Color(0, 153, 51));
			btnFinalizar.setForeground(Color.WHITE);
			btnFinalizar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					finalizarTicket();
				}
			});
			buttonPane.add(btnFinalizar);

			JButton btnCerrar = new JButton("Cerrar");
			btnCerrar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(btnCerrar);
		}
	}

	private void cargarTickets() {
		model.setRowCount(0);

		if (tecnicoLogueado == null) {
			return; 
		}

		ArrayList<Ticket> tickets = Altice.getInstance().getListaTickets();

		if (tickets != null) {
			for (Ticket t : tickets) {
				if (t != null && t.getElCliente() != null) {

					if (t.getEstado() == 0 && 
							t.getElCliente().getZonaVivienda().equalsIgnoreCase(tecnicoLogueado.getZonAsignada()) &&
							t.getAreaAtencion().equalsIgnoreCase(tecnicoLogueado.getTipoTecnico())) {

						model.addRow(new Object[] {
								t.getIdTicket(),
								t.getElCliente().getNombreCliente() + " " + t.getElCliente().getApellidoCliente(),
								t.getElCliente().getZonaVivienda(),
								t.getAreaAtencion(),
								t.getHoraGeneracion().toString()
						});
					}
				}
			}
		}
	}
	private void finalizarTicket() {
		int fila = tableTickets.getSelectedRow();
		if (fila >= 0) {
			String idTicket = tableTickets.getValueAt(fila, 0).toString();
			int confirm = JOptionPane.showConfirmDialog(this, 
					"¿Desea marcar el ticket " + idTicket + " como finalizado?", 
					"Confirmación de Trabajo", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				Ticket t = Altice.getInstance().buscarTicketById(idTicket);
				if (t != null) {
					t.setEstado(1); 

					t.setTecnicoAsignado(tecnicoLogueado); 

					JOptionPane.showMessageDialog(this, "Trabajo completado. El ticket ya no aparecerá en su lista.");
					cargarTickets(); 
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione un ticket de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
}