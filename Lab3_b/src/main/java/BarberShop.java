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
            while (true) {
                cutHair();
            }
        }

        void cutHair() {
            if (barberChair.tryAcquire())
                isSleeping = true;

            if (isSleeping) {
                System.out.println("The barber is sleeping");
            } else {
                System.out.println("The barber is cutting hair");
                barberChair.release();
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void wakeUp() {
            isSleeping = false;
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
                while (!barberChair.tryAcquire()) {
                    barber.wakeUp();
                    System.out.println("Client " + id + " waiting");
                    try {
                        sleep(50000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Client " + id + " get haircut");
                waitingChairs.release();
            } else {
                System.out.println("No free sits");
            }
        }
    }
}
