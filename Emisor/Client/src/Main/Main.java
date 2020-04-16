package Main;

import Controladores.ControladorVentanaMensaje;

import Ventanas.VentanaMensaje;

public class Main {
    public Main() {
        super();
    }

    public static void main(String[] args) {
        VentanaMensaje ventana = new VentanaMensaje("Elegir modo usuario");
        ControladorVentanaMensaje controlador = new ControladorVentanaMensaje(ventana);
        ventana.setControlador(controlador);
        ventana.abrir();
    }
}
