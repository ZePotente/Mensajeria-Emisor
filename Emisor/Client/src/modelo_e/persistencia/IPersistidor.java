package modelo_e.persistencia;

public interface IPersistidor {
    public void guardarDatos(Object object);
    public Object recuperarDatos();
}
