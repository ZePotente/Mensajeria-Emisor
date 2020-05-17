package configuracion;

public class Configuracion {
    private String nroIPDirectorio = "";
    private String nroIPSvMensajes = "";
    
    public Configuracion(String nroIPDirectorio) {
        this.nroIPDirectorio = nroIPDirectorio;
    }
    
    public Configuracion(String nroIPDirectorio, String nroIPSvMensajes) {
        this.nroIPDirectorio = nroIPDirectorio;
        this.nroIPSvMensajes = nroIPSvMensajes;
    }

    public String getNroIPDirectorio() {
        return nroIPDirectorio;
    }
    
    public String getIPSvMensajes() {
        return nroIPSvMensajes;
    }
}
