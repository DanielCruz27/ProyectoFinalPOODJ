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
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import Logico.Altice;
import Logico.Tecnico;
import Logico.Personal;

public class Mi_informacionTecnico extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public Mi_informacionTecnico() {
        setTitle("Altice - Perfil del Técnico");
        setModal(true);
        setResizable(false);
        setSize(400, 420);
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
        
        JLabel lblMiPerfil = new JLabel("MI INFORMACIÓN LABORAL");
        lblMiPerfil.setForeground(Color.WHITE);
        lblMiPerfil.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
        panelHeader.add(lblMiPerfil);

        // --- PANEL DE DATOS ---
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(new TitledBorder(new LineBorder(new Color(0, 102, 204)), " Detalles de la Cuenta ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial Rounded MT Bold", Font.BOLD, 12), new Color(0, 102, 204)));
        panelDatos.setBounds(20, 60, 345, 260);
        contentPanel.add(panelDatos);
        panelDatos.setLayout(null);

        JLabel lbl1 = new JLabel("Nombre Completo:"); lbl1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl1.setBounds(20, 30, 150, 14); panelDatos.add(lbl1);
        JLabel lblNombreVal = new JLabel("Cargando...");
        lblNombreVal.setBounds(20, 50, 250, 14); panelDatos.add(lblNombreVal);

        JLabel lbl2 = new JLabel("Salario Base:"); lbl2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl2.setBounds(20, 80, 100, 14); panelDatos.add(lbl2);
        JLabel lblSalarioVal = new JLabel("RD$ 0.00");
        lblSalarioVal.setBounds(20, 100, 150, 14); panelDatos.add(lblSalarioVal);

        JLabel lbl3 = new JLabel("Estado:"); lbl3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl3.setBounds(180, 80, 100, 14); panelDatos.add(lbl3);
        JLabel lblEstadoVal = new JLabel("Activo");
        lblEstadoVal.setBounds(180, 100, 100, 14); panelDatos.add(lblEstadoVal);

        JLabel lbl4 = new JLabel("Tipo de Técnico:"); lbl4.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl4.setBounds(20, 135, 150, 14); panelDatos.add(lbl4);
        JLabel lblTipoVal = new JLabel("N/A");
        lblTipoVal.setBounds(20, 155, 150, 14); panelDatos.add(lblTipoVal);

        JLabel lbl5 = new JLabel("Zona Asignada:"); lbl5.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl5.setBounds(180, 135, 150, 14); panelDatos.add(lbl5);
        JLabel lblZonaVal = new JLabel("Sin asignar");
        lblZonaVal.setBounds(180, 155, 150, 14); panelDatos.add(lblZonaVal);

        // --- CARGA DE DATOS ---
     // --- CARGA DE DATOS ---
        Object user = Altice.getInstance().getUsuarioLogueado();
        if (user instanceof Tecnico) {
            Tecnico tec = (Tecnico) user;
            lblNombreVal.setText(tec.getNombre() + " " + tec.getApellido());
            lblSalarioVal.setText("RD$ " + tec.getSalarioBase());
            
            // Aquí manejamos el estado laboral (Baja o Activo)
            if (tec.getEstado() == 1) {
                lblEstadoVal.setText("ACTIVO");
                lblEstadoVal.setForeground(new Color(0, 153, 51)); // Verde para los activos
            } else {
                lblEstadoVal.setText("DE BAJA / INACTIVO");
                lblEstadoVal.setForeground(Color.RED); // Rojo para los que no están laborando
            }
            
            lblTipoVal.setText(tec.getTipoTecnico()); 
            lblZonaVal.setText(tec.getZonAsignada());
        
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(new Color(245, 245, 245));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Entendido");
        okButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
        
        // --- CAMBIO AQUÍ: Clase anónima en vez de lambda ---
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPane.add(okButton);
    }
}
