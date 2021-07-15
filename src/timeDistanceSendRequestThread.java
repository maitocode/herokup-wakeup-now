public class timeDistanceSendRequestThread extends Thread {
    int minutes;
    boolean stop;
    public timeDistanceSendRequestThread(int minutes) {
        super();
        this.minutes = minutes;
        this.stop = false;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
