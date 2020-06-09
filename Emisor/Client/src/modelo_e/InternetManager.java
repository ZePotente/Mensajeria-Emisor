package modelo_e;

import modelo_e.mensaje.Mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.Observable;

public class InternetManager extends Observable implements IInternetManager {
    public InternetManager() {
        
    }
    
    public void enviarMensaje(String nroIPServidorMensajes, String nombreDestinatario, String nroIPDestinatario, int nroPuerto, String msg) throws UnknownHostException, IOException {
        Socket socket = new Socket(nroIPServidorMensajes.trim(), nroPuerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(nombreDestinatario+"\n"+nroIPDestinatario+"\n"+msg);
        socket.close();
        
        if (msg.split(Mensaje.SEPARADOR)[0].equals(Mensaje.MENSAJE_RECEPCION)) {
            setChanged();
            notifyObservers(nombreDestinatario);
        }
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

