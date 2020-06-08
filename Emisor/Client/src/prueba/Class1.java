package prueba;

import modelo_e.encriptacion.EncriptacionCesarStrategy;
import modelo_e.encriptacion.IEncriptacionStrategy;

public class Class1 {
    public Class1() {
        super();
    }
    
    public static void main(String[] args) {
        String cuerpo, asunto;
        asunto = "Asunto"; cuerpo = "Hola.";
        IEncriptacionStrategy encriptador = new EncriptacionCesarStrategy();
        
        System.out.println(asunto + cuerpo);
        System.out.println(encriptador.encriptar(asunto, cuerpo));
    }
}
