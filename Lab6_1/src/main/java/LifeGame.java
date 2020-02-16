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

        box.getChildren().addAll(start, stop, reset);

        Slider slider = new Slider(1, 10, 5);

        box.getChildren().addAll(new Label("Slow"), slider, new Label("Fast"));

        root.getChildren().add(box);
    }

    private void setUpButtons() {
        //TODO: on click listeners
    }

    private void setUpSlider() {
        //TODO: add slider listener
    }

    private void setCanvasListener(Canvas canvas) {
        canvas.setOnMouseClicked(mouseEvent -> gameTable.setCellState(mouseEvent.getX(), mouseEvent.getY()));
    }
}
