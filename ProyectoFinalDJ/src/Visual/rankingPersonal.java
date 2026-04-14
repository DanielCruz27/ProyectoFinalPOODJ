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

		DefaultCategoryDataset dataset = Altice.getInstance().obtenerDatosRankingVentas();

		JFreeChart chart = ChartFactory.createBarChart(
				"RENDIMIENTO DEL PERSONAL COMERCIAL", 
				"Vendedores",                         
				"Cantidad de Contratos",              
				dataset,                           
				PlotOrientation.VERTICAL, 
				true,                                 
				true,                                
				false                              
				);

		chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(0, 102, 204));
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		rangeAxis.setLowerBound(0); 
		if(dataset.getRowCount() == 0 || dataset.getColumnCount() == 0) {
			rangeAxis.setUpperBound(10);
		}
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true); 
		contentPanel.add(chartPanel, BorderLayout.CENTER);

	}

}
