import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class BarberShop implements Runnable {
    private Semaphore waitingChairs = new Semaphore(3);
    private Semaphore barberChair = new Semaphore(1);
    private Semaphore sleeping = new Semaphore(1);


    public void run() {
        Barber barber = new Barber();
        new Thread(barber).start();

        long i = 0L;
        while (true) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer customer = new Customer(i);
            new Thread(customer).start();
            i++;
        }
    }

    class Barber implements Runnable {

        @Override
        public void run() {
            while (true) {
                cutHair();
            }
        }

        void cutHair() {
            if (sleeping.tryAcquire()) {
                System.out.println("The barber is sleeping");
            } else {
                System.out.println("The barber is cutting hair");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                barberChair.release();
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
                while (!barberChair.tryAcquire()) {
                    System.out.println("Client " + id + " waiting");
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Client " + id + " get haircut");
                sleeping.release();
                waitingChairs.release();
            } else {
                System.out.println("No free sits");
            }
        }
    }
}
