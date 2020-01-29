import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Controller {
    @FXML
    private Button start1, start2, stop1, stop2;
    @FXML
    private Label message;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderValue;

    private Thread thread1, thread2;

    private int semafore = 0;
    private double position = 50.0;

    @FXML
    private void initialize() {
        slider.setValue(50.0);
        slider.setBlockIncrement(1.0d);
        slider.setMax(100.0d);
        slider.setMin(0.0d);

        initializeThreads();
    }

    @FXML
    public void startThreadOne() {
        initializeThreads();
        thread1.start();
    }

    @FXML
    public void startThreadTwo() {
        initializeThreads();
        thread2.start();
    }

    @FXML
    public void stopThreadOne() {
        semafore = 0;
    }

    @FXML
    public void stopThreadTwo() {
        semafore = 0;
    }

    private void initializeThreads() {
        if (thread1 == null || !thread1.isAlive())
            thread1 = new Thread(() -> {
                if (semafore == 0)
                    semafore = 1;
                if (semafore == 2)
                    message.setText("Second thread is running");
                if (semafore == 1) {
                    start2.setDisable(true);
                    stop2.setDisable(true);
                    while (position != 10 && semafore==1) {
                        Thread.yield();
                        if (position < 10.0)
                            position++;
                        else
                            position--;
                        Platform.runLater(() ->
                        {
                            sliderValue.setText(String.valueOf(position));
                            slider.setValue(position);
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    semafore = 0;
                    start2.setDisable(false);
                    stop2.setDisable(false);
                }
            });

        if (thread2 == null || !thread2.isAlive())
            thread2 = new Thread(() -> {
            if (semafore == 0)
                semafore = 2;
            if (semafore == 1)
                message.setText("First thread is running");
            if (semafore == 2) {
                start1.setDisable(true);
                stop1.setDisable(true);
                while (position != 90 && semafore==2) {
                    Thread.yield();
                    if (position < 90.0)
                        position++;
                    else
                        position--;
                    Platform.runLater(() ->
                    {
                        sliderValue.setText(String.valueOf(position));
                        slider.setValue(position);
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                semafore = 0;
                start1.setDisable(false);
                stop1.setDisable(false);
            }
        });

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.setPriority(1);
        thread2.setPriority(10);
    }
}
