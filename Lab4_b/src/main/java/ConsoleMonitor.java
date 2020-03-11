import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ConsoleMonitor implements Runnable {
    private Garden garden;
    private Lock readLock;

    ConsoleMonitor(Garden garden, ReadWriteLock readWriteLock) {
        this.garden = garden;
        readLock = readWriteLock.readLock();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            readLock.lock();

            for (int i = 0; i < garden.getRowsNumber(); i++) {
                for (int j = 0; j < garden.getColumnsNumber(); j++) {
                    System.out.print(garden.getValue(i, j) + " ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("-".repeat(garden.getRowsNumber() * 2));
            System.out.println();
            readLock.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
