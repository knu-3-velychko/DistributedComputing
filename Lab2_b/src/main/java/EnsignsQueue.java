import java.util.LinkedList;
import java.util.Queue;

public class EnsignsQueue {
    private volatile int size;
    private Queue<Integer> queue = new LinkedList<>();

    EnsignsQueue(int size) {
        this.size = size;
    }

    public int getSize() {
        return queue.size();
    }

    public boolean isEmpty() {
        return this.getSize() != 0;
    }

    public synchronized void add(int value) {
        while (queue.size() >= size) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        queue.add(value);
        notifyAll();
    }

    public synchronized int get() {
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        return queue.remove();
    }
}
