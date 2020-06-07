package controladores;

import modelo.Sistema;

import modelo.agenda.Usuario;

import ventanas.VentanaMensaje;

import vistas.InterfazVistaIngresaNombre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ControladorVentanaIngresaNombre implements ActionListener {
    private InterfazVistaIngresaNombre vista;
    private Sistema sistema;

    public ControladorVentanaIngresaNombre(InterfazVistaIngresaNombre vista) {
        this.vista = vista;
        this.sistema = Sistema.getInstancia();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(InterfazVistaIngresaNombre.CONTINUAR)) {
            VentanaMensaje ventana = new VentanaMensaje("Panel Emisor");
            ControladorVentanaMensaje controlador = new ControladorVentanaMensaje(ventana);
            ventana.setControlador(controlador);
            try {
                String nroIP = InetAddress.getLocalHost().getHostAddress();
                sistema.ingresarComoEmisor(new Usuario(vista.getNombre(), nroIP));
                ventana.abrir();
                vista.cerrar();
            } catch (UnknownHostException e) {
                System.out.println("Error al obtener el numero de IP");
            }
        }
    }
}
