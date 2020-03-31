import java.io.*;
import java.util.Random;

public class FindByPhone implements Runnable {
    private Random random;
    private File file;

    private RWLock rwLock;

    FindByPhone(String fName, RWLock rwLock) {
        random = new Random();
        file = new File(fName);

        this.rwLock = rwLock;
    }

    @Override
    public void run() {
        int pos;
        String phone, line;
        boolean flag = false;
        while (!Thread.currentThread().isInterrupted()) {
            rwLock.lockRead();

            pos = random.nextInt(Constants.phones.size());
            phone = Constants.phones.get(pos);
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split(" ");
                    if (str[2].equals(phone)) {
                        System.out.println("FindByPhone thread found person with phone: " + str[0] + " " + str[1] + " " + str[2]);
                        flag = true;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!flag) System.out.println("FindByPhone thread. Not found: " + phone);
            rwLock.unlockRead();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
