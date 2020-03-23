import java.util.Random;

public class MatrixGenerator {
    private int row_size;
    private int column_size;
    private double maxValue;

    Random random;

    MatrixGenerator(int size, double maxValue) {
        this(size, size, maxValue);
    }

    MatrixGenerator(int row_size, int column_size, double maxValue) {
        this.row_size = row_size;
        this.column_size = column_size;
        this.maxValue = maxValue;

        random = new Random();
    }

    double[][] generate() {
        double[][] matrix = new double[row_size][column_size];

        for (int i = 0; i < row_size; i++) {
            matrix[i] = new double[column_size];
            for (int j = 0; j < column_size; j++) {
                matrix[i][j] = maxValue * random.nextDouble();
            }
        }

        return matrix;
    }
}
