import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Duck extends Thread {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int y;

    private int speedX;
    private int speedY;

    private int width;
    private int height;

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    private final int sizeX = 128;
    private final int sizeY = 128;

    private JLabel duck;
    private GamePanel panel;

    Duck(int width, int height, GamePanel panel) {
        super();

        this.width = width;
        this.height = height - panel.getHeight() * 5 / 12;
        this.panel = panel;

        Random random = new Random();
        int duckType = Math.abs(random.nextInt()) % 2;

        duck = new JLabel(new ImageIcon(getClass().getResource((duckType == 0) ? "duckLR.png" : "duckRL.png")));

        duck.setSize(new Dimension(sizeX, sizeY));

        speedX = Math.abs(random.nextInt(3)) + 1;
        speedY = Math.abs(random.nextInt(2)) - 5;
        if (duckType == 1) speedX = -speedX;

        y = height;
        int quarterWidth = width / 4;
        x = width / 2 - quarterWidth / 2 + Math.abs(random.nextInt()) % quarterWidth - 2 * sizeX;
    }

    @Override
    public void run() {
        panel.add(duck);

        boolean flag = true;

        while (!isInterrupted() && flag) {
            int nx = x + speedX;
            int ny = y + speedY;

            if ((speedX > 0 && nx > width) || (speedX < 0 && nx < -sizeX) || (ny < -sizeY)) flag = false;

            x = nx;
            y = ny;
            duck.setLocation(x, y);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (flag) panel.changedPoints(1);
        else panel.changedPoints(-1);

        panel.remove(duck);
        panel.repaint();
        panel.removeDuck(this);
        new Thread(new Explosion(panel,x,y)).start();
    }
}
