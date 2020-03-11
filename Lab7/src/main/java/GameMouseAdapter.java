import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseAdapter extends MouseAdapter {
    private GamePanel panel;
    private static final int maxRadius = 100;

    GameMouseAdapter(GamePanel panel) {
        this.panel = panel;
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    @Override
    public void mouseReleased(MouseEvent e) {
        int rad = 0;
        if (e.isAltDown()) rad = maxRadius;

        int x = e.getX();
        int y = e.getY();
        for (Duck duck : panel.getDucks()) {
            synchronized (duck) {
                if (x >= duck.getX() - rad
                        && x <= duck.getX() + duck.getSizeX() + rad
                        && y >= duck.getY() - rad
                        && y <= duck.getY() + duck.getSizeY() + rad) duck.interrupt();
            }
        }
    }
}
