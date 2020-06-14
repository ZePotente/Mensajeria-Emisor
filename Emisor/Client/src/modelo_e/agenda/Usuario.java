package modelo_e.agenda;

import java.io.Serializable;

import modelo_e.ArmableDesarmable;

public class Usuario implements ArmableDesarmable, Serializable, Cloneable {
    protected String nombre;
    protected String numeroDeIP;
    protected boolean estado;

    public Usuario(String nombre, String numeroDeIP) {
        this.nombre = nombre;
        this.numeroDeIP = numeroDeIP;
    }
    
    public Object clone() {
        Object ob = null;
        try {
            ob = super.clone();
        } catch (CloneNotSupportedException e) {
            //esta sobreescrito asi que no hace falta.
        }
        return ob;
    }
    
    public String getNumeroDeIP() {
        return numeroDeIP;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public boolean getEstado() {
        return estado;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        String estado;
        if (this.estado) {
            estado = "Online";
        } else {
            estado = "Offline";
        }
        return nombre + "-" + estado;
    }
    
    @Override
    public String desarmar() {
        return nombre + ArmableDesarmable.SEPARADOR + 
               numeroDeIP;
    }
}
