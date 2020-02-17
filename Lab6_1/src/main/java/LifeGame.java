import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LifeGame extends Application {
    private static final int width = 600;
    private static final int height = 600;
    private static final int toolPaneHeight = 100;
    private static final int buttonWidth = 50;

    private static int cellSize = 10;

    private VBox root;
    private Scene scene;
    private GameTable gameTable;

    boolean isRunning;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        isRunning = false;

        root = new VBox();
        scene = new Scene(root, width, height + toolPaneHeight);
        final Canvas canvas = new Canvas(width, height);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        this.gameTable = new GameTable(height / cellSize, width / cellSize, cellSize, graphics);
        root.getChildren().add(canvas);

        setUpBottomPane();

        setCanvasListener(canvas);

        stage.setScene(scene);
        stage.show();

        gameTable.start();
    }

    private void setUpBottomPane() {
        HBox box = new HBox(30);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.BASELINE_CENTER);

        Button start = new Button("Start");
        start.setMinWidth(buttonWidth);
        Button stop = new Button("Stop");
        stop.setMinWidth(buttonWidth);
        Button reset = new Button("Reset");
        reset.setMinWidth(buttonWidth);

        setUpButtons(start, stop, reset);
        box.getChildren().addAll(start, stop, reset);

        Slider slider = new Slider(1, 10, 5);
        setSliderListener(slider);

        box.getChildren().addAll(new Label("Slow"), slider, new Label("Fast"));

        root.getChildren().add(box);
    }

    private void setUpButtons(Button start, Button stop, Button reset) {
        List<Thread> threads = null;

        start.setOnMouseClicked(mouseEvent -> {
            stop.setDisable(false);
            start.setDisable(true);
            startGame(threads);
        });

        stop.setDisable(true);
        stop.setOnMouseClicked(mouseEvent -> {
            stop.setDisable(true);
            start.setDisable(false);
            stopGame();
        });

        reset.setOnMouseClicked(mouseEvent -> gameTable.resetMatrix());
        //TODO: on click listeners
    }

    private void setCanvasListener(Canvas canvas) {
        canvas.setOnMouseClicked(mouseEvent -> gameTable.setCellState(mouseEvent.getX(), mouseEvent.getY()));
    }

    private void setSliderListener(Slider slider) {
        slider.setOnDragDone(dragEvent -> gameTable.setPause((int) (11 - slider.getValue()) * 1000));
    }

    private void startGame(List<Thread> threads) {
        isRunning = true;
    }

    private void stopGame(List<Thread> threads) {
        isRunning = false;
        for(int i=0;i<threads.size();i++){
            //threads.
        }
    }
}
