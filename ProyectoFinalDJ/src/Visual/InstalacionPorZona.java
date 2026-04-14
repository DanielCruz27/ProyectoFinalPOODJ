package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import Logico.Altice;

public class InstalacionPorZona extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Color azulAltice = new Color(0, 102, 204);

	public InstalacionPorZona() {
		setTitle("Altice - Reporte Técnico por Zona");
		setModal(true);
		setSize(750, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(azulAltice);
		contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		DefaultCategoryDataset dataset = Altice.getInstance().obtenerDatasetInstalacionesPorZona();

		JFreeChart chart = ChartFactory.createBarChart(
				"TRABAJOS POR ZONAS", 
				"Regiones Geográficas", 
				"Cantidad de Trabajos", 
				dataset, 
				PlotOrientation.VERTICAL, 
				false, 
				true, 
				false
				);

		chart.setBackgroundPaint(Color.WHITE);
		chart.getTitle().setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));

		chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);
		chart.getCategoryPlot().setRangeGridlinePaint(Color.GRAY);
		chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(0, 153, 51)); 

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(new LineBorder(Color.WHITE, 2, true));
		contentPanel.add(chartPanel, BorderLayout.CENTER);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar ");
				btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 11));
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCerrar);
			}
		}
	}
}
