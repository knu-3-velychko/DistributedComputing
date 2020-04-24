public class ThirdEnsign extends Thread {
    private EnsignsQueue queue;
    private volatile boolean isWorking;
    private int sum;

    public ThirdEnsign(EnsignsQueue queue) {
        this.queue = queue;
        isWorking = true;
    }

    public void setWorking(boolean value) {
        isWorking = value;
    }

    public int getSum() {
        return sum;
    }

    public void run() {
        int value;
        while (isWorking || !queue.isEmpty()) {
            value = queue.get();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            System.out.println("Third ensign get: " + value);
            sum += value;
        }
    }

}