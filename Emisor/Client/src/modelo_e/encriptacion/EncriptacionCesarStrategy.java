package modelo_e.encriptacion;

public class EncriptacionCesarStrategy implements IEncriptacionStrategy {
    private final int DESPLAZAMIENTO = 1;
    public EncriptacionCesarStrategy() {
        super();
    }

    @Override
    public String encriptar(String asunto, String cuerpo) {
        String mensaje = asunto + cuerpo;
        String cifrado = "";
        for (int i = 0; i < cifrado.length(); i++)
            cifrado += this.desplazar(mensaje.charAt(i));
        return cifrado;
    }
    
    private char desplazar(char car) {
        return (char) (car + this.DESPLAZAMIENTO);
    }
}
