import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringModifier implements Runnable {
    private int id;
    private CyclicBarrier barrier;
    private String str;
    private Class<Result> result;

    StringModifier(int id, String str, CyclicBarrier barrier, Class<Result> result) {
        this.id = id;
        this.str = str;
        this.barrier = barrier;
        this.result = result;
    }

    @Override
    public void run() {
        System.out.println("String modifier #" + id + "get string to compute: " + str);
        long aCount = str.chars().filter(ch -> ch == 'A').count();
        long bCount = str.chars().filter(ch -> ch == 'B').count();
        HashMap<Character, Character> replacement = new HashMap<>();

        if (aCount > bCount) {
            replacement.put('A', 'C');
            replacement.put('D', 'B');

            replace(aCount - bCount, replacement);
        } else {
            replacement.put('B', 'D');
            replacement.put('C', 'A');

            replace(bCount - aCount, replacement);
        }
        try {
            Result.saveResult("String modifier #" + id + "computed string: " + str);
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void replace(long number, HashMap<Character, Character> replacement) {
        StringBuilder stringBuilder = new StringBuilder(str);
        char current;
        for (int i = 0; i < stringBuilder.length(); i++) {
            current = stringBuilder.charAt(i);
            if (replacement.containsKey(current)) {
                stringBuilder.setCharAt(i, replacement.get(current));
                number--;
                if (number == 0)
                    break;
            }
        }
        str = stringBuilder.toString();
    }
}
