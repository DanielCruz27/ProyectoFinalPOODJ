package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

import Logico.Altice;
import Logico.Cliente;
import Logico.Contrato;
import Logico.Servicio;
import Logico.PlanHogar;
import Logico.PlanMovil;

public class MiContrato extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableServicios;
	private DefaultTableModel model;
	private JLabel lblFechaFirma;
	private JLabel lblVendedor;
	private Cliente clienteLogueado;

	public MiContrato() {
		setTitle("Altice - Resumen de mi Contrato");
		setModal(true);
		setSize(600, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 600, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("DETALLES DE MI CONTRATO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- INFORMACIÓN GENERAL ---
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Datos Generales ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 11), new Color(0, 102, 204)));
		panelInfo.setBounds(20, 55, 545, 80);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		JLabel lbl1 = new JLabel("Fecha de Firma:");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl1.setBounds(20, 25, 100, 14);
		panelInfo.add(lbl1);

		lblFechaFirma = new JLabel("---");
		lblFechaFirma.setBounds(130, 25, 150, 14);
		panelInfo.add(lblFechaFirma);

		JLabel lbl2 = new JLabel("Vendedor:");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl2.setBounds(20, 50, 100, 14);
		panelInfo.add(lbl2);

		lblVendedor = new JLabel("---");
		lblVendedor.setBounds(130, 50, 250, 14);
		panelInfo.add(lblVendedor);

		// --- TABLA DE SERVICIOS ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 150, 545, 200);
		contentPanel.add(scrollPane);

		String[] columnas = {"Servicio", "Tipo", "Precio Base", "Número Asignado"};
		model = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		tableServicios = new JTable(model);
		scrollPane.setViewportView(tableServicios);

		// --- CARGAR DATOS ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			cargarDatosContrato();
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

	private void cargarDatosContrato() {
		try {
			Contrato contrato = clienteLogueado.getMiContrato();
			
			if (contrato != null) {
				// Cargar labels
				lblFechaFirma.setText(contrato.getFechaFirma().toString());
				if (contrato.getVendedor() != null) {
					lblVendedor.setText(contrato.getVendedor().getNombre() + " " + contrato.getVendedor().getApellido());
				}
				
				// Cargar Tabla
				model.setRowCount(0);
				ArrayList<Servicio> misServicios = contrato.getMisServicios();
				
				for (Servicio s : misServicios) {
					String tipo = "";
					String numero = "N/A";
					
					if (s instanceof PlanHogar) {
						tipo = "Hogar";
						numero = ((PlanHogar) s).getNumeroTelefonico();
					} else if (s instanceof PlanMovil) {
						tipo = "Móvil";
						numero = ((PlanMovil) s).getNumeroTelefonico();
					}
					
					model.addRow(new Object[] {
						s.getNombreServicio(),
						tipo,
						"RD$ " + s.getPrecioBase(),
						numero
					});
				}
			} else {
				lblVendedor.setText("Sin contrato activo todavía.");
			}
		} catch (Exception e) {
			System.out.println("Error al cargar contrato: " + e.getMessage());
		}
	}
}
