package Modelo;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerRecepcion extends Thread {
    private ServerSocket sv;
    private static int port;
    
    public ServerRecepcion() throws IOException {
        sv = new ServerSocket(ServerRecepcion.port);
    }


    @Override
    public void run() {
        while (true) {
            try(Socket socket = sv.accept();) {
                setChanged();
                notifyObservers(nombreDestinatario);
            } catch (IOException e) {
                //tampoco deberia ir aca.
                System.out.println("Error al recibir una notificacion de recepcion.");
            }

        }
    }
}
