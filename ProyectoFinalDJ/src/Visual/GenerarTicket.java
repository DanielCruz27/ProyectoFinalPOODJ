package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Logico.Altice;
import Logico.Cliente;

public class GenerarTicket extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> cbxProblema;
	private JTextArea txtDescripcion;
	private Cliente clienteLogueado;

	public GenerarTicket() {
		setTitle("Altice - Reportar Avería o Solicitud");
		setModal(true);
		setResizable(false);
		setSize(480, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 480, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("GENERAR NUEVO TICKET DE SOPORTE");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- FORMULARIO ---
		JLabel lbl1 = new JLabel("¿Qué problema presenta?");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl1.setBounds(30, 60, 200, 14);
		contentPanel.add(lbl1);

		String[] problemas = {
			"<< Seleccione >>", 
			"Internet lento", 
			"Cable roto", 
			"Poste inclinado", 
			"Instalación de equipo"
		};
		cbxProblema = new JComboBox<>(problemas);
		cbxProblema.setBounds(30, 80, 400, 25);
		contentPanel.add(cbxProblema);

		JLabel lbl2 = new JLabel("Descripción adicional (Opcional):");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl2.setBounds(30, 125, 250, 14);
		contentPanel.add(lbl2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 145, 400, 100);
		contentPanel.add(scrollPane);

		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		scrollPane.setViewportView(txtDescripcion);

		// --- INFO DE ZONA (Visual) ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			JLabel lblZona = new JLabel("Su reporte será asignado a la zona: " + clienteLogueado.getZonaVivienda());
			lblZona.setForeground(new Color(0, 102, 204));
			lblZona.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblZona.setBounds(30, 260, 400, 14);
			contentPanel.add(lblZona);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnEnviar = new JButton("Enviar Reporte");
				btnEnviar.setBackground(new Color(0, 153, 51));
				btnEnviar.setForeground(Color.WHITE);
				btnEnviar.setFont(new Font("Tahoma", Font.BOLD, 11));
				// Estilo tradicional sin lambdas
				btnEnviar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						enviarTicket();
					}
				});
				buttonPane.add(btnEnviar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCancelar);
			}
		}
	}

	private void enviarTicket() {
		if (cbxProblema.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione el tipo de problema.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String problemaSeleccionado = cbxProblema.getSelectedItem().toString();
		
		// Llamamos al método de Altice que hace el ruteo por departamentos
		Altice.getInstance().generarTicket(clienteLogueado, problemaSeleccionado);
		
		JOptionPane.showMessageDialog(this, 
			"Ticket generado con éxito.\nUn técnico de la zona " + clienteLogueado.getZonaVivienda() + " revisará su caso.", 
			"Ticket Registrado", JOptionPane.INFORMATION_MESSAGE);
		
		dispose();
	}
}

