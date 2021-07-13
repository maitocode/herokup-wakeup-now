import javax.swing.*;

public class countDownWhileWaitingThread extends Thread {

    private JTextField ttfUrl;
    private boolean stop;

    public countDownWhileWaitingThread(JTextField ttfUrl) {
        super();
        this.ttfUrl = ttfUrl;
        this.stop = false;
    }

    String getStatus(int timeCountDown) {
        return "[" + timeCountDown + "] " + "Pinging to " + ttfUrl.getText();
    }
    String refreshDot(String dot) {
        if (dot.equals("")) return ".";
        if (dot.equals(".")) return "..";
        if (dot.equals("..")) return "...";
        else return ".";
    }

    synchronized public void setStop(boolean stop) {
        this.stop = stop;
    }

    synchronized public boolean isStop() {
        return stop;
    }
}
