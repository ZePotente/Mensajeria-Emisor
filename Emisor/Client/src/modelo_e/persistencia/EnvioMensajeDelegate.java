package modelo_e.persistencia;

import modelo_e.mensaje.Mensaje;

public interface EnvioMensajeDelegate {
    public boolean enviarMensaje(Mensaje mensaje);
}
