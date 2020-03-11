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

    CellsFragmentTable(FragmentMatrix fragmentMatrix, ReadWriteLock readWriteLock, CyclicBarrier barrier) {
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
            readLock.lock();
            isChanged = false;
            for (int i = fragmentMatrix.getXStart(); i < fragmentMatrix.getXEnd(); i++) {
                for (int j = fragmentMatrix.getYStart(); j < fragmentMatrix.getYEnd(); j++) {
                    newCellState = cellState(i, j);
                    if (newCellState != matrix.get(i).get(j)) {
                        isChanged = true;
                        tmpMatrix[i - fragmentMatrix.getXStart()][j - fragmentMatrix.getYStart()] = newCellState;
                    }
                }
            }
            readLock.unlock();

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                barrier.reset();
                Thread.currentThread().interrupt();
            }

            if (isChanged) {
                Lock writeLock = readWriteLock.writeLock();
                writeLock.lock();
                for (int i = fragmentMatrix.getXStart(); i < fragmentMatrix.getXEnd(); i++) {
                    for (int j = fragmentMatrix.getYStart(); j < fragmentMatrix.getYEnd(); j++) {
                        fragmentMatrix.getFragmentMatix().get(i).set(j, tmpMatrix[i - fragmentMatrix.getXStart()][j - fragmentMatrix.getYStart()]);
                    }
                }
                writeLock.unlock();
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                barrier.reset();
                Thread.currentThread().interrupt();
            }
        }
    }

    private int cellState(int x, int y) {
        int xLeft, yLeft, xRight, yRight;
        if (x - 1 < 0) xLeft = x;
        else xLeft = x - 1;
        if (y - 1 < 0) yLeft = y;
        else yLeft = y - 1;
        if (x + 1 >= fragmentMatrix.getFragmentMatix().size()) xRight = x;
        else xRight = x + 1;
        if (y + 1 >= fragmentMatrix.getFragmentMatix().get(0).size()) yRight = y;
        else yRight = y + 1;


        int aliveCells = 0;
        for (int i = xLeft; i <= xRight; i++) {
            for (int j = yLeft; j <= yRight; j++) {
                if (!(i == x && j == y) && fragmentMatrix.getFragmentMatix().get(i).get(j) != 0) {
                    aliveCells++;
                }
            }
        }

        if (aliveCells != 0)
            System.out.println(aliveCells);
        if (fragmentMatrix.getFragmentMatix().get(x).get(y) == 0 && aliveCells == 3)
            return 1;
        if (fragmentMatrix.getFragmentMatix().get(x).get(y) != 0 && aliveCells >= 2 && aliveCells <= 3)
            return 1;
        return 0;
    }
}
