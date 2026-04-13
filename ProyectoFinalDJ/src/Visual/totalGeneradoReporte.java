package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import Logico.Altice;

public class totalGeneradoReporte extends JDialog {
	public static void main(String[] args) {

		try {

		totalGeneradoReporte dialog = new totalGeneradoReporte();

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		dialog.setVisible(true);

		} catch (Exception e) {

		e.printStackTrace();

		}

		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public totalGeneradoReporte() {
		// Configuración del JDialog
		setTitle("Altice - Reporte Financiero de Ingresos");
		setModal(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// 1. Obtener los datos desde la lógica (Altice.java)
		DefaultPieDataset dataset = Altice.getInstance().obtenerDatosFinanzasPie();

		// 2. Crear el gráfico de pastel (Pie Chart)
		JFreeChart chart = ChartFactory.createPieChart(
			"DISTRIBUCIÓN DE INGRESOS POR TIPO DE SERVICIO", // Título
			dataset,             // Datos
			true,                // Incluir leyenda
			true,                // Tooltips
			false                // URLs
		);

		// --- 3. PERSONALIZACIÓN VISUAL ---
		
		// Fondo del gráfico
		chart.setBackgroundPaint(Color.WHITE);
		chart.getTitle().setFont(new Font("Arial", Font.BOLD, 20));

		// Configuración del área del pastel (Plot)
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false); // Quitar borde exterior
		
		// Formato de la etiqueta: {0} es el nombre, {1} es el valor, {2} es el porcentaje
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: RD$ {1} ({2})"));
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setLabelBackgroundPaint(new Color(245, 245, 245)); // Fondo gris suave para etiquetas

		// Asignar colores específicos a las tajadas
		plot.setSectionPaint("Planes Móviles", new Color(0, 102, 204)); // Azul Altice
		plot.setSectionPaint("Planes Hogar", new Color(128, 128, 128));   // Gris corporativo o Naranja

		// Separar un poco las tajadas (opcional, da un efecto 3D moderno)
		plot.setExplodePercent("Planes Móviles", 0.02);
		plot.setExplodePercent("Planes Hogar", 0.02);

		// 4. Crear el panel del gráfico y agregarlo al JDialog
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.WHITE);
		chartPanel.setMouseWheelEnabled(true);
		contentPanel.add(chartPanel, BorderLayout.CENTER);
	}
}