package ventanas;


import controladores.ControladorVentanaMensaje;

import modelo.agenda.Usuario;

import vistas.InterfazVistaMensaje;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaMensaje extends JFrame implements InterfazVistaMensaje {
    private String[] tiposDeMensaje = {"Simple","Alarma","Recepcion"};
    private ControladorVentanaMensaje controlador;
    private JButton enviarBoton = new JButton("Enviar");
    private DefaultListModel<Usuario> listaDestinatariosModelo = new DefaultListModel<>();
    private DefaultListModel<String> listaNotificacionesModelo = new DefaultListModel<>();
    private JButton botonEnviar = new JButton("Enviar");
    private JTextArea cuerpoTextArea = new JTextArea();
    private JTextField asuntoTextField = new JTextField();
    private JList listaDestinatarios = new JList();
    private JComboBox listaTiposDeMensajes = new JComboBox(tiposDeMensaje);
    private JButton botonActualizar = new JButton("Actualizar");
    
    public VentanaMensaje(String args0) {
        super(args0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contenedorPrincipal = this.getContentPane();
        contenedorPrincipal.setLayout(new BorderLayout());
        
        // Creo paneles
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(new EmptyBorder(10,10,10,10));
        panelSuperior.setLayout(new GridLayout(1,2,1,1));
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(new EmptyBorder(10,10,10,10));
        panelInferior.setLayout(new BorderLayout());
        
        // Seteo accion a los botones
        botonEnviar.setActionCommand("EnviarClickeado");
        botonActualizar.setActionCommand("ActualizarClickeado");
        
        // Agrego los paneles al contenedor principal
        contenedorPrincipal.add(panelSuperior, BorderLayout.CENTER);
        contenedorPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        // Modifico el panel inferior
        JLabel notificationesLabel = new JLabel("Notificaciones");
        JList<String> listaNotificaciones = new JList<>(listaNotificacionesModelo);
        listaNotificaciones.setCellRenderer(getRenderer());
        JScrollPane scrollListaNotificaciones = new JScrollPane(listaNotificaciones);
        panelInferior.add(notificationesLabel, BorderLayout.NORTH);
        panelInferior.add(scrollListaNotificaciones, BorderLayout.CENTER);
        
        // Modifico el panel superior
        JPanel panelMensaje = new JPanel();
        panelMensaje.setLayout(new BorderLayout());
        panelMensaje.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        // Me encargo de la parte norte del panel mensaje
        JPanel panelMensajeSuperior = new JPanel();
        panelMensajeSuperior.setLayout(new GridLayout(3,1,1,1));
        JLabel mensajeLabel = new JLabel("Mensaje");
        mensajeLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
        JLabel asuntoLabel = new JLabel("Asunto");
        panelMensajeSuperior.add(mensajeLabel);
        panelMensajeSuperior.add(asuntoLabel);
        panelMensajeSuperior.add(asuntoTextField);
        
        // Me encargo de la parte centro del panel mensaje
        JPanel panelMensajeCentral = new JPanel();
        panelMensajeCentral.setLayout(new GridLayout(1,1,1,1));
        JPanel panelCuerpoMensaje = new JPanel();
        panelCuerpoMensaje.setLayout(new BorderLayout());
        JLabel cuerpoLabel = new JLabel("Cuerpo");
        cuerpoTextArea.setWrapStyleWord(true);
        cuerpoTextArea.setLineWrap(true);
        cuerpoTextArea.setBorder(BorderFactory.createLineBorder(Color.gray));
        panelCuerpoMensaje.add(cuerpoLabel, BorderLayout.NORTH);
        JScrollPane scrollCuerpoTextArea = new JScrollPane(cuerpoTextArea);
        panelCuerpoMensaje.add(scrollCuerpoTextArea);
        panelMensajeCentral.add(panelCuerpoMensaje);
        
        // Me encargo de la parte inferior del panel mensaje
        JPanel panelMensajeInferior = new JPanel();
        panelMensajeInferior.setLayout(new GridLayout(2,1,1,1));
        panelMensajeInferior.setAlignmentX(SwingConstants.CENTER);
        panelMensajeInferior.add(listaTiposDeMensajes);
        panelMensajeInferior.add(botonEnviar);
        
        // Completo el panel mensaje
        panelMensaje.add(panelMensajeSuperior, BorderLayout.NORTH);
        panelMensaje.add(panelMensajeCentral, BorderLayout.CENTER);
        panelMensaje.add(panelMensajeInferior, BorderLayout.SOUTH);
        
        // Me encargo del panel derecho (Lista de destinatarios)
        JPanel panelDestinatarios = new JPanel();
        panelDestinatarios.setLayout(new BorderLayout());
        //panelDestinatarios.setLayout(new GridLayout(2,1,1,1));
        JLabel destinatariosLabel = new JLabel("Destinatarios");
        destinatariosLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        
        // Agrego borde entre celdas de la JList destinatarios
        listaDestinatarios.setCellRenderer(getRenderer());
        
        // Seteo el modelo de la JList destinatarios
        
        listaDestinatarios.setModel(listaDestinatariosModelo);
        JScrollPane scrollListaDestinatarios = new JScrollPane(listaDestinatarios);
        scrollListaDestinatarios.setBorder(BorderFactory.createLineBorder(Color.gray));
        panelDestinatarios.add(destinatariosLabel, BorderLayout.NORTH);
        panelDestinatarios.add(scrollListaDestinatarios);
        panelDestinatarios.add(botonActualizar, BorderLayout.SOUTH);
        
        // Agrego los componentes al panel superior
        panelSuperior.add(panelDestinatarios);
        panelSuperior.add(panelMensaje);
        
    }

    public void setControlador(ActionListener controlador) {
        this.controlador = (ControladorVentanaMensaje) controlador;
        botonEnviar.addActionListener(controlador);
        botonActualizar.addActionListener(controlador);
    }

    public void abrir() {
        pack();
        setBounds(600,600,600,600);
        setLocationRelativeTo(null);
        setVisible(true);
        cargarDestinatariosPosibles();
    }

    public void cerrar() {
        setVisible(false);
    }
    
    public String getAsunto() {
        return asuntoTextField.getText();
    }
    
    public String getDescripcion() {
        return cuerpoTextArea.getText();
    }
    
    public String getTipoDeMensaje() {
        return (String)listaTiposDeMensajes.getSelectedItem();
    }
    
    public ArrayList<Usuario> getDestinatarios() {
        ArrayList<Usuario> destinatarios = new ArrayList<Usuario>();
        List<Usuario> seleccionados = listaDestinatarios.getSelectedValuesList();
        for (Usuario seleccionado: seleccionados) {
            destinatarios.add(seleccionado);
        }
        return destinatarios;
    }
    
    public void agregarNotification(Usuario receptor) {
        listaNotificacionesModelo.addElement(receptor.getNombre()+" recibio el mensaje");
    }
    
    private void cargarDestinatariosPosibles() {
        controlador.actualizarlistaDestinatarios();
    }
    
    public void actualizarListaDestinatarios(ArrayList<Usuario> destinatarios) {
        listaDestinatariosModelo.removeAllElements();
        if (destinatarios != null) {
            for (Usuario destinatario: destinatarios) {
                listaDestinatariosModelo.addElement(destinatario);
            }
        }
    }
    
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
          @Override
          public Component getListCellRendererComponent(JList<?> list,
              Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel listCellRendererComponent = (JLabel) super
                .getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
            listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0,
                0, 1, 0, Color.gray));
            return listCellRendererComponent;
          }
        };
    }
}
