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

    private volatile int semaphore = 0;
    private double position = 50.0;

    @FXML
    private void initialize() {
        slider.setValue(50.0);
        slider.setBlockIncrement(1.0d);
        slider.setMax(100.0d);
        slider.setMin(0.0d);

        setUpButtons(false, false, true, true);
    }

    @FXML
    public void startThreadOne() {
        thread1 = initializeThread(1, "Second thread is running.", 10.0, false, true);
        thread1.setDaemon(true);
        thread1.setPriority(1);
        thread1.start();
    }

    @FXML
    public void startThreadTwo() {
        thread2 = initializeThread(2, "First thread is running.", 90.0, true, false);
        thread2.setDaemon(true);
        thread2.setPriority(10);
        thread2.start();
    }

    @FXML
    public void stopThreadOne() {
        semaphore = 0;
    }

    @FXML
    public void stopThreadTwo() {
        semaphore = 0;
    }

    private Thread initializeThread(int semaphoreValue, String messageText, double targetPosition, boolean disabledStop1, boolean disabledStop2) {
        return new Thread(() -> {
            if (semaphore == 0)
                semaphore = semaphoreValue;
            else if (semaphore != semaphoreValue)
                message.setText(messageText);
            if (semaphore == semaphoreValue) {
                setUpButtons(true, true, disabledStop1, disabledStop2);
                while ((int) position != (int) targetPosition && semaphore != 0) {
                    Thread.yield();
                    if (position < targetPosition)
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
                semaphore = 0;
                setUpButtons(false, false, false, false);
            }
        });
    }

    private void setUpButtons(boolean disabledStart1, boolean disabledStart2, boolean disabledStop1, boolean disabledStop2) {
        start1.setDisable(disabledStart1);
        start2.setDisable(disabledStart2);
        stop1.setDisable(disabledStop1);
        stop2.setDisable(disabledStop2);
    }
}
