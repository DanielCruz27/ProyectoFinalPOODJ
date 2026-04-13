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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Logico.Altice;
import Logico.Cliente;

public class HacerValoracion extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> cbxEstrellas;
	private JTextArea txtComentario;
	private Cliente clienteLogueado;

	public HacerValoracion() {
		setTitle("Altice - Valorar mi Experiencia");
		setModal(true);
		setResizable(false);
		setSize(450, 380);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER AZUL ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 450, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("TU OPINIÓN NOS IMPORTA");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- CUERPO ---
		JLabel lblPregunta = new JLabel("¿Cómo calificaría nuestro servicio?");
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPregunta.setBounds(25, 60, 385, 20);
		contentPanel.add(lblPregunta);

		// Combo de Estrellas (Usando emojis para que se vea nítido)
		String[] estrellas = {
			"Seleccionar...", 
			"⭐ (Muy Malo)", 
			"⭐⭐ (Regular)", 
			"⭐⭐⭐ (Bueno)", 
			"⭐⭐⭐⭐ (Muy Bueno)", 
			"⭐⭐⭐⭐⭐ (Excelente)"
		};
		cbxEstrellas = new JComboBox<>(estrellas);
		cbxEstrellas.setBounds(100, 90, 235, 30);
		contentPanel.add(cbxEstrellas);

		JLabel lblComentario = new JLabel("Cuéntanos más (opcional):");
		lblComentario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblComentario.setBounds(40, 140, 200, 14);
		contentPanel.add(lblComentario);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 160, 355, 100);
		contentPanel.add(scrollPane);

		txtComentario = new JTextArea();
		txtComentario.setLineWrap(true);
		txtComentario.setWrapStyleWord(true);
		txtComentario.setBorder(new LineBorder(Color.LIGHT_GRAY));
		scrollPane.setViewportView(txtComentario);

		// Cargar usuario
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnEnviar = new JButton("Enviar Valoración");
				btnEnviar.setBackground(new Color(0, 153, 51));
				btnEnviar.setForeground(Color.WHITE);
				btnEnviar.setFont(new Font("Tahoma", Font.BOLD, 11));
				btnEnviar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						enviarFeedback();
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

	private void enviarFeedback() {
		if (cbxEstrellas.getSelectedIndex() <= 0) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione una puntuación.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Aquí podrías guardar esto en una lista de Valoraciones en Altice si quieres reportes después
		String puntuacion = cbxEstrellas.getSelectedItem().toString();
		String comentario = txtComentario.getText().trim();

		// Simulación de guardado
		System.out.println("Nueva valoración de: " + clienteLogueado.getNombreCliente());
		System.out.println("Puntuación: " + puntuacion);
		System.out.println("Comentario: " + (comentario.isEmpty() ? "Sin comentario" : comentario));

		JOptionPane.showMessageDialog(this, 
			"¡Gracias por tu valoración!\nTu feedback nos ayuda a mejorar día a día.", 
			"Valoración Recibida", JOptionPane.INFORMATION_MESSAGE);
		
		dispose();
	}
}
