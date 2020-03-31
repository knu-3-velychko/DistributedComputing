public class SecondEnsign extends Thread {
    private EnsignsQueue fromFirst;
    private EnsignsQueue toThird;
    private volatile boolean isWorking;

    public SecondEnsign(EnsignsQueue fromFirst, EnsignsQueue toThird) {
        this.fromFirst = fromFirst;
        this.toThird = toThird;

        isWorking = true;
    }

    public void setWorking(boolean value) {
        isWorking = value;
    }

    public void run() {
        int value;
        while (isWorking || !fromFirst.isEmpty()) {
            value = fromFirst.get();
            System.out.println("Second ensign get: " + value);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            System.out.println("Second ensign put: " + value);
            toThird.add(value);
        }
    }
}