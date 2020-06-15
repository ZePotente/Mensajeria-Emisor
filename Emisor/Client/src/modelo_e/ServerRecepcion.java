package modelo_e;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Observable;

public class ServerRecepcion extends Observable implements IServerRecepcion {
    private ServerSocket sv;
    private int puerto;
    
    public ServerRecepcion(int puerto) {
        this.puerto = puerto;
    }

    public void escucharNotificaciones() {   
        new Thread() {
            public void run() {
                try {
                sv = new ServerSocket(puerto);
                while (true) {
                    try (Socket socket = sv.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) 
                    {
                        String nombreDestinatario = in.readLine();
                        
                        setChanged();
                        notifyObservers(nombreDestinatario);
                    } catch (IOException e) {
                        //tampoco deberia ir aca.
                        System.out.println("Error al recibir una notificacion de recepcion.");
                    }
        
                }
                } catch (IOException e) {
                    // lastima
                    System.out.println("Algo fallo en el server de notificacion de mensajes con recepcion.");
                }
            }
        }.start();
    }
}
