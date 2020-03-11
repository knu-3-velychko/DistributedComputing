import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LifeGame extends Application {
    private static final int width = 600;
    private static final int height = 600;
    private static final int toolPaneHeight = 100;
    private static final int buttonWidth = 50;

    private static int cellSize = 10;
    private static int fragmentSize = 10;

    private VBox root;
    private Scene scene;
    private GameTable gameTable;

    boolean isRunning;

    private int currentColor;

    private final Color[] list = new Color[]{null, Color.BLACK, Color.PERU, Color.POWDERBLUE, Color.CORAL, Color.GOLD};

    private int pause = 5_00;

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
        this.gameTable = new GameTable(height / cellSize, width / cellSize, cellSize, graphics, list);
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

        root.getChildren().add(box);

        ToggleGroup group = new ToggleGroup();
        RadioButton button;

        for (int i = 1; i < list.length; i++) {
            button = new RadioButton();
            button.setText(Integer.toString(i));
            group.getToggles().add(button);
            box.getChildren().add(button);
        }

        group.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle();
            currentColor = Integer.parseInt(chk.getText());
        });
    }

    private void setUpButtons(Button start, Button stop, Button reset) {
        List<Thread> threads = new LinkedList<>();

        start.setOnMouseClicked(mouseEvent -> {
            stop.setDisable(false);
            start.setDisable(true);
            reset.setDisable(true);
            startGame(threads);
        });

        stop.setDisable(true);
        stop.setOnMouseClicked(mouseEvent -> {
            stop.setDisable(true);
            start.setDisable(false);
            reset.setDisable(false);
            stopGame(threads);
        });

        reset.setOnMouseClicked(mouseEvent -> gameTable.resetMatrix());
    }

    private void setCanvasListener(Canvas canvas) {
        canvas.setOnMouseClicked(mouseEvent -> gameTable.setCellState(currentColor, mouseEvent.getX(), mouseEvent.getY()));
    }


    private void startGame(List<Thread> threads) {
        isRunning = true;

        for (int i = 1; i < list.length; i++) {
            CellsFragmentTable cellsFragmentTable = new CellsFragmentTable(i, gameTable);
            Thread thread = new Thread(cellsFragmentTable);
            threads.add(thread);
            thread.setDaemon(true);
            thread.start();
        }
    }

    private void stopGame(List<Thread> threads) {
        isRunning = false;
        for (Thread value : threads) {
            value.interrupt();
        }
        threads.clear();
    }
}
