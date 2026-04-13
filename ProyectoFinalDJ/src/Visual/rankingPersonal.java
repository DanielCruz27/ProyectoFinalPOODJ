package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import Logico.Altice;

public class rankingPersonal extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			rankingPersonal dialog = new rankingPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public rankingPersonal() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setTitle("Altice - Ranking de Ventas por Empleado");
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BorderLayout(0, 0));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // 1. Obtener los datos desde la lógica
        DefaultCategoryDataset dataset = Altice.getInstance().obtenerDatosRankingVentas();

        // 2. Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
            "RENDIMIENTO DEL PERSONAL COMERCIAL", // Título
            "Vendedores",                         // Eje X
            "Cantidad de Contratos",              // Eje Y
            dataset,                              // Los datos
            PlotOrientation.VERTICAL, 
            true,                                 // Leyenda
            true,                                 // Tooltips
            false                                 // URLs
        );

        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(0, 102, 204));
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // 1. Obligamos a que solo muestre números enteros (1, 2, 3...)
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // 2. Definimos un rango mínimo (por ejemplo, que siempre llegue a 10 como base)
        rangeAxis.setLowerBound(0); 
        if(dataset.getRowCount() == 0 || dataset.getColumnCount() == 0) {
            rangeAxis.setUpperBound(10);
        }
        // 4. Crear el panel del gráfico y agregarlo al JDialog
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true); // Permite hacer zoom con el mouse
        contentPanel.add(chartPanel, BorderLayout.CENTER);
		
	}

}
