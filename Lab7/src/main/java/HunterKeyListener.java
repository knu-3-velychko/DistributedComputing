import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HunterKeyListener implements KeyListener {
    private GamePanel panel;

    HunterKeyListener(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (panel != null) {
            if (keyEvent.getKeyCode() == 32 && panel.getHunter().getCountBullet() < panel.getMaxBullets()) {
                Bullet bullet;
                if (panel.getHunter().getSide() == 1) {
                    bullet = new Bullet(panel, panel.getHunter(), panel.getHunter().getX() + 60, panel.getHunter().getY(), false);
                } else {
                    bullet = new Bullet(panel, panel.getHunter(), panel.getHunter().getX() + Hunter.getSizeX() - 60, panel.getHunter().getY(), true);
                }
                bullet.start();
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            panel.getHunter().setKeys(true, false);
            if (panel.getHunter().getX() > 0) {
                if (panel.getHunter().getSide() != 1) {
                    panel.getHunter().setIcon(new ImageIcon(panel.getClass().getResource("hunterRL.png")));
                    panel.getHunter().setSide(1);
                }
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            panel.getHunter().setKeys(false, true);
            if (panel.getHunter().getX() < panel.getWidth() - Hunter.getSizeX()) {
                if (panel.getHunter().getSide() != 2) {
                    panel.getHunter().setIcon(new ImageIcon(panel.getClass().getResource("hunterLR.png")));
                    panel.getHunter().setSide(2);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if ((keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)) {
            panel.getHunter().setKeys(false, false);
        }
    }
}
