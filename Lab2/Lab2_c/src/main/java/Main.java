import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final int monksNumber = 30;
        List<Monk> monastery1 = new ArrayList<>();
        List<Monk> monastery2 = new ArrayList<>();
        for (int i = 0; i < monksNumber; i++) {
            monastery1.add(new Monk("Huan-un"));
            monastery2.add(new Monk("Huan-in"));
        }

        Competition comp = new Competition(monastery1, monastery2, 0, monksNumber-1);
        ForkJoinPool pool = new ForkJoinPool();
        Monk winner = pool.invoke(comp);
        System.out.println("Winner: " + winner);
    }
}
