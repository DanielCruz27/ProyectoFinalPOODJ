package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Logico.*;

public class CrearPlanes extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID, txtNombre;
	private JSpinner spnPrecio;
	private JRadioButton rbMovil, rbHogar;
	private JPanel panelDinamico;
	
	private JSpinner spnMinutosMovil;
	private JCheckBox chkWa, chkIg, chkFb, chkTk, chkYt;
	
	private JSpinner spnVelocidad, spnMinutosHogar;
	private JCheckBox chkNetflix, chkHBO, chkDisney, chkPrime, chkAlticeTV;
	
	public static void main(String[] args) {
		try {
			CrearPlanes dialog = new CrearPlanes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CrearPlanes() {
		setTitle("Altice - Registro de Planes y Servicios");
		setModal(true);
		setResizable(false);
		setSize(500, 650);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- CABECERA ---
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 500, 40);
		contentPanel.add(panelHeader);
		
		JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO PLAN / SERVICIO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		// --- DATOS GENERALES ---
		JLabel lblId = new JLabel("ID Servicio:");
		lblId.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblId.setBounds(30, 60, 100, 14);
		contentPanel.add(lblId);

		txtID = new JTextField("S-" + Altice.getInstance().getCodigoServicio());
		txtID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtID.setEditable(false);
		txtID.setBounds(30, 80, 120, 25);
		txtID.setBackground(new Color(245, 245, 245));
		contentPanel.add(txtID);

		JLabel lblNom = new JLabel("Nombre del Plan:");
		lblNom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblNom.setBounds(180, 60, 200, 14);
		contentPanel.add(lblNom);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtNombre.setBounds(180, 80, 260, 25);
		contentPanel.add(txtNombre);

		JLabel lblPrecio = new JLabel("Precio Base (RD$):");
		lblPrecio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		lblPrecio.setBounds(30, 120, 150, 14);
		contentPanel.add(lblPrecio);

		spnPrecio = new JSpinner(new SpinnerNumberModel(500.0, 50.0, 50000.0, 50.0));
		spnPrecio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		spnPrecio.setBounds(30, 140, 120, 25);
		contentPanel.add(spnPrecio);

		// --- SELECTOR DE TIPO ---
		JPanel panelTipo = new JPanel();
		TitledBorder borderTipo = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Tipo de Plan", 
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 14), new Color(0, 102, 204));
		panelTipo.setBorder(borderTipo);
		panelTipo.setBackground(Color.WHITE);
		panelTipo.setBounds(30, 180, 410, 60);
		contentPanel.add(panelTipo);
		panelTipo.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));

		rbMovil = new JRadioButton("Plan Móvil");
		rbMovil.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		rbMovil.setBackground(Color.WHITE);
		rbHogar = new JRadioButton("Plan Hogar");
		rbHogar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		rbHogar.setBackground(Color.WHITE);
		
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rbMovil); grupo.add(rbHogar);
		panelTipo.add(rbMovil); panelTipo.add(rbHogar);

		// --- PANEL DINÁMICO ---
		panelDinamico = new JPanel();
		panelDinamico.setBackground(new Color(252, 252, 252));
		TitledBorder borderEspecial = new TitledBorder(new LineBorder(new Color(0, 102, 204)), 
				" Especificaciones del Plan ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 14), new Color(0, 102, 204));
		panelDinamico.setBorder(borderEspecial);
		panelDinamico.setBounds(30, 260, 410, 220);
		contentPanel.add(panelDinamico);
		panelDinamico.setLayout(null);
		panelDinamico.setVisible(false);

		// Evento RadioButtons tradicional
		ActionListener tipoListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configurarPanelDinamico();
			}
		};
		rbMovil.addActionListener(tipoListener);
		rbHogar.addActionListener(tipoListener);

		// --- BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar Plan");
		btnRegistrar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(0, 153, 51));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		buttonPane.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);
	}

	private void configurarPanelDinamico() {
		panelDinamico.removeAll();
		panelDinamico.setVisible(true);

		if (rbMovil.isSelected()) {
			JLabel l1 = new JLabel("Minutos Incluidos:");
			l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			l1.setBounds(20, 30, 150, 14);
			spnMinutosMovil = new JSpinner(new SpinnerNumberModel(100, 0, 10000, 10));
			spnMinutosMovil.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			spnMinutosMovil.setBounds(20, 50, 100, 25);
			panelDinamico.add(l1); panelDinamico.add(spnMinutosMovil);

			JLabel l2 = new JLabel("Redes Sociales Incluidas:");
			l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			l2.setBounds(20, 90, 200, 14);
			panelDinamico.add(l2);

			chkWa = new JCheckBox("WhatsApp"); chkWa.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkWa.setBounds(20, 110, 100, 20);
			chkIg = new JCheckBox("Instagram"); chkIg.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkIg.setBounds(120, 110, 100, 20);
			chkFb = new JCheckBox("Facebook"); chkFb.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkFb.setBounds(220, 110, 100, 20);
			chkTk = new JCheckBox("TikTok"); chkTk.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkTk.setBounds(20, 135, 100, 20);
			chkYt = new JCheckBox("YouTube"); chkYt.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkYt.setBounds(120, 135, 100, 20);
			
			panelDinamico.add(chkWa); panelDinamico.add(chkIg); panelDinamico.add(chkFb);
			panelDinamico.add(chkTk); panelDinamico.add(chkYt);
			
		} else {
			JLabel l1 = new JLabel("Velocidad (Mbps):");
			l1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			l1.setBounds(20, 25, 150, 14);
			spnVelocidad = new JSpinner(new SpinnerNumberModel(50, 5, 1000, 5));
			spnVelocidad.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			spnVelocidad.setBounds(20, 45, 100, 25);
			
			JLabel l2 = new JLabel("Minutos Fijo:");
			l2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			l2.setBounds(210, 25, 150, 14);
			spnMinutosHogar = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 50));
			spnMinutosHogar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			spnMinutosHogar.setBounds(210, 45, 100, 25);
			
			JLabel l3 = new JLabel("Servicios Streaming:");
			l3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
			l3.setBounds(20, 85, 200, 14);
			
			chkNetflix = new JCheckBox("Netflix"); chkNetflix.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkNetflix.setBounds(20, 105, 100, 20);
			chkHBO = new JCheckBox("HBO Max"); chkHBO.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkHBO.setBounds(120, 105, 100, 20);
			chkDisney = new JCheckBox("Disney+"); chkDisney.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkDisney.setBounds(220, 105, 100, 20);
			chkPrime = new JCheckBox("Prime Video"); chkPrime.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkPrime.setBounds(20, 130, 110, 20);
			chkAlticeTV = new JCheckBox("Altice TV"); chkAlticeTV.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13)); chkAlticeTV.setBounds(135, 130, 110, 20);

			panelDinamico.add(l1); panelDinamico.add(spnVelocidad);
			panelDinamico.add(l2); panelDinamico.add(spnMinutosHogar);
			panelDinamico.add(l3); panelDinamico.add(chkNetflix);
			panelDinamico.add(chkHBO); panelDinamico.add(chkDisney);
			panelDinamico.add(chkPrime); panelDinamico.add(chkAlticeTV);
		}
		
		for(Component c : panelDinamico.getComponents()) {
			if(c instanceof JCheckBox) c.setBackground(new Color(252, 252, 252));
		}

		panelDinamico.revalidate();
		panelDinamico.repaint();
	}

	private void registrar() {
		try {
			if (txtNombre.getText().trim().isEmpty()) throw new Exception("Nombre del plan requerido.");
			if (!rbMovil.isSelected() && !rbHogar.isSelected()) throw new Exception("Seleccione tipo de plan.");

			String nombre = txtNombre.getText().trim();
			float precio = Float.parseFloat(spnPrecio.getValue().toString());
			Servicio nuevo = null;

			if (rbMovil.isSelected()) {
				int mins = Integer.parseInt(spnMinutosMovil.getValue().toString());
				String redes = "";
				if(chkWa.isSelected()) redes += "WhatsApp, ";
				if(chkIg.isSelected()) redes += "Instagram, ";
				if(chkFb.isSelected()) redes += "Facebook, ";
				if(chkTk.isSelected()) redes += "TikTok, ";
				if(chkYt.isSelected()) redes += "YouTube, ";
				redes = redes.isEmpty() ? "Ninguna" : redes.substring(0, redes.length()-2);
				
				nuevo = new PlanMovil(txtID.getText(), nombre, precio, true, "", mins, redes);
			} else {
				int vel = Integer.parseInt(spnVelocidad.getValue().toString());
				int minsH = Integer.parseInt(spnMinutosHogar.getValue().toString());
				String streams = "";
				if(chkNetflix.isSelected()) streams += "Netflix, ";
				if(chkHBO.isSelected()) streams += "HBO Max, ";
				if(chkDisney.isSelected()) streams += "Disney+, ";
				if(chkPrime.isSelected()) streams += "Prime Video, ";
				if(chkAlticeTV.isSelected()) streams += "Altice TV, ";
				streams = streams.isEmpty() ? "Ninguno" : streams.substring(0, streams.length()-2);

				nuevo = new PlanHogar(txtID.getText(), nombre, precio, true, "", vel, streams, minsH);
			}

			Altice.getInstance().RegistarServicio(nuevo);
			JOptionPane.showMessageDialog(this, "Plan registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			limpiar();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiar() {
		txtNombre.setText("");
		spnPrecio.setValue(500.0);
		txtID.setText("S-" + Altice.getInstance().getCodigoServicio());
		panelDinamico.setVisible(false);
		txtNombre.requestFocus();
	}
}