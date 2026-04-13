package Server;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class server extends Thread{
	public static void main(String args[]) {
		ServerSocket sfd = null;
		try {
			sfd = new ServerSocket(7000);
		}
		catch(IOException ioe) {
			JOptionPane.showMessageDialog(null,"Conexion rechazada"+ioe, "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		
		while (true)
		{
			try {
				Socket nsfd = sfd.accept();
				JOptionPane.showMessageDialog(null, "Conexion aceptada de: " + nsfd.getInetAddress(), "Conexion", JOptionPane.INFORMATION_MESSAGE);
				DataInputStream oos = new DataInputStream(nsfd.getInputStream());
				DataOutputStream escritor = new DataOutputStream(new FileOutputStream(new File("Altice_Respaldo"+LocalDate.now().getDayOfMonth()+"-"+LocalDate.now().getMonth()+"-"+LocalDate.now().getYear())));
				int Byte;
				try {
					while((Byte = oos.read())!=-1) 
						escritor.write(Byte);
					oos.close();
					escritor.close();
					
				}catch (IOException e) {
					e.printStackTrace();
				}
			}catch(IOException ioe) {
				JOptionPane.showMessageDialog(null, "error"+ioe, "Error", JOptionPane.WARNING_MESSAGE);

			}
		}
	}

}
