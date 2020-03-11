import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class Gardener implements Runnable {
    private Garden garden;
    private Lock writeLock;

    Gardener(Garden garden, ReadWriteLock readWriteLock) {
        this.garden = garden;
        writeLock = readWriteLock.writeLock();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            writeLock.lock();

            for (int i = 0; i < garden.getRowsNumber(); i++) {
                for (int j = 0; j < garden.getColumnsNumber(); j++) {
                    if (garden.getValue(i, j) < 3)
                        garden.incrementValue(i, j);
                }
            }
            writeLock.unlock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
