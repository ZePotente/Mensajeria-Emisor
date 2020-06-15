package modelo_e.mensaje;

import Excepciones.MensajeMalFormadoException;

import modelo_e.ArmableDesarmable;
import modelo_e.agenda.Usuario;

public class MensajeRecepcion extends Mensaje {
    public MensajeRecepcion(String asunto, String descripcion, Usuario destinatario, Usuario emisor) {
        super(asunto,descripcion,destinatario,emisor);
    }
    
    @Override
    public String desarmar() {
        return Mensaje.MENSAJE_RECEPCION + ArmableDesarmable.SEPARADOR +
               super.toString();
    }
}
