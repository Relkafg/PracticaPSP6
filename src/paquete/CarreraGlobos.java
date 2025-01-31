package paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CarreraGlobos extends JFrame {
    private List<Globo> globos;
    private Techo techo;
    private List<Integer> ranking;
    private boolean carreraIniciada = false;
    private boolean carreraTerminada = false;
    private JButton startButton;

    public CarreraGlobos() {
        setTitle("Carrera de Globos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        globos = new ArrayList<>();
        ranking = new ArrayList<>();
        techo = new Techo();

        startButton = new JButton("Iniciar Carrera");
        startButton.setBounds(220, 500, 160, 40);
        startButton.addActionListener(e -> iniciarCarrera());
        add(startButton);

        setLayout(null);
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
            techo.start();
        }
    }

    public synchronized void registrarLlegada(int id) {
        if (!ranking.contains(id)) {
            ranking.add(id);
        }
        if (ranking.size() == globos.size()) {
            carreraTerminada = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 50, getWidth(), 10);

        for (Globo globo : globos) {
            globo.dibujar(g);
        }

        if (carreraTerminada) {
            mostrarPodio(g);
        }
    }

    private void mostrarPodio(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Clasificación Final:", 200, 300);

        String[] medallas = {"Oro", "Plata", "Bronce", "4to Lugar"};
        for (int i = 0; i < ranking.size(); i++) {
            g.drawString("Globo " + (ranking.get(i)+1) + " - " + medallas[i], 220, 330 + i * 30);
        }
    }

    public static void main(String[] args) {
        new CarreraGlobos();
    }
}

