import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    @FXML
    private Slider slider;
    @FXML
    private Spinner<Integer> priorityThread1, priorityThread2;
    @FXML
    private Label sliderValue;
    @FXML
    private Button button;

    private Thread thread1, thread2;

    private double position = 50.0;

    private volatile boolean flag = true;

    @FXML
    private void initialize() {
        priorityThread1.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));
        priorityThread2.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));

        slider.setValue(50.0);
        slider.setBlockIncrement(1.0d);
        slider.setMax(100.0d);
        slider.setMin(0.0d);

    }

    @FXML
    public void onClick() {
        startThreads();
    }

    private Thread initializeThread(double targetPosition) {
        return new Thread(() -> {
            while (flag) {
                Thread.yield();
                if ((int) position < (int) targetPosition)
                    position++;
                else
                    position--;
                Platform.runLater(() -> {
                    slider.setValue(position);
                    sliderValue.setText(Double.toString(position));
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeThreads() {
        thread1 = initializeThread(10.0);
        thread2 = initializeThread(90.0);

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        priorityThread1.valueProperty().addListener(
                (observableValue, integer, newValue) -> thread1.setPriority(newValue));

        priorityThread2.valueProperty().addListener(
                (observableValue, integer, newValue) -> thread1.setPriority(newValue));
    }

    private void startThreads() {
        if (thread1 == null || thread2 == null || !thread1.isAlive() || !thread2.isAlive()){
            initializeThreads();
            button.setText("Stop");
            position=slider.getValue();
            slider.setDisable(true);
            thread1.start();
            thread2.start();
            flag = true;
        }
        else {
            button.setText("Start");
            slider.setDisable(false);
            flag=false;
        }
    }

}
