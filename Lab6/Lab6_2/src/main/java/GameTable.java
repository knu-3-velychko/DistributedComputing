import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTable {
    private final int rows;
    private final int columns;
    private final int cellSize;
    private final GraphicsContext graphics;

    private List<List<AtomicInteger>> grid;

    private final Color[] list;

    public GameTable(int rows, int columns, int cellSize, GraphicsContext graphics, Color[] list) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.graphics = graphics;
        this.list = list;

        grid = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            grid.add(new ArrayList<>(columns));
            for (int j = 0; j < columns; j++) {
                grid.get(i).add(new AtomicInteger(0));
            }
        }
        System.out.println(rows + " " + columns);
    }

    public void start() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid.get(i).set(j, new AtomicInteger(0));
            }
        }
        draw();
    }

    public void setCellState(int color, double xPos, double yPos) {
        int i = (int) (xPos / cellSize);
        int j = (int) (yPos / cellSize);

        if (grid.get(i).get(j).get() == color)
            grid.get(i).set(j, new AtomicInteger(0));
        else
            grid.get(i).set(j, new AtomicInteger(color));
        draw();
    }

    synchronized void draw() {
        graphics.setFill(Color.LIGHTSALMON);
        graphics.fillRect(0, 0, cellSize * rows, cellSize * columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid.get(i).get(j).get() == 0) {
                    graphics.setFill(Color.LAVENDER);
                } else {
                    graphics.setFill(list[grid.get(i).get(j).get()]);
                }
                graphics.fillRect((i * cellSize) + 1, (j * cellSize) + 1, cellSize - 2, cellSize - 2);
            }
        }
    }

    List<List<AtomicInteger>> getMatrix() {
        return grid;
    }

    void resetMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid.get(i).set(j, new AtomicInteger(0));
            }
        }
        draw();
    }
}
