import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GamePanel extends JPanel {
    private int width;
    private int height;

    private int points = 0;
    private int maxPoints = 0;
    private JLabel score;
    private JLabel bestScore;
    private ImageIcon background = new ImageIcon(getClass().getResource("background.png"));
    private final int minimumDucks = 2;
    private static final int maxBullets = 5;
    private int maxDucks = minimumDucks;

    public ConcurrentLinkedQueue<Duck> getDucks() {
        return ducks;
    }

    private ConcurrentLinkedQueue<Duck> ducks = new ConcurrentLinkedQueue<>();

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    private Hunter hunter = null;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    private MainFrame mainFrame;

    GamePanel(MainFrame mainFrame) {
        setBackground(Color.WHITE);
        this.mainFrame = mainFrame;
        this.width = mainFrame.getWidth();
        this.height = mainFrame.getHeight();

        setLayout(null);
        setSize(width, height);

        score = setupText("Score: 0", 22, Color.WHITE, 200, 100, 10, height - 630);
        this.add(score);

        bestScore = setupText("Score: 0", 22, Color.WHITE, 200, 100, 10, height - 600);
        this.add(bestScore);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("sight.png"));
        Cursor cursor = toolkit.createCustomCursor(image, new Point(getX() + 14, getY() + 14), "green-sight");
        setCursor(cursor);

        GameMouseAdapter mouseAdapter = new GameMouseAdapter(this);
        addMouseListener(mouseAdapter);
        GameRunner gameRunner=new GameRunner(this);
        gameRunner.start();
    }

    private JLabel setupText(String text, int fontSize, Color color, int width, int height, int x, int y) {
        JLabel label = new JLabel(text);
        label.setVisible(true);
        label.setFont((new Font("Monserrat", Font.PLAIN, fontSize)));
        label.setForeground(color);
        label.setSize(width, height);
        label.setLocation(x, y);
        return label;
    }

    synchronized int getMaxDucks() {
        return maxDucks;
    }

    synchronized int getMaxBullets() {
        return maxBullets;
    }

    synchronized void changedPoints(int d) {
        points += d;
        if (points < 0) {
            points = 0;
        }
        if (maxPoints < points) {
            maxPoints = points;
        }

        int pointsLog2 = (int) Math.floor(Math.log(points) / Math.log(2.0));
        int anotherPoints = (int) Math.pow(2.0, pointsLog2);

        if (anotherPoints == points) {
            maxDucks = pointsLog2;
        }
        if (maxDucks < minimumDucks) {
            maxDucks = minimumDucks;
        }

        score.setText("Score: " + points);
        bestScore.setText("Best Score: " + maxPoints);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(background.getImage(), 0, 0, width, height, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(width, height);
    }

    void removeDuck(Duck duck) {
        ducks.remove(duck);
    }

    Hunter getHunter() {
        return hunter;
    }
}
