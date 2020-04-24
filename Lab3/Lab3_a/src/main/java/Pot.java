public class Pot {
    private int capacity;
    private int content;

    Pot(int capacity) {
        this.capacity = capacity;
        this.content = 0;
    }

    public synchronized void addHoney(int id) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        content++;
        System.out.println("Bee " + id + " added honey drop.\nPot: " + content + "/" + capacity);
    }

    public synchronized void eatAll() {
        content = 0;
        notifyAll();
    }

    public synchronized boolean isFull() {
        return capacity == content;
    }
}
