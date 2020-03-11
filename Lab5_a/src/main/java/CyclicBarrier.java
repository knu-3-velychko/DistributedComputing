public class CyclicBarrier {
    private int parties;
    private int partiesAwait;
    private Runnable event;

    CyclicBarrier(int parties, Runnable event) {
        this.parties = parties;
        this.partiesAwait = parties;
        this.event = event;
    }

    synchronized void await() throws InterruptedException {
        partiesAwait++;
        if (partiesAwait < parties) {
            wait();
        } else {
            partiesAwait = 0;
            notifyAll();
            event.run();
        }
    }
}
