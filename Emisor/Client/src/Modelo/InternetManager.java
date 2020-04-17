package Modelo;

import Modelo.Mensaje.Mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
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
    
    public void requestDestinatarios(String nroIP, int nroPuerto) throws IOException {
        escuchar();
        Socket socket = new Socket(nroIP.trim(), nroPuerto);
        PrintWriter out;
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("RequestReceptores\n"+nroIP.trim());
    }
    
    public void escuchar() {
        new Thread() {
                    public void run() {
                        try {
                            ServerSocket s = new ServerSocket(1234);
                            while (true) { // una vez que escucha ese puerto se queda escuchandolo aunque ingresen otro puerto
                                Socket soc = s.accept();
                                PrintWriter out = new PrintWriter(soc.getOutputStream(), true); // hace falta?
                                BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                                
                                String msg, aux;
                                aux = in.readLine();
                                System.out.println(aux);
                                s.close();
                                soc.close();
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }.start();
    }
}

