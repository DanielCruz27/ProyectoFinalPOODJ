package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import Logico.Altice;
import java.awt.Toolkit;

public class PlanMasContratado extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public PlanMasContratado() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlanMasContratado.class.getResource("/Recursos/LogoAltice.jpg")));
		setTitle("Plan máas Contratado");
		setModal(true);
		setSize(650, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(new Color(0, 102, 204)); 
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		DefaultPieDataset dataset = Altice.getInstance().obtenerDatasetPlanMasContratado();

		JFreeChart chart = ChartFactory.createPieChart(
				"PLANES MÁS CONTRATADOS", 
				dataset, 
				true,
				true, 
				false
				);

		chart.setBackgroundPaint(Color.WHITE);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setLabelFont(new Font("Tahoma", Font.BOLD, 12));
		plot.setOutlineVisible(false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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