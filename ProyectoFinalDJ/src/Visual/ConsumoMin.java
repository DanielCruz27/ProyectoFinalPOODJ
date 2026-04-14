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
import java.awt.Toolkit;

public class ConsumoMin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Cliente clienteLogueado;

	public ConsumoMin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConsumoMin.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Consumo de Minutos");
		setModal(true);
		setSize(500, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 500, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("ESTADO DE MINUTOS DISPONIBLES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

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

		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
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
