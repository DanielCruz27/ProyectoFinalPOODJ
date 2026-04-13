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
import Logico.PlanHogar;
import Logico.PlanMovil;
import Logico.Servicio;

public class ConsumoMin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Cliente clienteLogueado;

	public ConsumoMin() {
		setTitle("Altice - Consumo General");
		setModal(true);
		setSize(500, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 500, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("ESTADO DE MINUTOS DISPONIBLES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- TABLA ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 60, 435, 230);
		contentPanel.add(scrollPane);

		String[] headers = {"Línea / Contrato", "Servicio", "Balance Minutos"};
		model = new DefaultTableModel(null, headers) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		table = new JTable(model);
		scrollPane.setViewportView(table);

		// --- CARGAR DATOS ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			// Simulamos consumo para que la data cambie
			Altice.getInstance().simularConsumoAleatorio(clienteLogueado);
			cargarTabla();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(245, 245, 245));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar");
				// Clase anónima tradicional (Sin lambdas)
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
		if (clienteLogueado != null && clienteLogueado.getMiContrato() != null) {
			for (Servicio s : clienteLogueado.getMiContrato().getMisServicios()) {
				String num = "";
				int mins = 0;

				if (s instanceof PlanMovil) {
					num = ((PlanMovil) s).getNumeroTelefonico();
					mins = ((PlanMovil) s).getMinutosIncluidos();
				} else if (s instanceof PlanHogar) {
					num = ((PlanHogar) s).getNumeroTelefonico();
					mins = ((PlanHogar) s).getMinutosTelefonoHogar();
				}

				model.addRow(new Object[]{num, s.getNombreServicio(), mins + " min."});
			}
		}
	}
}
