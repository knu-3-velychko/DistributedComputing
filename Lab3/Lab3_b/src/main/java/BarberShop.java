import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class BarberShop implements Runnable {
    private Semaphore waitingChairs = new Semaphore(3);
    private Semaphore barberChair = new Semaphore(1);
    Barber barber;

    public void run() {
        barber = new Barber();
        new Thread(barber).start();

        long i = 0L;
        while (true) {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer customer = new Customer(i);
            new Thread(customer).start();
            i++;
        }
    }

    class Barber implements Runnable {
        private boolean isSleeping;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                cutHair();
            }
        }

        void cutHair() {
            if (isSleeping) {
                System.out.println("The barber is sleeping");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                if (barberChair.tryAcquire())
                    isSleeping = true;
                else {
                    System.out.println("The barber is cutting hair");
                    barberChair.release();
                }
            }
        }

        void wakeUp() {
            if (isSleeping) {
                isSleeping = false;
                barberChair.release();
                System.out.println("The barber is woken up");
            }
        }
    }

    class Customer implements Runnable {
        private long id;

        Customer(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            if (waitingChairs.tryAcquire()) {
                System.out.println("Client " + id + " waiting");
                while (!barberChair.tryAcquire()) {
                    barber.wakeUp();
                }
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Client " + id + " set in barber chair");
                waitingChairs.release();
            } else {
                System.out.println("No free sits");
            }
        }
    }
}
