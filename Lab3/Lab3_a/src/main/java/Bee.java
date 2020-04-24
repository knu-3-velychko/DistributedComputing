public class Bee implements Runnable {
    private int id;
    private Pot pot;
    private Bear bear;

    Bee(int id, Pot pot, Bear bear) {
        this.id = id;
        this.pot = pot;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pot.addHoney(id);
            if (pot.isFull()) {
                System.out.println("Pot is full");
                bear.wakeUp();
            }

        }
    }
}
