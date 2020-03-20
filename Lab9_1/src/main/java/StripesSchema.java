public class StripesSchema {
    private final double[][] A;
    private final double[][] B;

    private double[][] C;

    private final int threadsNumb;

    public StripesSchema(double[][] A, double[][] B, int threadsNumb) {
        this.A = A;
        this.B = B;

        this.threadsNumb = threadsNumb;
    }

    public void calculateProduct() {

    }
}
