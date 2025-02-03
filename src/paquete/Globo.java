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
    private boolean pausado = false;
    private static final int GLOBO_SIZE = 80; // Aumentamos el tamaño de detección

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
            g.drawImage(globoImage, x, y, GLOBO_SIZE, GLOBO_SIZE, null);
        }
    }

    @Override
    public void run() {
        while (y > 60) {
            if (!pausado) {
                y -= (int) (Math.random() * velocidad + 1);
                x += (Math.random() > 0.5 ? 1 : -1) * (Math.random() * 3);
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            SwingUtilities.invokeLater(() -> frame.repaint());
        }
        terminado = true;
        frame.registrarLlegada(id);
    }

    public void pausarMedioSegundo() {
        System.out.println("Globo ha sido clicado y se detendrá medio segundo.");
        new Thread(() -> {
            pausado = true;
            System.out.println("Globo " + id + " ha sido clicado y se detendrá medio segundo.");
            //JOptionPane.showMessageDialog(frame, "Globo " + id + " ha sido clicado y se detendrá medio segundo.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            pausado = false;
        }).start();
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public boolean contienePunto(int px, int py) {
        return px >= x && px <= x + GLOBO_SIZE && py >= y && py <= y + GLOBO_SIZE;
    }
}
