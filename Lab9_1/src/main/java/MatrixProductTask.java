import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MatrixProductTask extends RecursiveAction {
    private final double[][] A;
    private final double[][] B;

    private double[][] C;

    private final int row_start;
    private final int row_end;

    private final int column_start;
    private final int column_end;

    private int i = 0;
    private int threadsNumber = 0;

    public MatrixProductTask(final double[][] A, final double[][] B, double[][] C, int i, int threadsNumber) {
        this(A, B, C, 0, A.length, 0, B.length);
        this.i = i;
        this.threadsNumber = threadsNumber;
    }

    public MatrixProductTask(final double[][] A, final double[][] B, double[][] C, int row_start, int row_end, int column_start, int column_end) {
        this.A = A;
        this.B = B;
        this.C = C;

        this.row_start = row_start;
        this.row_end = row_end;

        this.column_start = column_start;
        this.column_end = column_end;
    }

    @Override
    protected void compute() {
        if (threadsNumber > 0) {
            List<MatrixProductTask> tasks = new ArrayList<>();
            int row_start, row_end;
            int column_start, column_end;

            for (int j = 0; j < threadsNumber; j++) {
                row_start = j * threadsNumber;
                row_end = Math.min((j + 1) * threadsNumber, A.length);

                column_start = ((i + j) % threadsNumber) * threadsNumber;
                column_end = Math.min((column_start + 1) * threadsNumber, A.length);

                MatrixProductTask task = new MatrixProductTask(A, B, C, row_start, row_end, column_start, column_end);
                tasks.add(task);
            }

            RecursiveAction.invokeAll(tasks);
        } else {
            for (int i = row_start; i < row_end; i++) {
                for (int j = column_start; j < column_end; j++) {
                    C[i][j] = calculateEntry(i, j);
                }
            }
        }
    }

    private double calculateEntry(int i, int j) {
        double result = 0.0;
        for (int k = 0; k < B.length; k++) {
            result += A[i][k] * B[k][j];
        }
        return result;
    }
}
