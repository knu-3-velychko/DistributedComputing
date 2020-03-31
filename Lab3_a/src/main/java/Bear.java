public class Bear implements Runnable {
    private Pot pot;
    private volatile boolean isAwake;

    Bear(Pot pot) {
        this.pot = pot;
    }

    public synchronized void wakeUp() {
        this.isAwake = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (!isAwake) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                pot.eatAll();
                System.out.println("Bear woke up and ate all honey.");
                isAwake = false;
            }
        }
    }
}
