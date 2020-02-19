import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CellsFragmentTable implements Runnable {

    private List<List<AtomicInteger>> matrix;

    private int top, bottom, left, right;

    private final int color;


    CellsFragmentTable(int color, List<List<AtomicInteger>> matrix) {
        this.color = color;
        this.matrix = matrix;
    }

    @Override
    public void run() {
        int newCellState;

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                checkCell(i, j);
            }
        }

        while (!Thread.currentThread().isInterrupted()) {
            for (int i = top; i < bottom; i++) {
                for (int j = left; j < right; j++) {
                    newCellState = cellState(i, j);
                    if (newCellState != matrix.get(i).get(j).get()) {
                        matrix.get(i).set(j, new AtomicInteger(newCellState));
                    }
                }
            }
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

        if (aliveCells != 0)
            System.out.println(aliveCells);
        if (matrix.get(x).get(y).get() == 0 && aliveCells == 3) {
            checkCell(x, y);
            return color;
        }
        if (matrix.get(x).get(y).get() == color && aliveCells >= 2 && aliveCells <= 3) {
            checkCell(x, y);
            return color;
        }
        return 0;
    }

    void checkCell(int x, int y) {
        if (x < left)
            left = x;
        if (y < top)
            top = y;
        if (x > right)
            right = x;
        if (y > bottom)
            bottom = y;
    }
}
