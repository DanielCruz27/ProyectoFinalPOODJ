package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Logico.Altice;
import Logico.PlanHogar;
import Logico.PlanMovil;
import Logico.Servicio;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegRecarga extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNumeroTelefonico;
	private JSpinner spnMinutos;

	public static void main(String[] args) {
		try {
			RegRecarga dialog = new RegRecarga();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegRecarga() {
		setTitle("Altice - Recarga de Saldo");
		setResizable(false);
		setModal(true);
		setSize(300, 250);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- ESTILO ALTICE ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 300, 35);
		contentPanel.add(panelHeader);
		
		JLabel lblT = new JLabel("RECARGA ");
		lblT.setForeground(Color.WHITE);
		lblT.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		panelHeader.add(lblT);

		JLabel lblNum = new JLabel("Número Telefónico:");
		lblNum.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblNum.setBounds(30, 50, 200, 14);
		contentPanel.add(lblNum);

		txtNumeroTelefonico = new JTextField();
		txtNumeroTelefonico.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtNumeroTelefonico.setBorder(new LineBorder(new Color(0, 102, 204)));
		txtNumeroTelefonico.setBounds(30, 70, 220, 25);
		contentPanel.add(txtNumeroTelefonico);
		
		JLabel lblMins = new JLabel("Monto / Minutos a recargar:");
		lblMins.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblMins.setBounds(30, 110, 200, 14);
		contentPanel.add(lblMins);

		spnMinutos = new JSpinner();
		spnMinutos.setModel(new SpinnerNumberModel(50, 5, 5000, 10));
		spnMinutos.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		spnMinutos.setBounds(30, 130, 100, 25);
		contentPanel.add(spnMinutos);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRecarga = new JButton("Confirmar Recarga");
				btnRecarga.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
				btnRecarga.setBackground(new Color(0, 153, 51));
				btnRecarga.setForeground(Color.WHITE);
				btnRecarga.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String numero = txtNumeroTelefonico.getText().trim();
						int cantidad = (Integer) spnMinutos.getValue();
						
						if(numero.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						Servicio service = Altice.getInstance().realizarRecarga(numero, cantidad);
						
						if(service != null) {
							String mensaje = "";
							if(service instanceof PlanHogar) {
								mensaje = "Recarga exitosa.\nNuevo saldo: " + ((PlanHogar)service).getMinutosTelefonoHogar() + " minutos.";
							} else if(service instanceof PlanMovil) {
								mensaje = "Recarga exitosa.\nNuevo saldo: " + ((PlanMovil)service).getMinutosIncluidos() + " minutos.";
							}
							JOptionPane.showMessageDialog(null, mensaje, "Altice Confirmación", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Número no encontrado en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonPane.add(btnRecarga);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}