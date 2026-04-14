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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Logico.Altice;
import Logico.Personal;
import java.awt.Toolkit;

public class MisComisiones extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblCantVentas, lblMontoComision;
	private Personal comercialLogueado;

	public MisComisiones() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MisComisiones.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Mis comisiones");
		setModal(true);
		setResizable(false);
		setSize(450, 320);
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

		JLabel lblTitulo = new JLabel("PANEL DE INCENTIVOS COMERCIALES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JPanel panelData = new JPanel();
		panelData.setBackground(Color.WHITE);
		panelData.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Resumen de Ganancias ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 11), new Color(0, 102, 204)));
		panelData.setBounds(25, 60, 385, 150);
		contentPanel.add(panelData);
		panelData.setLayout(null);

		JLabel lbl1 = new JLabel("Contratos Cerrados:");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl1.setBounds(30, 40, 150, 20);
		panelData.add(lbl1);

		lblCantVentas = new JLabel("0");
		lblCantVentas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantVentas.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCantVentas.setBounds(230, 40, 120, 20);
		panelData.add(lblCantVentas);

		JLabel lbl2 = new JLabel("COMISIÓN ACUMULADA:");
		lbl2.setForeground(new Color(0, 102, 204));
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl2.setBounds(30, 90, 180, 25);
		panelData.add(lbl2);

		lblMontoComision = new JLabel("RD$ 0.00");
		lblMontoComision.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoComision.setForeground(new Color(0, 153, 51));
		lblMontoComision.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMontoComision.setBounds(210, 90, 140, 25);
		panelData.add(lblMontoComision);

		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Personal) {
			comercialLogueado = (Personal) user;
			calcularComisiones();
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

	private void calcularComisiones() {
		try {
			if (comercialLogueado == null) {
				throw new Exception("Vendedor no identificado.");
			}

			double monto = Altice.getInstance().calcularComisionesPorVendedor(comercialLogueado.getIdEmpleado());
			int cantVentas = Altice.getInstance().buscarContratoByUser().size();

			lblCantVentas.setText(String.valueOf(cantVentas));
			lblMontoComision.setText("RD$ " + String.format("%.2f", monto));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}
}
