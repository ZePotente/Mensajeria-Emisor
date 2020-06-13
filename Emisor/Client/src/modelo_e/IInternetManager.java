package modelo_e;

import java.io.IOException;

import java.net.UnknownHostException;

public interface IInternetManager {
    public boolean enviarMensaje(String nroIPServidorMensajes, String nombreDestinatario, String nroIPDestinatario, int nroPuerto,
                       String msg);
    public String requestDestinatarios(String nroIPDirectorio, int nroPuerto) throws IOException;
}
