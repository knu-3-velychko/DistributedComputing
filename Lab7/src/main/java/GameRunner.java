public class GameRunner extends Thread {
    private GamePanel panel;

    GameRunner(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        if (panel.getHunter() == null) {
            panel.setHunter(new Hunter(panel.getMainFrame(), panel));
            new Thread(panel.getHunter()).start();
        }

        while (!isInterrupted()) {
            if (panel.getDucks().size() < panel.getMaxDucks()) {
                Duck duck = new Duck(panel.getWidth(), panel.getHeight(), panel);
                panel.getDucks().add(duck);
                duck.start();
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
