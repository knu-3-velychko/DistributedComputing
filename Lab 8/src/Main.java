import algorithm.CannonMethod;
import algorithm.FoxMethod;
import algorithm.SequentialMethod;
import algorithm.StripesMethod;

public class Main {
    public static void main(String[] args) {
//        int[] sizes = { 24 * 5, 24 * 21, 24 * 42 };   // Small
        int[] sizes = { 24 * 21, 24 * 42, 24 * 125 };   // Big

        for (int matrixSize : sizes) {
            SequentialMethod.compute(args, matrixSize);
            StripesMethod.compute(args, matrixSize);
            FoxMethod.compute(args, matrixSize);
            CannonMethod.compute(args, matrixSize);
        }
    }
}
