import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int frameWidth = 1000;
    private static final int frameHeight = 600;

    private MainFrame(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        this.setSize(new Dimension(frameWidth, frameHeight));
        Dimension dimension = getToolkit().getScreenSize();
        this.setLocation((dimension.width - frameWidth) / 2, (dimension.height - frameHeight) / 2);
        GamePanel panel=new GamePanel(this);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainFrame("Duck Hunt"));
    }
}
