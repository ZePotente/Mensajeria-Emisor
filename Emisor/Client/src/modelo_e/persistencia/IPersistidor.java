package modelo_e.persistencia;

import java.io.IOException;

import java.util.ArrayList;

public interface IPersistidor {
    public void guardarDatos(Object object);
    public Object recuperarDatos();
}