package Visual;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.*;
import Logico.*;

public class PagarContrato extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblMontoDeuda;
	private JComboBox<MetodoDePago> cbxMetodos; // ComboBox para elegir el método
	private Cliente clienteLogueado;
	private float deudaActual = 0;

	public PagarContrato() {
	    setTitle("Altice - Realizar Pago");
	    setModal(true);
	    setSize(450, 400);
	    setLocationRelativeTo(null);
	    getContentPane().setLayout(new BorderLayout());
	    
	    contentPanel.setBackground(Color.WHITE);
	    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	    getContentPane().add(contentPanel, BorderLayout.CENTER);
	    contentPanel.setLayout(null);

	    // --- HEADER ---
	    JPanel panelHeader = new JPanel();
	    panelHeader.setBackground(new Color(0, 102, 204));
	    panelHeader.setBounds(0, 0, 450, 40);
	    contentPanel.add(panelHeader);
	    
	    JLabel lblTituloHeader = new JLabel("PAGO DE SERVICIOS");
	    lblTituloHeader.setForeground(Color.WHITE);
	    lblTituloHeader.setFont(new Font("Arial", Font.BOLD, 14));
	    panelHeader.add(lblTituloHeader);

	    // --- MONTO ---
	    lblMontoDeuda = new JLabel("RD$ 0.00");
	    lblMontoDeuda.setHorizontalAlignment(SwingConstants.CENTER);
	    lblMontoDeuda.setFont(new Font("Tahoma", Font.BOLD, 26));
	    lblMontoDeuda.setForeground(Color.RED);
	    lblMontoDeuda.setBounds(10, 60, 414, 40);
	    contentPanel.add(lblMontoDeuda);

	    // --- SELECCIÓN DE MÉTODO ---
	    JPanel panelSeleccion = new JPanel();
	    panelSeleccion.setLayout(null);
	    panelSeleccion.setBackground(Color.WHITE);
	    panelSeleccion.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY), " Seleccione su método de pago "));
	    panelSeleccion.setBounds(35, 120, 360, 85);
	    contentPanel.add(panelSeleccion);

	    cbxMetodos = new JComboBox<MetodoDePago>();
	    cbxMetodos.setBounds(20, 35, 320, 25);
	    panelSeleccion.add(cbxMetodos);

	    // --- CARGAR DATOS ---
	    Object user = Altice.getInstance().getUsuarioLogueado();
	    if (user instanceof Cliente) {
	        clienteLogueado = (Cliente) user;
	        cargarDatos();
	    }

	    {
	        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        getContentPane().add(buttonPane, BorderLayout.SOUTH);
	        
	        JButton btnPagar = new JButton("Confirmar Pago");
	        btnPagar.setBackground(new Color(0, 153, 51));
	        btnPagar.setForeground(Color.WHITE);
	        // --- CORREGIDO: Sin lambdas ---
	        btnPagar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                procesarPago();
	            }
	        });
	        buttonPane.add(btnPagar);

	        JButton btnCancelar = new JButton("Cerrar");
	        // --- CORREGIDO: Sin lambdas ---
	        btnCancelar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        buttonPane.add(btnCancelar);
	    }
	}

	private void cargarDatos() {
		// Calcular deuda
		deudaActual = Altice.getInstance().calcularMontoDeudaReal(clienteLogueado);
		lblMontoDeuda.setText("RD$ " + String.format("%.2f", deudaActual));
		
		// Cargar métodos en el combo
		cbxMetodos.removeAllItems();
		if (clienteLogueado.getMisMetodos() != null) {
			for (MetodoDePago m : clienteLogueado.getMisMetodos()) {
				cbxMetodos.addItem(m);
			}
		}
	}

	private void procesarPago() {
	    if (deudaActual <= 0) {
	        JOptionPane.showMessageDialog(this, "No tiene deudas pendientes.");
	        return;
	    }

	    if (cbxMetodos.getItemCount() == 0) {
	        JOptionPane.showMessageDialog(this, "Debe agregar un método de pago antes de proceder.");
	        return;
	    }

	    MetodoDePago metodoSeleccionado = (MetodoDePago) cbxMetodos.getSelectedItem();
	    
	    // Cálculos para el desglose
	    float subtotal = deudaActual / 1.18f; 
	    float itbisCalculado = deudaActual - subtotal; 

	    int confirm = JOptionPane.showConfirmDialog(this, 
	            "Resumen del Pago:\n" +
	            "Subtotal: RD$ " + String.format("%.2f", subtotal) + "\n" +
	            "ITBIS (18%): RD$ " + String.format("%.2f", itbisCalculado) + "\n" +
	            "Total a debitar: RD$ " + String.format("%.2f", deudaActual) + "\n\n" +
	            "¿Confirmar pago con " + metodoSeleccionado.toString() + "?", 
	            "Confirmación de Pago", JOptionPane.YES_NO_OPTION);

	    if (confirm == JOptionPane.YES_OPTION) {
	        Pago nuevoPago = new Pago("FACT-" + Altice.getInstance().codigoFactura, 
	                                 LocalDate.now(), deudaActual, true, 
	                                 metodoSeleccionado, itbisCalculado, clienteLogueado.getMiContrato());
	        
	        // 1. Lo agregamos a la lista global de Altice (Para reportes)
	        Altice.getInstance().getHistorialPagos().add(nuevoPago);
	        
	        // 2. Lo agregamos al historial del contrato (Para que la deuda baje)
	        clienteLogueado.getMiContrato().getHistorialDePagos().add(nuevoPago);
	        
	        // 3. OJO AQUÍ: Solo lo agregamos al cliente si la lista de pagos del cliente 
	        // es diferente a la del contrato. 
	        if (clienteLogueado.getMisPagos() != clienteLogueado.getMiContrato().getHistorialDePagos()) {
	            clienteLogueado.getMisPagos().add(nuevoPago);
	        }
	        
	        Altice.getInstance().codigoFactura++;
	        
	        // Puntos y estado
	        int puntos = (int)(deudaActual / 100);
	        clienteLogueado.setPuntosAcumulados(clienteLogueado.getPuntosAcumulados() + puntos);
	        clienteLogueado.setEstadoCliente(true);

	        JOptionPane.showMessageDialog(this, "¡Éxito! Pago procesado.");
	        dispose();
	    }
	}
}