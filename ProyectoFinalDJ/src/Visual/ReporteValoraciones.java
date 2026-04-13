package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import Logico.Altice;

public class ReporteValoraciones extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReporteValoraciones() {
        setTitle("Altice - Calificación del Servicio");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setModal(true);

        DefaultCategoryDataset dataset = Altice.getInstance().obtenerDatosValoraciones();

        JFreeChart chart = ChartFactory.createBarChart(
            "SATISFACCIÓN DEL CLIENTE", // Título
            "Puntuación (Estrellas)",    // Eje X
            "Cantidad de Opiniones",     // Eje Y
            dataset, 
            PlotOrientation.VERTICAL, 
            false, // No necesitamos leyenda (solo hay una serie)
            true, 
            false
        );

        // 3. Un poco de color
        chart.setBackgroundPaint(Color.WHITE);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 204, 0)); // Color dorado/estrella

        // 4. Mostrarlo
        ChartPanel panel = new ChartPanel(chart);
        getContentPane().add(panel, BorderLayout.CENTER);
    }
}