import java.util.concurrent.ForkJoinTask;

public class MatrixProductTask extends ForkJoinTask<double[][]> {
    private final double[][] A;
    private final double[][] B;

    private double[][] C;

    private final int row_start;
    private final int row_end;

    private final int column_start;
    private final int column_end;

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
    public double[][] getRawResult() {
        return C;
    }

    @Override
    protected void setRawResult(double[][] result) {
        this.C = result;
    }

    @Override
    protected boolean exec() {
        for (int i = row_start; i < row_end; i++) {
            for (int j = column_start; j < column_end; j++) {
                C[i][j] = calculateEntry(i, j);
            }
        }
        return true;
    }

    private double calculateEntry(int i, int j) {
        double result = 0.0;
        for (int k = 0; k < B.length; k++) {
            result += A[i][k] * B[k][j];
        }
        return result;
    }
}
