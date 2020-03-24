import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Test {
    private HTMLBuilder htmlBuilder;
    private List<Integer> sizes;

    Test() {
        sizes = new LinkedList<>();

        htmlBuilder = new HTMLBuilder();
        htmlBuilder.build();
        htmlBuilder.createTable();
    }

    void addTask(Integer size) {
        sizes.add(size);
    }

    void run() {
        double sequentialTime;
        double time, acceleration;
        List<Pair<Double, Double>> results;

        for (Integer i : sizes) {
            results = new LinkedList<>();
            sequentialTime = ((double) calculate(i, 1)) / 1000.0;

            //FIXME: repeated code
            time = ((double) calculate(i, 2)) / 1000.0;
            acceleration = sequentialTime / time;
            results.add(new Pair<>(time, acceleration));

            time = ((double) calculate(i, 4)) / 1000.0;
            acceleration = sequentialTime / time;
            results.add(new Pair<>(time, acceleration));

            time = ((double) calculate(i, 9)) / 1000.0;
            acceleration = sequentialTime / time;
            results.add(new Pair<>(time, acceleration));

            htmlBuilder.addResult(i, sequentialTime, results);

            System.out.println(i);
        }

        finish();
    }

    private Long calculate(int size, int threadsNumber) {
        MatrixGenerator matrixGenerator = new MatrixGenerator(size, 100);
        double[][] A = matrixGenerator.generate();
        double[][] B = matrixGenerator.generate();
        //1 proc, 2 proc, 4 proc, 9 proc
        StripesSchema stripesSchema = new StripesSchema(A, B, threadsNumber);
        Long startTime = System.currentTimeMillis();

        stripesSchema.calculateProduct();

        Long time = System.currentTimeMillis() - startTime;

        return time;
    }

    private void finish() {
        htmlBuilder.finishTable();
        htmlBuilder.finish();
    }
}
