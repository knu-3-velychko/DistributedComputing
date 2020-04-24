import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Competition extends RecursiveTask<Monk> {
    private List<Monk> monastery1, monastery2;
    private int start, end;

    Competition(List<Monk> monastery1, List<Monk> monastery2, int start, int end) {
        this.monastery1 = monastery1;
        this.monastery2 = monastery2;

        this.start = start;
        this.end = end;
    }

    @Override
    protected Monk compute() {
        if (end - start == 0) {
            System.out.println("Competed: " + monastery1.get(end) + " and " + monastery2.get(end));
            return Monk.max(monastery1.get(end), monastery2.get(end));
        }

        Competition leftWinner = new Competition(monastery1, monastery2, start, (end + start) / 2);
        leftWinner.fork();

        Competition rightWinner = new Competition(monastery1, monastery2, (end + start) / 2 + 1, end);
        rightWinner.fork();

        Monk left = leftWinner.join();
        Monk right = rightWinner.join();
        System.out.println("Competed: " + left + " and " + right);

        return Monk.max(left, right);
    }
}
