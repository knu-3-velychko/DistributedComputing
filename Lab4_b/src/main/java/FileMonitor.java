import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class FileMonitor implements Runnable {
    private Garden garden;
    private Lock readLock;
    private BufferedWriter writer = null;

    FileMonitor(Garden garden, ReadWriteLock readWriteLock, String fileName) {
        this.garden = garden;
        readLock = readWriteLock.readLock();
        try {
            this.writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            readLock.lock();
            if (writer != null) {
                try {
                    for (int i = 0; i < garden.getRowsNumber(); i++) {
                        for (int j = 0; j < garden.getColumnsNumber(); j++) {
                            writer.write(garden.getValue(i, j) + " ");
                        }
                        writer.newLine();
                    }
                    writer.newLine();
                    writer.write("-".repeat(garden.getRowsNumber() * 2));
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            readLock.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Thread.currentThread().interrupt();
            }
        }
        try {
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
