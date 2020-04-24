import java.util.Random;

public class Monk implements Comparable {
    private int energy;
    private String monastery;

    Monk(String monastety) {
        this.monastery = monastety;
        Random r = new Random();
        energy = r.nextInt(100);
    }

    int getEnergy() {
        return energy;
    }

    String getMonastery() {
        return monastery;
    }

    @Override
    public int compareTo(Object o) {
        Monk other = (Monk) o;
        return Integer.compare(this.energy, other.energy);
    }

    @Override
    public String toString() {
        return "Monk from " + monastery + " with energy " + energy;
    }

    public static Monk max(Monk first, Monk second) {
        if (first.energy > second.energy) {
            return first;
        } else {
            return second;
        }
    }
}
