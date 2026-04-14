package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import Logico.Altice;
import java.awt.Toolkit;

public class reporteTicket extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public reporteTicket() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(reporteTicket.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Panel de Tickets");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setModal(true);

		DefaultPieDataset dataset = Altice.getInstance().obtenerDatosTickets();

		JFreeChart chart = ChartFactory.createPieChart(
				"TIPOS DE TICKETS GENERADOS", 
				dataset, 
				true, 
				true, 
				false
				);

		chart.setBackgroundPaint(Color.WHITE);

		ChartPanel panel = new ChartPanel(chart);
		getContentPane().add(panel, BorderLayout.CENTER);
	}
}