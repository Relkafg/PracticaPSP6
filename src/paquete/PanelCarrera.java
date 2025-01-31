package paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

class PanelCarrera extends JPanel {
    private final List<Globo> globos;
    private Image metaImage;
    private boolean listenerActivado = false;

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
    }

    // Método para activar el MouseListener
    public void activarMouseListener() {
        if (!listenerActivado) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // Verificar si se ha hecho clic sobre un globo
                    for (Globo globo : globos) {
                        if (globo.contienePunto(e.getX(), e.getY())) {
                            globo.pausarMedioSegundo(); // Pausar el globo durante medio segundo
                            break; // Solo un globo puede ser pausado a la vez
                        }
                    }
                }
            });
            listenerActivado = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
