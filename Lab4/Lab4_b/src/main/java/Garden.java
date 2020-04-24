public class Garden {
    private final int n = 5;
    private final int m = 5;

    private int[][] matrix = new int[n][m];

    private static Garden instance = null;

    private Garden() {

    }

    public static Garden getInstance() {
        if (instance == null)
            instance = new Garden();
        return instance;
    }

    public int getRowsNumber() {
        return n;
    }

    public int getColumnsNumber() {
        return m;
    }

    public int getValue(int i, int j) {
        return matrix[i][j];
    }

    public void setValue(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public void incrementValue(int i, int j) {
        matrix[i][j]++;
    }

}
