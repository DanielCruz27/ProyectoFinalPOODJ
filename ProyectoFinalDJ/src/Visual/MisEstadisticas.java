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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import Logico.Altice;
import Logico.Tecnico;
import Logico.Ticket;

public class MisEstadisticas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblInstalacionesVal, lblAveriasVal, lblBonoVal, lblHorasExtrasVal;
	private Tecnico tecnicoLogueado;

	public MisEstadisticas() {
		setTitle("Altice - Mis Estadísticas y Horas");
		setModal(true);
		setSize(450, 400); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 450, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("RESUMEN DE RENDIMIENTO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Mis Logros y Tiempo ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), new Color(0, 102, 204)));
		panelInfo.setBounds(30, 60, 375, 230); 
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		JLabel lbl1 = new JLabel("Instalaciones:"); 
		lbl1.setBounds(25, 35, 150, 20);
		panelInfo.add(lbl1);
		lblInstalacionesVal = new JLabel("0"); lblInstalacionesVal.setBounds(250, 35, 100, 20); 
		lblInstalacionesVal.setHorizontalAlignment(SwingConstants.RIGHT); 
		panelInfo.add(lblInstalacionesVal);

		JLabel lbl2 = new JLabel("Averías:"); 
		lbl2.setBounds(25, 70, 150, 20); panelInfo.add(lbl2);
		lblAveriasVal = new JLabel("0");
		lblAveriasVal.setBounds(250, 70, 100, 20);
		lblAveriasVal.setHorizontalAlignment(SwingConstants.RIGHT);
		panelInfo.add(lblAveriasVal);

		JLabel lblHoras = new JLabel("Horas Extras:");
		lblHoras.setBounds(25, 105, 150, 20); 
		panelInfo.add(lblHoras);
		lblHorasExtrasVal = new JLabel("0 h"); 
		lblHorasExtrasVal.setBounds(250, 105, 100, 20);
		lblHorasExtrasVal.setHorizontalAlignment(SwingConstants.RIGHT);
		panelInfo.add(lblHorasExtrasVal);

		JLabel lbl3 = new JLabel("BONO ACUMULADO:"); 
		lbl3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl3.setForeground(new Color(0, 153, 51)); 
		lbl3.setBounds(25, 165, 150, 25); 
		panelInfo.add(lbl3);

		lblBonoVal = new JLabel("RD$ 0.00"); 
		lblBonoVal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBonoVal.setForeground(new Color(0, 153, 51)); 
		lblBonoVal.setBounds(200, 165, 150, 25);
		lblBonoVal.setHorizontalAlignment(SwingConstants.RIGHT); 
		panelInfo.add(lblBonoVal);

		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Tecnico) {
			tecnicoLogueado = (Tecnico) user;
			calcularData();
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Cerrar");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(okButton);
	}

	private void calcularData() {
		int inst = 0;
		int averia = 0;

		try {
			if (tecnicoLogueado == null) {
				throw new Exception("No se pudo identificar al técnico logueado.");
			}

			ArrayList<Ticket> lista = Altice.getInstance().getListaTickets();
			if (lista == null) {
				throw new Exception("La base de datos de tickets no está disponible.");
			}

			for (Ticket t : lista) {
				if (t != null && t.getEstado() == 1 && t.getTecnicoAsignado() != null) {
					if (t.getTecnicoAsignado().getIdEmpleado().equalsIgnoreCase(tecnicoLogueado.getIdEmpleado())) {
						if (t.getAreaAtencion().equalsIgnoreCase("Instalacion")) {
							inst++;
						} else {
							averia++;
						}
					}
				}
			}

			lblInstalacionesVal.setText(String.valueOf(inst));
			lblAveriasVal.setText(String.valueOf(averia));

			int horas = tecnicoLogueado.getHorasExtrasTrabajadas(); 
			lblHorasExtrasVal.setText(horas + " h");


			double bonoTickets = (inst + averia) * 250;
			lblBonoVal.setText("RD$ " + bonoTickets);

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Error: Información de técnico incompleta.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Aviso: " + e.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
		}
	}
}