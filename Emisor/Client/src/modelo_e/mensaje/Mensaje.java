package modelo_e.mensaje;

import Excepciones.MalTipoDeMensajeException;

import Excepciones.MensajeMalFormadoException;

import java.io.Serializable;

import modelo_e.ArmableDesarmable;
import modelo_e.agenda.Usuario;

public class Mensaje implements ArmableDesarmable, Serializable {
    // clase
    public static final String MENSAJE_SIMPLE = "1", MENSAJE_ALARMA = "2", MENSAJE_RECEPCION = "3";
    // instancia
    protected String asunto;
    protected String descripcion;
    protected Usuario emisor;
    protected Usuario destinatario;
    
    public Mensaje(String asunto, String descripcion, Usuario destinatario, Usuario emisor) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.destinatario = destinatario;
        this.emisor = emisor;
    }
    
    @Override
    public String toString() {
        return asunto + ArmableDesarmable.SEPARADOR +
               descripcion + ArmableDesarmable.SEPARADOR +
               destinatario.toString() + ArmableDesarmable.SEPARADOR +
               emisor.toString();
    }
    
    @Override
    public String desarmar() {
        return asunto + ArmableDesarmable.SEPARADOR +
               descripcion + ArmableDesarmable.SEPARADOR +
               destinatario.desarmar() + ArmableDesarmable.SEPARADOR +
               emisor.desarmar(); 
    }
    
    public Usuario getDestinatario() {
        return this.destinatario;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void ejecutar(){};
}

