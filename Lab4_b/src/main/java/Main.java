import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        final String fileName = "garden.txt";
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Garden garden = Garden.getInstance();

        Thread nature = new Thread(new Nature(garden, readWriteLock));
        Thread gardener = new Thread(new Gardener(garden, readWriteLock));
        Thread consoleMonitor = new Thread(new ConsoleMonitor(garden, readWriteLock));
        Thread fileMonitor = new Thread(new FileMonitor(garden, readWriteLock, fileName));

        nature.start();
        gardener.start();
        consoleMonitor.start();
        fileMonitor.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nature.interrupt();
        gardener.interrupt();
        consoleMonitor.interrupt();
        fileMonitor.interrupt();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            nature.interrupt();
            gardener.interrupt();
            consoleMonitor.interrupt();
            fileMonitor.interrupt();
        }, "Shutdown-thread"));
    }
}
