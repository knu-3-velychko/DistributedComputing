public class Changed {
    private volatile boolean changed = true;

    synchronized boolean getChanged() {
        return changed;
    }

    synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }
}
