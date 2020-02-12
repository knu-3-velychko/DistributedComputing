public class Main {

    public static void main(String[] args) {
        EnsignsQueue firstToSecond = new EnsignsQueue(5);
        EnsignsQueue secondToThird = new EnsignsQueue(5);

        FirstEnsign ensign1 = new FirstEnsign(firstToSecond);
        SecondEnsign ensign2 = new SecondEnsign(firstToSecond, secondToThird);
        ThirdEnsign ensign3 = new ThirdEnsign(secondToThird);

        ensign1.start();
        ensign2.start();
        ensign3.start();
        try {
            Thread.sleep(1000);
            ensign1.setWorking(false);
            ensign2.setWorking(false);
            ensign3.setWorking(false);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of stolen items: " + ensign3.getSum());
    }
}
