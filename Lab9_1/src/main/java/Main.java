public class Main {
    public static void main(String[] args) {
        Test test = new Test();

        test.addTask(100);
        test.addTask(500);
        test.addTask(1000);
        test.addTask(1500);
        test.addTask(2000);
        test.addTask(2500);
        test.addTask(3000);

        test.run();
    }
}
