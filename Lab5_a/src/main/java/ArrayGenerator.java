import java.util.ArrayList;
import java.util.Random;

public class ArrayGenerator {
    private static Random random = new Random();

    public static ArrayList<Boolean> generate() {
        int size = random.nextInt(900) + 100;
        ArrayList<Boolean> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(random.nextBoolean());
        }
        return array;
    }
}
