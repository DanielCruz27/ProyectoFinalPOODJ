package Visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import Logico.*;

public class ListarServicios extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtNombre, txtPrecio;
	private JSpinner spnEspecial1, spnEspecial2; 
	private JLabel lblEspecial1, lblEspecial2, lblStreaming;
	private JPanel panelEditar;
	private JButton btnGuardar; 
	private Servicio seleccionado = null;

	private JCheckBox chkWa, chkIg, chkFb, chkTk, chkYt;
	private JCheckBox chkNetflix, chkHBO, chkDisney, chkPrime, chkAlticeTV;
	private JPanel panelChecks;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ListarServicios dialog = new ListarServicios("admin"); 
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarServicios(String rol) {
		setTitle("Altice - Consulta y Edición de Catálogo");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(0, 102, 204));
		panelHeader.setBounds(0, 0, 1000, 40);
		contentPanel.add(panelHeader);

		JLabel lblTitulo = new JLabel("GESTIÓN DE CATÁLOGO DE PLANES");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		panelHeader.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 620, 430);
		contentPanel.add(scrollPane);

		String[] columnas = {"ID", "Nombre", "Tipo", "Precio", "Estado"}; 
		model = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		table = new JTable(model);
		table.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		table.getTableHeader().setBackground(new Color(0, 102, 204));
		table.getTableHeader().setForeground(Color.WHITE);
		scrollPane.setViewportView(table);

		panelEditar = new JPanel();
		panelEditar.setBackground(Color.WHITE);
		panelEditar.setLayout(null);
		panelEditar.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Detalles y Edición ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204)));
		panelEditar.setBounds(660, 60, 310, 430);
		contentPanel.add(panelEditar);

		JLabel l1 = new JLabel("Nombre:");
		l1.setBounds(20, 30, 80, 14); 
		panelEditar.add(l1);
		txtNombre = new JTextField();
		txtNombre.setBounds(20, 50, 270, 25); 
		panelEditar.add(txtNombre);

		JLabel l2 = new JLabel("Precio Base:");
		l2.setBounds(20, 85, 100, 14); 
		panelEditar.add(l2);
		txtPrecio = new JTextField(); 
		txtPrecio.setBounds(20, 105, 120, 25);
		panelEditar.add(txtPrecio);

		lblEspecial1 = new JLabel("Dato 1:");
		lblEspecial1.setBounds(20, 145, 150, 14); 
		panelEditar.add(lblEspecial1);
		spnEspecial1 = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		spnEspecial1.setBounds(20, 165, 100, 25);
		panelEditar.add(spnEspecial1);

		lblEspecial2 = new JLabel("Dato 2:");
		lblEspecial2.setBounds(170, 145, 150, 14); 
		panelEditar.add(lblEspecial2);
		spnEspecial2 = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		spnEspecial2.setBounds(170, 165, 100, 25);
		panelEditar.add(spnEspecial2);

		lblStreaming = new JLabel("Inclusiones:");
		lblStreaming.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		lblStreaming.setBounds(20, 205, 250, 14); 
		panelEditar.add(lblStreaming);

		panelChecks = new JPanel();
		panelChecks.setBackground(new Color(250, 250, 250));
		panelChecks.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelChecks.setBounds(20, 225, 270, 130);
		panelEditar.add(panelChecks);
		panelChecks.setLayout(null);

		chkWa = new JCheckBox("WhatsApp"); 
		chkWa.setBounds(5, 5, 120, 20);
		chkWa.setBackground(new Color(250,250,250));

		chkIg = new JCheckBox("Instagram"); 
		chkIg.setBounds(5, 30, 120, 20); 
		chkIg.setBackground(new Color(250,250,250));

		chkFb = new JCheckBox("Facebook"); 
		chkFb.setBounds(5, 55, 120, 20);
		chkFb.setBackground(new Color(250,250,250));

		chkTk = new JCheckBox("TikTok"); 
		chkTk.setBounds(135, 5, 120, 20);
		chkTk.setBackground(new Color(250,250,250));

		chkYt = new JCheckBox("YouTube"); 
		chkYt.setBounds(135, 30, 120, 20); 
		chkYt.setBackground(new Color(250,250,250));

		chkNetflix = new JCheckBox("Netflix");
		chkNetflix.setBounds(5, 5, 120, 20); 
		chkNetflix.setBackground(new Color(250,250,250));

		chkHBO = new JCheckBox("HBO Max"); 
		chkHBO.setBounds(5, 30, 120, 20); 
		chkHBO.setBackground(new Color(250,250,250));

		chkDisney = new JCheckBox("Disney+"); 
		chkDisney.setBounds(5, 55, 120, 20); 
		chkDisney.setBackground(new Color(250,250,250));

		chkPrime = new JCheckBox("Prime Video"); 
		chkPrime.setBounds(135, 5, 120, 20); 
		chkPrime.setBackground(new Color(250,250,250));

		chkAlticeTV = new JCheckBox("Altice TV"); 
		chkAlticeTV.setBounds(135, 30, 120, 20); 
		chkAlticeTV.setBackground(new Color(250,250,250));

		btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setBackground(new Color(0, 153, 51));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(65, 375, 180, 35);
		btnGuardar.addActionListener(e -> guardarCambios());
		panelEditar.add(btnGuardar);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { cargarDatosSeleccionado(); }
		});

		lblEspecial1.setVisible(false);
		spnEspecial1.setVisible(false);
		lblEspecial2.setVisible(false); 
		spnEspecial2.setVisible(false);
		lblStreaming.setVisible(false); 
		panelChecks.setVisible(false);

		cargarTabla();
	}

	private void cargarTabla() {
		model.setRowCount(0);
		for (Servicio s : Altice.getInstance().getCatalogoServicio()) {
			String tipo = (s instanceof PlanMovil) ? "Móvil" : "Hogar";
			model.addRow(new Object[]{s.getIdServicio(), s.getNombreServicio(), tipo, s.getPrecioBase(), s.isEstadoDelServicio()?"Activo":"No"});
		}
	}

	private void cargarDatosSeleccionado() {
		int fila = table.getSelectedRow();
		if (fila >= 0) {
			String id = (String) table.getValueAt(fila, 0);
			seleccionado = Altice.getInstance().buscarServicioById(id);

			if (seleccionado != null) {
				txtNombre.setText(seleccionado.getNombreServicio());
				txtPrecio.setText(String.valueOf(seleccionado.getPrecioBase()));

				panelChecks.removeAll();
				panelChecks.setVisible(true); 
				lblStreaming.setVisible(true);

				if (seleccionado instanceof PlanMovil) {
					PlanMovil pm = (PlanMovil) seleccionado;
					lblEspecial1.setText("Mins. Incluidos:");
					lblEspecial1.setVisible(true);
					spnEspecial1.setVisible(true);
					spnEspecial1.setValue(pm.getMinutosIncluidos());
					lblEspecial2.setVisible(false); spnEspecial2.setVisible(false);

					lblStreaming.setText("Redes Sociales:");

					String redes = pm.getRedesLibresIncluidas();
					chkWa.setSelected(redes.contains("WhatsApp"));
					chkIg.setSelected(redes.contains("Instagram"));
					chkFb.setSelected(redes.contains("Facebook"));
					chkTk.setSelected(redes.contains("TikTok"));
					chkYt.setSelected(redes.contains("YouTube"));

					panelChecks.add(chkWa); 
					panelChecks.add(chkIg); 
					panelChecks.add(chkFb);
					panelChecks.add(chkTk);
					panelChecks.add(chkYt);

				} else {
					PlanHogar ph = (PlanHogar) seleccionado;
					lblEspecial1.setText("Velocidad Mbps:");
					lblEspecial1.setVisible(true);
					spnEspecial1.setVisible(true);
					spnEspecial1.setValue(ph.getVelocidadInternet());

					lblEspecial2.setText("Mins. Fijo:");
					lblEspecial2.setVisible(true); spnEspecial2.setVisible(true);
					spnEspecial2.setValue(ph.getMinutosTelefonoHogar());

					lblStreaming.setText("Servicios Streaming:");

					String st = ph.getStreamingIncluido();
					chkNetflix.setSelected(st.contains("Netflix"));
					chkHBO.setSelected(st.contains("HBO Max"));
					chkDisney.setSelected(st.contains("Disney+"));
					chkPrime.setSelected(st.contains("Prime Video"));
					chkAlticeTV.setSelected(st.contains("Altice TV"));

					panelChecks.add(chkNetflix);
					panelChecks.add(chkHBO);
					panelChecks.add(chkDisney);
					panelChecks.add(chkPrime); 
					panelChecks.add(chkAlticeTV);
				}

				panelChecks.revalidate();
				panelChecks.repaint();
				panelEditar.revalidate();
				panelEditar.repaint();
			}
		}
	}

	private void guardarCambios() {
		try {
			if (seleccionado == null) return;
			seleccionado.setNombreServicio(txtNombre.getText());
			seleccionado.setPrecioBase(Float.parseFloat(txtPrecio.getText()));

			if (seleccionado instanceof PlanMovil) {
				PlanMovil pm = (PlanMovil) seleccionado;
				pm.setMinutosLibres((Integer)spnEspecial1.getValue());
				String redes = "";
				if(chkWa.isSelected()) redes += "WhatsApp, ";
				if(chkIg.isSelected()) redes += "Instagram, ";
				if(chkFb.isSelected()) redes += "Facebook, ";
				if(chkTk.isSelected()) redes += "TikTok, ";
				if(chkYt.isSelected()) redes += "YouTube, ";
				pm.setRedesLibresIncluidas(redes.isEmpty() ? "Ninguna" : redes.substring(0, redes.length()-2));
			} else {
				PlanHogar ph = (PlanHogar) seleccionado;
				ph.setVelocidadInternet((Integer)spnEspecial1.getValue());
				ph.setMinutosTelefonoHogar((Integer)spnEspecial2.getValue());
				String st = "";
				if(chkNetflix.isSelected()) st += "Netflix, ";
				if(chkHBO.isSelected()) st += "HBO Max, ";
				if(chkDisney.isSelected()) st += "Disney+, ";
				if(chkPrime.isSelected()) st += "Prime Video, ";
				if(chkAlticeTV.isSelected()) st += "Altice TV, ";
				ph.setStreamingIncluido(st.isEmpty() ? "Ninguno" : st.substring(0, st.length()-2));
			}
			JOptionPane.showMessageDialog(this, "Plan actualizado correctamente.");
			cargarTabla();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}
}