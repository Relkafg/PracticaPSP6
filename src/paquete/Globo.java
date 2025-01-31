package paquete;

import java.awt.*;
import javax.swing.*;

class Globo extends Thread {
    private int id;
    private int x, y;
    private final CarreraGlobos frame;
    private boolean terminado = false;
    private int velocidad = 5;
    private Image globoImage;  // Imagen del globo
    private Image explosionSprite;

    public Globo(int id, CarreraGlobos frame) {
        this.id = id;
        this.frame = frame;
        this.x = 100 + id * 100;
        this.y = 500;

        // Cargar la imagen del globo (asegúrate de tener una imagen "globo.png" en la carpeta de recursos)
        this.globoImage = new ImageIcon(getClass().getResource("/images/globo.png")).getImage();
        
        // Cargar la imagen de la explosión
        this.explosionSprite = new ImageIcon(getClass().getResource("/images/explosion.png")).getImage();
    }

    public void dibujar(Graphics g) {
        if (terminado) {
            // Si el globo terminó, dibujar la explosión
            g.drawImage(explosionSprite, x - 10, y - 10, 40, 40, null);
        } else {
            // Dibujar el globo con la imagen cargada
            g.drawImage(globoImage, x, y, 60, 60, null); // Ajusta el tamaño de la imagen según sea necesario
        }
    }

    public boolean contienePunto(int px, int py) {
        return px >= x && px <= x + 30 && py >= y && py <= y + 30;
    }

    public void reducirVelocidad() {
        velocidad = Math.max(1, velocidad - 1);
    }

    @Override
    public void run() {
        while (y > 60) {
            y -= (int) (Math.random() * velocidad + 1);
            x += (Math.random() > 0.5 ? 1 : -1) * (Math.random() * 3);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            frame.repaint(); // Llamada a repaint después de una actualización completa del globo
        }
        terminado = true;

        frame.registrarLlegada(id);
        frame.repaint(); // Repaint después de que el globo ha terminado
    }
}
