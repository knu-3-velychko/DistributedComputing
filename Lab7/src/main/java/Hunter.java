import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hunter implements Runnable {
    private JLabel hunterLable;
    private GamePanel panel;

    private int x, y;

    public static int getSizeX() {
        return sizeX;
    }

    public static int getSizeY() {
        return sizeY;
    }

    private static final int sizeX = 200, sizeY = 130;

    private static final int dx = 20;
    private int width;
    private volatile int countBullet = 0;

    private int side = 1;

    private boolean keyLeft = false;
    private boolean keyRight = false;

    Hunter(MainFrame frame, GamePanel panel) {
        this.panel = panel;
        this.width = panel.getWidth();
        x = panel.getWidth() / 2;
        y = panel.getHeight() - sizeY - 30;

        hunterLable = new JLabel(new ImageIcon(getClass().getResource("hunterRL.png")));
        hunterLable.setSize(new Dimension(sizeX, sizeY));
        hunterLable.setLocation(x, y);
        hunterLable.setVisible(true);
        panel.add(hunterLable);

        HunterKeyListener keyListener = new HunterKeyListener(panel);
        frame.addKeyListener(keyListener);
    }

    synchronized void throwBullet(int num) {
        countBullet += num;
    }

    synchronized int getCountBullet() {
        return countBullet;
    }

    synchronized int getSide() {
        return side;
    }

    synchronized void setKeys(boolean keyLeft, boolean keyRight) {
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
    }

    synchronized int getX() {
        return x;
    }

    synchronized int getY() {
        return y;
    }

    synchronized void setSide(int side) {
        this.side = side;
    }

    synchronized void setIcon(Icon icon) {
        hunterLable.setIcon(icon);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (keyLeft && x - dx >= 0) {
                x -= dx;
            } else if (keyRight && x + dx + sizeX <= width) {
                x += dx;
            }

            hunterLable.setLocation(x, y);

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
