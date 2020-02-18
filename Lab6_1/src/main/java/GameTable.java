import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameTable {
    private final int rows;
    private final int columns;
    private final int cellSize;
    private final GraphicsContext graphics;

    private List<List<Integer>> grid;

    public GameTable(int rows, int columns, int cellSize, GraphicsContext graphics) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.graphics = graphics;

        grid = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            grid.add(new ArrayList<>(columns));
            for (int j = 0; j < columns; j++) {
                grid.get(i).add(0);
            }
        }
        System.out.println(rows + " " + columns);
    }

    public void start() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid.get(i).set(j, 0);
            }
        }
        draw();
    }

    public void setCellState(double xPos, double yPos) {
        int i = (int) (xPos / cellSize);
        int j = (int) (yPos / cellSize);

        if (grid.get(i).get(j) == 0)
            grid.get(i).set(j, 1);
        else
            grid.get(i).set(j, 0);

        draw();
    }

    void draw() {
        graphics.setFill(Color.LIGHTSALMON);
        graphics.fillRect(0, 0, cellSize * rows, cellSize * columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid.get(i).get(j) == 0) {
                    graphics.setFill(Color.LAVENDER);
                } else {
                    graphics.setFill(Color.LIGHTSKYBLUE);
                }
                graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
            }
        }
    }

    List<List<Integer>> getMatrix() {
        return grid;
    }

    void resetMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid.get(i).set(j, 0);
            }
        }
        draw();
    }
}
