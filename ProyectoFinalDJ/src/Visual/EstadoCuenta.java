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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Logico.Altice;
import Logico.Cliente;

public class EstadoCuenta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNombreVal, lblZonaVal, lblPuntosVal, lblAtrasosVal, lblDeudaVal, lblEstadoVal;
	private Cliente clienteLogueado;

	public static void main(String[] args) {
		try {
			EstadoCuenta dialog = new EstadoCuenta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EstadoCuenta() {
		setTitle("Altice - Mi Estado de Cuenta");
		setModal(true);
		setResizable(false);
		setSize(500, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- HEADER AZUL ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 500, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("ESTADO DE CUENTA");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- PANEL DE INFORMACIÓN ---
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Mis Datos ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 11), new Color(0, 102, 204)));
		panelInfo.setBounds(25, 60, 435, 300);
		contentPanel.add(panelInfo);
		panelInfo.setLayout(null);

		// Etiquetas y Valores
		String[] labels = {"Nombre Completo:", "Zona Residencial:", "Puntos Altice:", "Facturas Atrasadas:", "Monto Adeudado:", "Estado del Servicio:"};
		int yPos = 35;

		// Inicialización de labels de valor
		lblNombreVal = new JLabel("---"); lblNombreVal.setBounds(200, 35, 220, 20); panelInfo.add(lblNombreVal);
		lblZonaVal = new JLabel("---"); lblZonaVal.setBounds(200, 75, 220, 20); panelInfo.add(lblZonaVal);
		lblPuntosVal = new JLabel("---"); lblPuntosVal.setBounds(200, 115, 220, 20); panelInfo.add(lblPuntosVal);
		lblAtrasosVal = new JLabel("---"); lblAtrasosVal.setBounds(200, 155, 220, 20); panelInfo.add(lblAtrasosVal);
		lblDeudaVal = new JLabel("RD$ 0.00"); lblDeudaVal.setFont(new Font("Tahoma", Font.BOLD, 13)); lblDeudaVal.setBounds(200, 195, 220, 20); panelInfo.add(lblDeudaVal);
		lblEstadoVal = new JLabel("---"); lblEstadoVal.setFont(new Font("Tahoma", Font.BOLD, 13)); lblEstadoVal.setBounds(200, 240, 220, 20); panelInfo.add(lblEstadoVal);

		for (String text : labels) {
			JLabel lbl = new JLabel(text);
			lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbl.setBounds(30, yPos, 150, 20);
			panelInfo.add(lbl);
			yPos += 40;
		}

		// --- CARGAR DATOS ---
		Object user = Altice.getInstance().getUsuarioLogueado();
		if (user instanceof Cliente) {
			clienteLogueado = (Cliente) user;
			cargarEstado();
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCerrar);
			}
		}
	}

	private void cargarEstado() {
	    try {
	        if (clienteLogueado != null) {
	            // 1. Llenar los datos básicos que faltaban
	            lblNombreVal.setText(clienteLogueado.getNombreCliente() + " " + clienteLogueado.getApellidoCliente());
	            lblZonaVal.setText(clienteLogueado.getZonaVivienda());
	            lblPuntosVal.setText(String.valueOf(clienteLogueado.getPuntosAcumulados()));
	            
	            // 2. Cálculos financieros desde Altice
	            int atrasos = Altice.getInstance().calcularAtrasosReales(clienteLogueado);
	            float deuda = Altice.getInstance().calcularMontoDeudaReal(clienteLogueado);
	            
	            lblAtrasosVal.setText(atrasos + " mes(es)");
	            lblDeudaVal.setText("RD$ " + String.format("%.2f", deuda));
	            
	            // 3. Manejo del color de la deuda
	            if (deuda > 0) {
	                lblDeudaVal.setForeground(Color.RED);
	            } else {
	                lblDeudaVal.setForeground(new Color(0, 153, 51));
	            }

	            // 4. Lógica del Estado del Cliente (La que arreglamos antes)
	            if (atrasos > 2 || !clienteLogueado.isEstadoCliente()) {
	                lblEstadoVal.setText("CLIENTE SUSPENDIDO");
	                lblEstadoVal.setForeground(Color.RED);
	            } else {
	                lblEstadoVal.setText("CLIENTE ACTIVO");
	                lblEstadoVal.setForeground(new Color(0, 153, 51));
	            }
	        } else {
	            // Por si acaso no hay nadie logueado, para que no se vea vacío
	            System.out.println("DEBUG: No hay cliente logueado en la sesión.");
	        }
	    } catch (Exception e) {
	        System.err.println("Error en cargarEstado: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
