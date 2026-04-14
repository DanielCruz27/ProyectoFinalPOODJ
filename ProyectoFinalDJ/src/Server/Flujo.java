package Server;

import java.io.*;
import java.net.*;
import java.time.LocalDate;

public class Flujo extends Thread {
    private Socket nsfd;
    private DataInputStream flujoLectura;

    public Flujo(Socket sfd) {
        this.nsfd = sfd;
        try {
            flujoLectura = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("Error inicializando flujo: " + ioe.getMessage());
        }
    }

    @Override
    public void run() {
        server.usuarios.add(this);
        System.out.println("Conectado con: " + nsfd.getInetAddress());

        FileOutputStream fosProyecto = null;
        FileOutputStream fosDesktop = null;

        try {
            String nombreArchivo = "Altice_Respaldo_" + LocalDate.now() + ".dat";
            
            String rutaEscritorio = System.getProperty("user.home") + File.separator + "Desktop";
            
            File archivoEnProyecto = new File(nombreArchivo);
            File archivoEnDesktop = new File(rutaEscritorio, nombreArchivo);

            fosProyecto = new FileOutputStream(archivoEnProyecto);
            fosDesktop = new FileOutputStream(archivoEnDesktop);

            byte[] buffer = new byte[1024];
            int bytesLeidos;

            
            while ((bytesLeidos = flujoLectura.read(buffer)) != -1) {
                fosProyecto.write(buffer, 0, bytesLeidos);
                fosDesktop.write(buffer, 0, bytesLeidos);
            }

            

        } catch (IOException e) {
            System.out.println("Error durante la transferencia: " + e.getMessage());
        } finally {
            try {
                if (fosProyecto != null) fosProyecto.close();
                if (fosDesktop != null) fosDesktop.close();
                if (flujoLectura != null) flujoLectura.close();
                if (nsfd != null) nsfd.close();
                server.usuarios.remove(this);
            } catch (IOException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
    

