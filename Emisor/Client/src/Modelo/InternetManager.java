package Modelo;

import Modelo.Mensaje.Mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

import java.net.UnknownHostException;

import java.util.Observable;

public class InternetManager extends Observable {
    public InternetManager() {
        super();
    }
    
    public void enviarMensaje(String nombreDestinatario, String nroIP, int nroPuerto, String msg) throws UnknownHostException, IOException {
        Socket socket = new Socket(nroIP.trim(), nroPuerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(msg);
        
        out.close();
        socket.close();
        
        if (msg.split(Mensaje.SEPARADOR)[0].equals(Mensaje.MENSAJE_RECEPCION)) {
            setChanged();
            notifyObservers(nombreDestinatario);
        }
    }
}

