package Modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.Observable;

public class InternetManager extends Observable {
    private static final int PUERTO_NOTIFICACION_RECEPCION = 300;

    public InternetManager() {
        escuchar(InternetManager.PUERTO_NOTIFICACION_RECEPCION);
    }
    
    public void escuchar(int puerto) {
        new Thread() {
            public void run() {
                try {
                ServerSocket sv = new ServerSocket(puerto);
                while (true) {
                    try(Socket socket = sv.accept();
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
                } catch (Exception e) {
                    // lastima
                    System.out.println("Algo fallo en el server de notificacion de mensajes con recepcion.");
                }
            }
        }.start();
    }
    
    public void enviarMensaje(String nombreDestinatario, String nroIP, int nroPuerto, String msg) throws UnknownHostException, IOException {
        Socket socket = new Socket(nroIP.trim(), nroPuerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(nombreDestinatario+"\n"+nroIP+"\n"+msg);
        
        socket.close();
        
        /*
        if (msg.split(Mensaje.SEPARADOR)[0].equals(Mensaje.MENSAJE_RECEPCION)) {
            setChanged();
            notifyObservers(nombreDestinatario);
        }
        */
    }
    
    public String requestDestinatarios(String nroIPDirectorio, int nroPuerto) throws IOException {
        Socket socket = new Socket(nroIPDirectorio.trim(), nroPuerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("RequestReceptores");
        
        // leida de la lista de receptores online
        String lista = in.readLine();
        socket.close();
        return lista;
    }
}

