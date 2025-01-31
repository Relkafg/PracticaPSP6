package paquete;

import javax.swing.*;
import java.awt.*;

class Globo extends Thread {
    private int id;
    private int x, y;
    private final CarreraGlobos frame;
    private boolean terminado = false;
    private int velocidad = 5;
    private Image globoImage;  
    private Image explosionSprite;

    public Globo(int id, CarreraGlobos frame) {
        this.id = id;
        this.frame = frame;
        this.x = 100 + id * 100;
        this.y = 500;

        this.globoImage = new ImageIcon(getClass().getResource("/images/globo.png")).getImage();
        this.explosionSprite = new ImageIcon(getClass().getResource("/images/explosion.png")).getImage();
    }

    public void dibujar(Graphics g) {
        if (terminado) {
            g.drawImage(explosionSprite, x - 10, y - 10, 40, 40, null);
        } else {
            g.drawImage(globoImage, x, y, 60, 60, null);
        }
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

            SwingUtilities.invokeLater(() -> frame.repaint());
        }
        terminado = true;
        frame.registrarLlegada(id);
    }
}
