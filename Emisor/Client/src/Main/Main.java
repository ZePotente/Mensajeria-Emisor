package Main;

import Controladores.ControladorVentanaIngresaNombre;
import Controladores.ControladorVentanaMensaje;

import Ventanas.VentanaIngresaNombre;
import Ventanas.VentanaMensaje;

public class Main {
    public Main() {
        super();
    }

    public static void main(String[] args) {
        VentanaIngresaNombre ventana = new VentanaIngresaNombre("Elegir nombre");
        ControladorVentanaIngresaNombre controlador = new ControladorVentanaIngresaNombre(ventana);
        ventana.setControlador(controlador);
        ventana.abrir();
    }
}
