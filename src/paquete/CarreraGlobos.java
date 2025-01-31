package paquete;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraGlobos extends JFrame {
    private List<Globo> globos;
    private List<Integer> ranking;
    private boolean carreraIniciada = false;
    private boolean carreraTerminada = false;
    private JButton startButton;
    private PanelCarrera panelCarrera;

    public CarreraGlobos() {
        setTitle("Carrera de Globos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        globos = new ArrayList<>();
        ranking = new ArrayList<>();
        panelCarrera = new PanelCarrera(globos);

        startButton = new JButton("Iniciar Carrera");
        startButton.addActionListener(e -> iniciarCarrera());

     // Usamos un layout null para controlar manualmente las posiciones
        setLayout(null);

        // Añadimos el panel de carrera
        panelCarrera.setBounds(0, 0, getWidth(), getHeight() - 100); // Ajustamos el panel para dejar espacio al botón
        add(panelCarrera);

        // Ajustamos el tamaño del botón
        int buttonWidth = 200;
        int buttonHeight = 50;

        // Centrar el botón en la parte inferior, con un margen
        int buttonX = (getWidth() - buttonWidth) / 2;
        int buttonY = getHeight() - buttonHeight - 300; // Margen de 20 píxeles desde la parte inferior
        startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Añadir el botón a la ventana
        add(startButton);

        setVisible(true);
    }

    private void iniciarCarrera() {
        if (!carreraIniciada) {
            carreraIniciada = true;
            startButton.setVisible(false);

            for (int i = 0; i < 4; i++) {
                Globo globo = new Globo(i, this);
                globos.add(globo);
                globo.start();
            }
        }
    }

    public synchronized void registrarLlegada(int id) {
        if (!ranking.contains(id)) {
            ranking.add(id);
        }
        if (ranking.size() == globos.size()) {
            carreraTerminada = true;
            mostrarPodio();
        }
        panelCarrera.repaint(); // Asegura que la interfaz se actualice correctamente
    }

    
    // Método para mostrar el podio en un JDialog
    private void mostrarPodio() {
        JDialog podioDialog = new JDialog(this, "Resultados", true);
        podioDialog.setSize(400, 300);
        podioDialog.setLocationRelativeTo(this);
        podioDialog.setResizable(false);

        PanelPodio panelPodio = new PanelPodio(ranking);
        podioDialog.add(panelPodio);
        
        podioDialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        new CarreraGlobos();
    }
}