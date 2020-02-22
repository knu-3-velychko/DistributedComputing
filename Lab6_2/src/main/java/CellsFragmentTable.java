import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CellsFragmentTable implements Runnable {

    private final GameTable gameTable;
    private List<List<AtomicInteger>> matrix;

    private final int color;


    CellsFragmentTable(int color, GameTable table) {
        this.color = color;
        this.gameTable = table;
        this.matrix = table.getMatrix();
    }

    @Override
    public void run() {
        int newCellState;
        boolean isChanged;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            isChanged = false;
            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.size(); j++) {
                    newCellState = cellState(i, j);
                    if (newCellState != matrix.get(i).get(j).get()) {
                        matrix.get(i).set(j, new AtomicInteger(newCellState));
                        isChanged = true;
                    }
                }
            }

            if (isChanged)
                gameTable.draw();
        }
    }

    private int cellState(int x, int y) {
        int xLeft, yLeft, xRight, yRight;
        if (x - 1 < 0) xLeft = x;
        else xLeft = x - 1;
        if (y - 1 < 0) yLeft = y;
        else yLeft = y - 1;
        if (x + 1 >= matrix.size()) xRight = x;
        else xRight = x + 1;
        if (y + 1 >= matrix.get(0).size()) yRight = y;
        else yRight = y + 1;


        int aliveCells = 0;
        for (int i = xLeft; i <= xRight; i++) {
            for (int j = yLeft; j <= yRight; j++) {
                if (!(i == x && j == y) && matrix.get(i).get(j).get() == color) {
                    aliveCells++;
                }
            }
        }

        if (matrix.get(x).get(y).get() != color && aliveCells == 3) {
            return color;
        }
        if (matrix.get(x).get(y).get() == color && aliveCells >= 2 && aliveCells <= 3) {
            return color;
        }
        if (matrix.get(x).get(y).get() == color && (aliveCells < 2 || aliveCells > 3))
            return 0;
        return matrix.get(x).get(y).get();
    }

}
