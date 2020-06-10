package modelo_e;

import java.io.IOException;

import java.net.UnknownHostException;

public interface IInternetManager {
    public void enviarMensaje(String nroIPServidorMensajes, String nombreDestinatario, String nroIPDestinatario, int nroPuerto,
                       String msg) throws UnknownHostException, IOException;
    public String requestDestinatarios(String nroIPDirectorio, int nroPuerto) throws IOException;
}
