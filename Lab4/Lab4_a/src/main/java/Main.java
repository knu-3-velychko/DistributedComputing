public class Main {
    public static void main(String[] args) {
        String fName = "save_data.txt";
        RWLock rwLock = new RWLock();

        Thread addPerson = new Thread(new AddPerson(fName, rwLock));
        Thread deletePerson = new Thread(new DeletePerson(fName, rwLock));
        Thread findByPhone = new Thread(new FindByPhone(fName, rwLock));
        Thread findBySurname = new Thread(new FindBySurname(fName, rwLock));


        findByPhone.start();
        findBySurname.start();
        addPerson.start();
        deletePerson.start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addPerson.interrupt();
        deletePerson.interrupt();
        findByPhone.interrupt();
        findBySurname.interrupt();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            addPerson.interrupt();
            deletePerson.interrupt();
            findByPhone.interrupt();
            findBySurname.interrupt();
        }, "Shutdown-thread"));
    }
}
