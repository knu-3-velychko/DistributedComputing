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

    private Thread thread1, thread2;

    @FXML
    private void initialize() {
        priorityThread1.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));
        priorityThread2.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));

        slider.setValue(50.0);

        initializeThreads();
    }

    @FXML
    public void onClick() {
        startThreads();
    }

    private void initializeThreads() {
        thread1 = new Thread(
                () -> {
                    while (true) {
                        Thread.yield();
                        if (slider.getValue() > 10)Platform.runLater(()
                                slider.setValue(slider.getValue()-1.0);
                                sliderValue.setText(Double.toString(slider.getValue()));
                            }
                    }
                });

        thread2 = new Thread(
                () -> {
                    while (true) {
                        Thread.yield();
                        if (slider.getValue() < 90)
                            Platform.runLater(() -> {
                                slider.setValue(slider.getValue()+1.0);
                                sliderValue.setText(Double.toString(slider.getValue()));
                            });
                    }
                });

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        priorityThread1.valueProperty().addListener(
                (observableValue, integer, newValue) -> thread1.setPriority(newValue));

        priorityThread2.valueProperty().addListener(
                (observableValue, integer, newValue) -> thread1.setPriority(newValue));
    }

    private void startThreads() {
        if(!thread1.isAlive())
            thread1.start();
        if(!thread2.isAlive())
            thread2.start();
    }
}
