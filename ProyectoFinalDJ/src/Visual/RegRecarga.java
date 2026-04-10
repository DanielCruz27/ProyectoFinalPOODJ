package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logico.Altice;
import Logico.PlanHogar;
import Logico.PlanMovil;
import Logico.Servicio;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegRecarga extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNumeroTelefonico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegRecarga dialog = new RegRecarga();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegRecarga() {
		setTitle("Recarga");
		setBounds(100, 100, 257, 293);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtNumeroTelefonico = new JTextField();
			txtNumeroTelefonico.setBounds(10, 33, 137, 20);
			contentPanel.add(txtNumeroTelefonico);
			txtNumeroTelefonico.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Numero Telefonico");
			lblNewLabel.setBounds(10, 11, 137, 14);
			contentPanel.add(lblNewLabel);
		}
		
		JSpinner spnMinutos = new JSpinner();
		spnMinutos.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));
		spnMinutos.setBounds(10, 80, 45, 20);
		contentPanel.add(spnMinutos);
		
		JLabel lblMinutosARecarga = new JLabel("Agregar Saldo");
		lblMinutosARecarga.setBounds(10, 65, 137, 14);
		contentPanel.add(lblMinutosARecarga);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRecarga = new JButton("Recargar");
				btnRecarga.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Servicio service = Altice.getInstance().realizarRecarga(txtNumeroTelefonico.getText(), new Integer(spnMinutos.getValue().toString()));
						
						if(service instanceof PlanHogar) {
						JOptionPane.showMessageDialog(null, "Saldo de "+ txtNumeroTelefonico.getText()+"es de RD$" +((PlanHogar)service).getMinutosTelefonoHogar(), getTitle(), ABORT);
						}else if(service instanceof PlanMovil) {
							JOptionPane.showMessageDialog(null, "Saldo de "+ txtNumeroTelefonico.getText()+"es de RD$" +((PlanMovil)service).getMinutosIncluidos(), getTitle(), ABORT);

						}
					}
				});
				btnRecarga.setActionCommand("OK");
				buttonPane.add(btnRecarga);
				getRootPane().setDefaultButton(btnRecarga);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
