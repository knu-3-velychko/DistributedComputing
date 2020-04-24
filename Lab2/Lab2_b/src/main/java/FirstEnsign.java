public class FirstEnsign extends Thread {
    private volatile boolean isWorking;
    private EnsignsQueue queue;

    public FirstEnsign(EnsignsQueue queue) {
        this.queue=queue;

    }

    public void setWorking(boolean value) {
        isWorking = value;
    }

    public void run() {
        while (isWorking || !queue.isEmpty()) {
            int value = (int) (Math.random() * 100) + 1;
            queue.add(value);
            System.out.println("First ensign put: " + value);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

}
