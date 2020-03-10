import javax.swing.*;
import java.awt.*;

public class Explosion implements Runnable {
    private JLabel explosion = new JLabel(new ImageIcon(getClass().getResource("explosion.png")));
    private GamePanel panel;

    private int x, y;
    private int sizeX = 110, sizeY = 110;

    Explosion(GamePanel panel, int x, int y) {
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        explosion.setSize(new Dimension(sizeX, sizeY));
        explosion.setLocation(x, y);
        panel.add(explosion);
        panel.repaint();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        panel.remove(explosion);
        panel.repaint();
    }
}
