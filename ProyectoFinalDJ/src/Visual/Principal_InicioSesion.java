package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Principal_InicioSesion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal_InicioSesion frame = new Principal_InicioSesion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Principal_InicioSesion() {
        
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Principal_InicioSesion.class.getResource("/Recursos/LogoAltice.jpg")));
        setTitle("Altice - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 903, 536);
        Dimension dim = getToolkit().getScreenSize();
		setSize(dim.width, dim.height-38);
        setLocationRelativeTo(null); 

        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(null);
        
        JPanel panelLogin = new JPanel();
        panelLogin.setBorder(new LineBorder(new Color(0, 0, 255), 2, true)); 
        panelLogin.setBackground(new Color(255, 255, 255, 240)); 
        panelLogin.setBounds(301, 47, 301, 387);
        panelPrincipal.add(panelLogin);
        panelLogin.setLayout(null);
        
        ImageIcon iconoOriginal = new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/LogoAltice.jpg"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(200, 101, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        lblLogo.setBounds(50, 11, 200, 101);
        panelLogin.add(lblLogo);
        
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
        lblTitulo.setBounds(88, 123, 135, 25);
        panelLogin.add(lblTitulo);
        
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
        lblUser.setBounds(34, 170, 102, 14);
        panelLogin.add(lblUser);
        
        textField = new JTextField();
        textField.setBorder(new LineBorder(new Color(0, 0, 255)));
        textField.setBounds(34, 195, 216, 25);
        panelLogin.add(textField);
        
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
        lblPass.setBounds(34, 235, 135, 14);
        panelLogin.add(lblPass);
        
        passwordField = new JPasswordField();
        passwordField.setBorder(new LineBorder(new Color(0, 0, 255)));
        passwordField.setBounds(34, 255, 216, 25);
        panelLogin.add(passwordField);
        
        JButton btnAcceder = new JButton("Acceder");
        btnAcceder.setBackground(new Color(0, 128, 255));
        btnAcceder.setForeground(Color.WHITE);
        btnAcceder.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
        btnAcceder.setBounds(95, 300, 111, 30);
        panelLogin.add(btnAcceder);
        
        JLabel lblOlvido = new JLabel("<html><u>¿Se te ha olvidado la contraseña?</u></html>");
        lblOlvido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblOlvido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String userRecuperar = JOptionPane.showInputDialog(Principal_InicioSesion.this, 
                    "Introduce tu usuario para recuperar la cuenta:", 
                    "Recuperación Altice", JOptionPane.QUESTION_MESSAGE);
                
                if(userRecuperar != null && !userRecuperar.isEmpty()) {
                    JOptionPane.showMessageDialog(Principal_InicioSesion.this, 
                        "¡Atención!\nSe ha enviado un código de recuperación al correo asociado.", 
                        "Advertencia de Seguridad", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        lblOlvido.setForeground(new Color(0, 128, 255));
        lblOlvido.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
        lblOlvido.setBounds(50, 349, 216, 14);
        panelLogin.add(lblOlvido);
        
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/Altice_Inicio.png")));
        lblFondo.setBounds(0, 0, 888, 489);
        panelPrincipal.add(lblFondo);
    }
}