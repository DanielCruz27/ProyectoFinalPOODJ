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
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

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
        panelLogin.setBorder(new LineBorder(new Color(0, 0, 255), 4)); 
        panelLogin.setBackground(new Color(255, 255, 255, 240)); 
        panelLogin.setBounds(698, 172, 526, 557);
        panelPrincipal.add(panelLogin);
        panelLogin.setLayout(null);
        
        ImageIcon iconoOriginal = new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/LogoAltice.jpg"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel("");
        lblLogo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 128)));
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        lblLogo.setBounds(135, 28, 250, 141);
        panelLogin.add(lblLogo);
        
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblTitulo.setBounds(187, 194, 154, 25);
        panelLogin.add(lblTitulo);
        
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        lblUser.setBounds(71, 244, 102, 14);
        panelLogin.add(lblUser);
        
        textField = new JTextField();
        textField.setBorder(new LineBorder(new Color(0, 0, 255)));
        textField.setBounds(71, 268, 329, 25);
        panelLogin.add(textField);
        
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        lblPass.setBounds(68, 317, 135, 14);
        panelLogin.add(lblPass);
        
        passwordField = new JPasswordField();
        passwordField.setBorder(new LineBorder(new Color(0, 0, 255)));
        passwordField.setBounds(71, 341, 329, 25);
        panelLogin.add(passwordField);
        
        JButton btnAcceder = new JButton("Acceder");
        btnAcceder.setBackground(new Color(0, 128, 255));
        btnAcceder.setForeground(Color.WHITE);
        btnAcceder.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        btnAcceder.setBounds(206, 404, 135, 30);
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
        lblOlvido.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        lblOlvido.setBounds(146, 461, 292, 25);
        panelLogin.add(lblOlvido);
        
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(Principal_InicioSesion.class.getResource("/Recursos/Altice_Inicio.png")));
        lblFondo.setBounds(0, 0, 888, 489);
        panelPrincipal.add(lblFondo);
        
        int ancho = getWidth(); 
        int alto = getHeight();

        Image imgEscalada = ((ImageIcon)lblFondo.getIcon()).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        lblFondo.setIcon(new ImageIcon(imgEscalada));
        lblFondo.setBounds(0, 0, ancho, alto);
    }
}