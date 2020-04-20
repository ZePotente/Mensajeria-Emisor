package Modelo;

public class Usuario implements ArmableDesarmable {
    protected String nombre;
    protected String numeroDeIP;
    protected boolean estado;

    public Usuario(String nombre, String numeroDeIP) {
        this.nombre = nombre;
        this.numeroDeIP = numeroDeIP;
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
        return nombre + ArmableDesarmable.SEPARADOR + 
               numeroDeIP + ArmableDesarmable.SEPARADOR +
               estado;
    }
}
