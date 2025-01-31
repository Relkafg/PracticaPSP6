package paquete;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

class PanelCarrera extends JPanel {
    private final List<Globo> globos;
    private Image metaImage;
    private int frames = 0;
    private int fps = 0;

    public PanelCarrera(List<Globo> globos) {
        this.globos = globos;
        setPreferredSize(new Dimension(600, 500));
        setDoubleBuffered(true); // Evita parpadeos

        // Cargar la imagen de la meta
        URL metaURL = getClass().getResource("/images/meta.png");
        if (metaURL != null) {
            metaImage = new ImageIcon(metaURL).getImage();
        } else {
            System.err.println("No se encontró la imagen de la meta.");
        }
        
     // Hilo para actualizar los FPS cada segundo
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Espera 1 segundo
                    fps = frames; // Guarda los FPS calculados
                    frames = 0; // Reinicia el contador de frames
                    repaint(); // Redibuja el panel con la nueva medición
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        // Contar frames
        frames++;
        
        // Dibujar FPS en la esquina superior izquierda
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("FPS: " + fps, 10, 20);
        
        // Dibujar la meta ajustada al ancho del JPanel
        if (metaImage != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = metaImage.getWidth(null);
            int imgHeight = metaImage.getHeight(null);

            // Calcular nueva altura proporcional a su ancho
            int newHeight = (int) ((double) imgHeight / imgWidth * panelWidth);

            // Limitar la altura para que no sea demasiado grande
            if (newHeight > panelHeight / 5) {  // La meta no debe ocupar más del 20% de la pantalla
                newHeight = panelHeight / 5;
            }

            // Dibuja la imagen en la parte superior del panel
            int yPos = 10;  // Asegura que la imagen esté en la parte superior
            g.drawImage(metaImage, 0, yPos, panelWidth, newHeight, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(0, 50, getWidth(), 10); // Línea de meta roja si no se encuentra la imagen
        }
        
        // Dibujar los globos
        for (Globo globo : globos) {
            globo.dibujar(g);
        }
    }
}
