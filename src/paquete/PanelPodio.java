package paquete;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelPodio extends JPanel {
    private List<Integer> ranking;
    Color bronce = new Color(205, 127, 50); // RGB para un tono bronce

    public PanelPodio(List<Integer> ranking) {
        this.ranking = ranking;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Posiciones del podio (1°, 2°, 3°)
        int[] alturas = {60, 90, 120}; // Alturas de los escalones
        int[] posicionesX = {150, 70, 230}; // Posiciones X de cada escalón
        Color[] colores = {Color.YELLOW, Color.GRAY, bronce}; // Oro, Plata y Bronce

        for (int i = 0; i < Math.min(3, ranking.size()); i++) {
            g.setColor(colores[i]);
            g.fillRect(posicionesX[i], alturas[i], 80, 200 - alturas[i]); // Escalón
            g.setColor(Color.BLACK);
            g.drawRect(posicionesX[i], alturas[i], 80, 200 - alturas[i]); // Borde
            g.drawString("Globo " + (ranking.get(i) + 1), posicionesX[i] + 20, alturas[i] - 10); // Nombre del globo
        }

        // Título
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("¡Podio de la Carrera!", 100, 30);
    }
}
