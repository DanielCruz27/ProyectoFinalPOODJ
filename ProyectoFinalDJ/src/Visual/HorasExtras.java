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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import Logico.Altice;
import Logico.Personal;
import Logico.Tecnico;

public class HorasExtras extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JSpinner spnHoras;
	private Tecnico tecnicoLogueado;

	public static void main(String[] args) {
		try {
			HorasExtras dialog = new HorasExtras();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HorasExtras() {
		setTitle("Altice - Registro de Horas Extras");
		setModal(true);
		setResizable(false);
		setSize(400, 250);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 400, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("REGISTRAR HORAS EXTRAS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- CUERPO ---
		JPanel panelRegistro = new JPanel();
		panelRegistro.setBackground(Color.WHITE);
		panelRegistro.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Registro ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 11), new Color(0, 102, 204)));
		panelRegistro.setBounds(25, 60, 335, 100);
		contentPanel.add(panelRegistro);
		panelRegistro.setLayout(null);

		JLabel lblTxt = new JLabel("Cantidad de horas trabajadas hoy:");
		lblTxt.setBounds(20, 40, 200, 14);
		panelRegistro.add(lblTxt);

		// Spinner para las horas (Mínimo 1, Máximo 8, Paso 1)
		spnHoras = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
		spnHoras.setBounds(230, 37, 70, 22);
		panelRegistro.add(spnHoras);

		// --- CARGAR TECNICO ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Tecnico) {
			tecnicoLogueado = (Tecnico) user;
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Registrar");
				// --- CAMBIOS DE COLOR AQUÍ ---
				btnGuardar.setBackground(new Color(0, 153, 51)); // Verde oscuro
				btnGuardar.setForeground(Color.WHITE);          // Texto blanco
				btnGuardar.setFocusPainted(false);             // Quita el cuadrito feo al hacer click
				// ------------------------------
				btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
				btnGuardar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						registrarHoras();
					}
				});
				buttonPane.add(btnGuardar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCancelar);
			}
		}
	}

	private void registrarHoras() {
		try {
			if (tecnicoLogueado == null) {
				throw new Exception("No hay un técnico autenticado.");
			}

			int horasNuevas = (int) spnHoras.getValue();
			
			// Sumamos a las horas que ya tiene el técnico
			int horasActuales = tecnicoLogueado.getHorasExtrasTrabajadas();
			tecnicoLogueado.setHorasExtrasTrabajadas(horasActuales + horasNuevas);
			
			JOptionPane.showMessageDialog(this, 
				"Se han registrado " + horasNuevas + " horas extras correctamente.\nTotal acumulado: " + tecnicoLogueado.getHorasExtrasTrabajadas() + " h", 
				"Éxito", JOptionPane.INFORMATION_MESSAGE);
			
			dispose();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
