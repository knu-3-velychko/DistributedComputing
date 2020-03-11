import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class Nature implements Runnable {
    private Garden garden;
    private Lock writeLock;
    private Random random;

    Nature(Garden garden, ReadWriteLock readWriteLock) {
        this.garden = garden;
        writeLock = readWriteLock.writeLock();
        random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            writeLock.lock();
            int row = random.nextInt(garden.getRowsNumber());
            int column = random.nextInt(garden.getColumnsNumber());
            int value = random.nextInt(3);

            garden.setValue(row, column, value);
            writeLock.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
