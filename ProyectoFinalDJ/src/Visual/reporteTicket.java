package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import Logico.Altice;

public class reporteTicket extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public reporteTicket() {
        setTitle("Altice - Análisis de Reportes y Averías");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setModal(true);

        // 1. Obtener datos
        DefaultPieDataset dataset = Altice.getInstance().obtenerDatosTickets();

        // 2. Crear gráfico de pastel
        JFreeChart chart = ChartFactory.createPieChart(
            "TIPOS DE TICKETS GENERADOS", 
            dataset, 
            true, 
            true, 
            false
        );

        chart.setBackgroundPaint(Color.WHITE);
        
        // 3. Panel
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().add(panel, BorderLayout.CENTER);
    }
}