import java.util.LinkedList;
import java.util.List;

public class Result {
    private static List<String> results = new LinkedList<>();

    public static synchronized void saveResult(String result) {
        results.add(result);
    }

    public static synchronized String getResults() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            stringBuilder.append(results.get(i));
        }
        return stringBuilder.toString();
    }
}
