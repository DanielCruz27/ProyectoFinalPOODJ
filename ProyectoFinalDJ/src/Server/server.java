package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    
    public static Vector<Flujo> usuarios = new Vector<>();
    
    public static void main(String args[]) {
        ServerSocket sfd = null;
        try {
            sfd = new ServerSocket(7000);
        } catch (IOException ioe) {
            System.out.println("Comunicación rechazada: " + ioe);
            System.exit(1);
        }

        while (true) {
            try {
                Socket nsfd = sfd.accept();
                System.out.println("Conexión aceptada de: " + nsfd.getInetAddress());
                
                Flujo flujo = new Flujo(nsfd);
                flujo.start(); 
                
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }
    }
}