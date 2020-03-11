import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class FindBySurname implements Runnable {
    private Random random;
    private File file;

    private RWLock rwLock;

    FindBySurname(String fName, RWLock rwLock) {
        random = new Random();
        file = new File(fName);

        this.rwLock = rwLock;
    }

    @Override
    public void run() {
        int pos;
        String surname, line;
        boolean flag = false;
        while (!Thread.currentThread().isInterrupted()) {
            rwLock.lockRead();

            pos = random.nextInt(Constants.surnames.size());
            surname = Constants.surnames.get(pos);
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split(" ");
                    if (str[0].equals(surname)) {
                        System.out.println("FindByPhone thread found person with phone: " + str[0] + " " + str[1] + " " + str[2]);
                        flag = true;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!flag) System.out.println("FindBySurname thread. Not found: " + surname);
            rwLock.unlockRead();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
