package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import Logico.Altice;
import Logico.Cliente;
import java.awt.Toolkit;

public class MetricasCalidad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Color azulAltice = new Color(0, 102, 204);

	public MetricasCalidad() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MetricasCalidad.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Métricas de Calidad");
		setModal(true);
		setSize(750, 550); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(azulAltice); 
		contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 3, 20, 20)); 

		int totalContratos = Altice.getInstance().getListaContratos().size();
		int totalServicios = Altice.getInstance().getCatalogoServicio().size();
		int totalPersonal = Altice.getInstance().getListaEmpleados().size();
		int totalClientes = Altice.getInstance().getListaClientes().size();

		int suspendidos = 0;
		int enDeuda = 0;

		for (Cliente c : Altice.getInstance().getListaClientes()) {
			if (!c.isEstadoCliente()) { 
				suspendidos++;
			}
			if (Altice.getInstance().comprobarSiHayDeuda(c.getIdCliente()).equalsIgnoreCase("Si")) {
				enDeuda++;
			}
		}


		contentPanel.add(crearTarjeta("CONTRATOS", String.valueOf(totalContratos), new Color(0, 180, 255)));
		contentPanel.add(crearTarjeta("SERVICIOS", String.valueOf(totalServicios), new Color(0, 204, 204)));
		contentPanel.add(crearTarjeta("PERSONAL", String.valueOf(totalPersonal), new Color(153, 102, 255)));
		contentPanel.add(crearTarjeta("CLIENTES TOTALES", String.valueOf(totalClientes), new Color(102, 255, 102)));
		contentPanel.add(crearTarjeta("SUSPENDIDOS", String.valueOf(suspendidos), new Color(255, 153, 51)));
		contentPanel.add(crearTarjeta("EN DEUDA", String.valueOf(enDeuda), new Color(255, 80, 80)));

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setBorder(new EmptyBorder(10, 0, 10, 0));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnCerrar = new JButton("Cerrar");
			btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(btnCerrar);
		}
	}

	private JPanel crearTarjeta(String titulo, String valor, Color colorResalte) {
		JPanel card = new JPanel();
		card.setLayout(new BorderLayout());
		card.setBackground(Color.WHITE); 
		card.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setOpaque(true);
		lblTitulo.setBackground(colorResalte);
		lblTitulo.setForeground(Color.BLACK); 
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));

		JLabel lblValor = new JLabel(valor);
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblValor.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
		lblValor.setForeground(colorResalte);

		card.add(lblTitulo, BorderLayout.NORTH);
		card.add(lblValor, BorderLayout.CENTER);

		return card;
	}
}