public class RWLock {
    private boolean writeLock;
    private int readersNumber;

    RWLock() {
        writeLock = false;
        readersNumber = 0;
    }

    synchronized void lockRead() {
        while (writeLock) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readersNumber++;
    }

    synchronized void unlockRead() {
        readersNumber--;
        if (readersNumber == 0)
            notifyAll();
    }

    synchronized void lockWrite() {
        while (readersNumber != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeLock = true;
    }

    synchronized void unlockWrite() {
        writeLock = false;
        notifyAll();
    }

}
