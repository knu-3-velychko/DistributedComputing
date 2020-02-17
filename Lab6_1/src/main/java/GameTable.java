import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameTable {
    private final int rows;
    private final int columns;
    private final int cellSize;
    private final GraphicsContext graphics;

    private final int minPause = 1_000;
    private final int maxPause = 10_000;
    private int pause;

    private int[][] grid;

    public GameTable(int rows, int columns, int cellSize, GraphicsContext graphics) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.graphics = graphics;

        grid = new int[rows][columns];
    }

    public void start() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = 0;
            }
        }
        draw();
    }

    public void setCellState(double xPos, double yPos) {
        int i = (int) (xPos / cellSize);
        int j = (int) (yPos / cellSize);

        if (grid[i][j] == 0)
            grid[i][j] = 1;
        else
            grid[i][j] = 0;

        draw();
    }

    private void draw() {
        graphics.setFill(Color.LIGHTSALMON);
        graphics.fillRect(0, 0, cellSize * rows, cellSize * columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 0) {
                    graphics.setFill(Color.LAVENDER);
                } else {
                    graphics.setFill(Color.LIGHTSKYBLUE);
                }
                graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
            }
        }
    }

    void setPause(int pause) {
        this.pause = pause;
    }

    void resetMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = 0;
            }
        }
        draw();
    }
}
