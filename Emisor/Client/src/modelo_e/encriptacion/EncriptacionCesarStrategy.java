package modelo_e.encriptacion;

public class EncriptacionCesarStrategy implements IEncriptacionStrategy {
    private final int DESPLAZAMIENTO = 1;
    public EncriptacionCesarStrategy() {
        super();
    }

    @Override
    public String encriptar(String cadena) {
        char car;
        String cifrado = "";
        // no importa el algoritmo ni la eficiencia, nomas que lo cifre.
        // Si queres algo mas eficiente o lindo hace tu propia clase Strategy.
        for (int i = 0; i < cadena.length(); i++) {
            car = cadena.charAt(i);
            if (isMinuscula(car))
                car = this.desplazar('a', car);
            else 
                if (isMayuscula(car))
                    car = this.desplazar('A', car);
            cifrado += car;
        }
        return cifrado;
    }
    private boolean isMinuscula(char car) {
        return car >= 'a' && car <= 'z';
    }
    
    private boolean isMayuscula(char car) {
        return car >= 'A' && car <= 'Z';
    }
    
    private char desplazar(char base, char car) {
        return (char) (base + (car + this.DESPLAZAMIENTO - base) % 26);
    }
}
