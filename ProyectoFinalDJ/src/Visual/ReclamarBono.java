package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Logico.Altice;
import Logico.Tecnico;
import Logico.Ticket;

public class ReclamarBono extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblMontoReclamar;
	private Tecnico tecnicoLogueado;
	private double bonoCalculado = 0;

	public ReclamarBono() {
		setTitle("Altice - Reclamar Bono Acumulado");
		setModal(true);
		setResizable(false);
		setSize(400, 280);
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
		
		JLabel lblTitulo = new JLabel("RECLAMAR BONIFICACIÓN");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- CUERPO ---
		JPanel panelMonto = new JPanel();
		panelMonto.setBackground(new Color(240, 248, 255));
		panelMonto.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Resumen de Pago ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 11), new Color(0, 102, 204)));
		panelMonto.setBounds(35, 60, 315, 110);
		contentPanel.add(panelMonto);
		panelMonto.setLayout(null);

		JLabel lblTxt = new JLabel("Monto disponible para reclamar:");
		lblTxt.setHorizontalAlignment(SwingConstants.CENTER);
		lblTxt.setBounds(10, 25, 295, 14);
		panelMonto.add(lblTxt);

		lblMontoReclamar = new JLabel("RD$ 0.00");
		lblMontoReclamar.setForeground(new Color(0, 153, 51));
		lblMontoReclamar.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblMontoReclamar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMontoReclamar.setBounds(10, 50, 295, 40);
		panelMonto.add(lblMontoReclamar);

		// --- CARGAR DATOS ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Tecnico) {
			tecnicoLogueado = (Tecnico) user;
			calcularBonoActual();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnReclamar = new JButton("Reclamar Bono");
				btnReclamar.setBackground(new Color(0, 153, 51));
				btnReclamar.setForeground(Color.WHITE);
				btnReclamar.setFont(new Font("Tahoma", Font.BOLD, 11));
				btnReclamar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ejecutarReclamo();
					}
				});
				buttonPane.add(btnReclamar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	private void calcularBonoActual() {
		int contadorTickets = 0;
		try {
			ArrayList<Ticket> tickets = Altice.getInstance().getListaTickets();
			for (Ticket t : tickets) {
				// Contamos tickets que: 
				// 1. Estén finalizados (1)
				// 2. Sean del técnico logueado
				// 3. NO hayan sido cobrados todavía (Asumiendo que agregaste el boolean o atributo)
				if (t.getEstado() == 1 && t.getTecnicoAsignado() != null) {
					if (t.getTecnicoAsignado().getIdEmpleado().equalsIgnoreCase(tecnicoLogueado.getIdEmpleado())) {
						contadorTickets++;
					}
				}
			}
			bonoCalculado = contadorTickets * 250.0;
			lblMontoReclamar.setText("RD$ " + bonoCalculado);
			
		} catch (Exception e) {
			lblMontoReclamar.setText("Error");
		}
	}

	private void ejecutarReclamo() {
		if (bonoCalculado <= 0) {
			JOptionPane.showMessageDialog(this, "Usted no tiene bonos acumulados para reclamar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, 
				"¿Desea reclamar RD$ " + bonoCalculado + " ahora mismo?\nEl balance volverá a 0.", 
				"Confirmar Cobro", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				// Aquí está la lógica para "limpiar" el bono
				ArrayList<Ticket> tickets = Altice.getInstance().getListaTickets();
				for (Ticket t : tickets) {
					if (t.getEstado() == 1 && t.getTecnicoAsignado() != null) {
						if (t.getTecnicoAsignado().getIdEmpleado().equalsIgnoreCase(tecnicoLogueado.getIdEmpleado())) {
							// Cambiamos el estado a 2 para indicar "Cobrado/Histórico"
							// O podrías tener un t.setBonoCobrado(true)
							t.setEstado(2); 
						}
					}
				}
				
				JOptionPane.showMessageDialog(this, "¡Felicidades! Bono reclamado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error al procesar el reclamo.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
