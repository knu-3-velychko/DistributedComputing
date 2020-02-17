import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class CellsFragmentTable implements Runnable {

    private FragmentMatrix fragmentMatrix;
    private ReadWriteLock readWriteLock;
    private CyclicBarrier barrier;

    private int[][] tmpMatrix;

    private CellsFragmentTable(FragmentMatrix fragmentMatrix, ReadWriteLock readWriteLock, CyclicBarrier barrier) {
        this.fragmentMatrix = fragmentMatrix;

        this.readWriteLock = readWriteLock;
        this.barrier = barrier;

        tmpMatrix = new int[fragmentMatrix.getWidth()][fragmentMatrix.getHeight()];
    }

    @Override
    public void run() {
        boolean isChanged;
        int newCellState;
        List<List<Integer>> matrix = fragmentMatrix.getFragmentMatix();
        while (!Thread.currentThread().isInterrupted()) {
            Lock readLock = readWriteLock.readLock();
            isChanged = false;
            for (int i = fragmentMatrix.getXStart(); i < fragmentMatrix.getXEnd(); i++) {
                for (int j = fragmentMatrix.getYStart(); j < fragmentMatrix.getYEnd(); j++) {
                    newCellState = cellState(i, j);
                    if (newCellState != matrix.get(i).get(j)) {
                        isChanged = true;
                        tmpMatrix[i][j] = newCellState;
                    }
                    matrix.get(i).set(j, cellState(i, j));
                }
            }
            readLock.unlock();

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            if (isChanged) {
                Lock writeLock = readWriteLock.writeLock();
                for (int i = fragmentMatrix.getXStart(); i < fragmentMatrix.getXEnd(); i++) {
                    for (int j = fragmentMatrix.getYStart(); j < fragmentMatrix.getYEnd(); j++) {
                        fragmentMatrix.getFragmentMatix().get(i).set(j, tmpMatrix[i][j]);
                    }
                }
                writeLock.unlock();
            }
        }
    }

    private int cellState(int x, int y) {
        int xLeft, yLeft, xRight, yRight;
        if (x - 1 < fragmentMatrix.getXStart()) xLeft = x;
        else xLeft = x - 1;
        if (y - 1 < fragmentMatrix.getYStart()) yLeft = y;
        else yLeft = y - 1;
        if (x + 1 > fragmentMatrix.getYStart()) xRight = x;
        else xRight = x + 1;
        if (y + 1 > fragmentMatrix.getYEnd()) yRight = y;
        else yRight = y + 1;

        int aliveCells = 0;
        for (int i = xLeft; i < xRight; i++) {
            for (int j = yLeft; j < yRight; j++) {
                if (i != j && fragmentMatrix.getFragmentMatix().get(i).get(j) != 0) {
                    aliveCells++;
                }
            }
        }

        if (fragmentMatrix.getFragmentMatix().get(x).get(y) == 0 && aliveCells > 3)
            return 1;
        if (fragmentMatrix.getFragmentMatix().get(x).get(y) != 0 && aliveCells >= 2 && aliveCells <= 3)
            return 1;
        return 0;
    }
}
