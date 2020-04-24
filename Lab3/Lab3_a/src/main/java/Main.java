public class Main {
    public static void main(String[] args) {
        final int potCapacity = 30;
        final int beesNumber = 3;

        Pot pot = new Pot(potCapacity);
        Bear bear = new Bear(pot);
        new Thread(bear).start();
        for (int i = 0; i < beesNumber; i++) {
            new Thread(new Bee(i, pot, bear)).start();
        }
    }
}
