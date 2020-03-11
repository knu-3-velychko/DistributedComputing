import java.util.List;

public class FragmentMatrix {
    private final int xStart;
    private final int yStart;
    private final int xEnd;
    private final int yEnd;
    private List<List<Integer>> fragmentMatrix;

    FragmentMatrix(List<List<Integer>> matrix, int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        fragmentMatrix = matrix;
    }

    public int getXStart() {
        return xStart;
    }

    public int getYStart() {
        return yStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public int getYEnd() {
        return yEnd;
    }

    public int getWidth() {
        return xEnd - xStart;
    }

    public int getHeight() {
        return yEnd - yStart;
    }

    List<List<Integer>> getFragmentMatix() {
        return fragmentMatrix;
    }
}
