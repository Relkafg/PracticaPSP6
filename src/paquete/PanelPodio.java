package paquete;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelPodio extends JPanel {
    private List<Integer> ranking;
    private Color bronce = new Color(205, 127, 50); // Color bronce
    private Image oro, plata, bronceImg; // Imágenes de las medallas

    public PanelPodio(List<Integer> ranking) {
        this.ranking = ranking;
        setPreferredSize(new Dimension(400, 300));

        // Cargar imágenes de las medallas
        oro = new ImageIcon(getClass().getResource("/images/oro.png")).getImage();
        plata = new ImageIcon(getClass().getResource("/images/plata.png")).getImage();
        bronceImg = new ImageIcon(getClass().getResource("/images/bronce.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Posiciones del podio (1°, 2°, 3°)
        int[] alturas = {60, 90, 120}; // Alturas de los escalones
        int[] posicionesX = {150, 70, 230}; // Posiciones X de cada escalón
        Color[] colores = {Color.YELLOW, Color.GRAY, bronce}; // Oro, Plata y Bronce
        Image[] medallas = {oro, plata, bronceImg}; // Array con las imágenes de las medallas

        for (int i = 0; i < Math.min(3, ranking.size()); i++) {
            g.setColor(colores[i]);
            g.fillRect(posicionesX[i], alturas[i], 80, 200 - alturas[i]); // Dibujar escalón
            g.setColor(Color.BLACK);
            g.drawRect(posicionesX[i], alturas[i], 80, 200 - alturas[i]); // Borde
            
            // Texto del ganador en el podio (ahora más abajo)
            g.drawString("Globo " + (ranking.get(i) + 1), posicionesX[i] + 15, alturas[i] - 5); 

            // Ajuste de posición de las medallas (bajamos la Y)
            int medallaX = posicionesX[i] + 10; // Centrado
            int medallaY = alturas[i] + 10; // BAJAMOS para que no se superponga al texto
            g.drawImage(medallas[i], medallaX, medallaY, 60, 60, this);
        }

        // Título
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("¡Podio de la Carrera!", 100, 30);
    }
}
