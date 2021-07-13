import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pingapp extends JFrame {
    private JPanel panelMain;
    private JTextArea ttaStatus1;
    private JTextArea ttaStatus2;
    private JButton btnStart1;
    private JButton btnStart2;
    private JTextField ttfUrl1;
    private JTextField ttfUrl2;
    private JButton btnStop1;
    private JButton btnStop2;
    private JLabel lbUrl1;
    private JLabel lbUrl2;
    private JSlider slider;
    private JList list1;
    private JList list2;
    private countDownWhileWaitingThread countDownWhileWaitingThread1;
    private countDownWhileWaitingThread countDownWhileWaitingThread2;
    private SendHttpRequestThread sendHttpRequestThread1;
    private SendHttpRequestThread sendHttpRequestThread2;

    public Pingapp(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.pack();
        //this.setPreferredSize(new Dimension(500,280));
        this.setResizable(false);

        sendHttpRequestThread1 = new SendHttpRequestThread(ttfUrl1) {
            @Override
            public void run() {
                this.sendRequest();
                countDownWhileWaitingThread1.setStop(true);

                ttaStatus1.setRows(ttaStatus1.getRows() + 1);
                System.out.println(this.getResponse());
                ttaStatus1.append(this.getResponse());
            }
        };
        sendHttpRequestThread2 = new SendHttpRequestThread(ttfUrl2) {
            @Override
            public void run() {
                this.sendRequest();
                countDownWhileWaitingThread2.setStop(true);
                ttaStatus2.setRows(ttaStatus2.getRows() + 1);
                ttaStatus2.append(this.getResponse());
            }
        };

        btnStart1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCountDownWhileWaitingThread(1);
                countDownWhileWaitingThread1.start();
                sendHttpRequestThread1.start();
            }
        });

        btnStart2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countDownWhileWaitingThread2.start();
            }
        });
    }

    void createCountDownWhileWaitingThread(int nameNumber) {
        if (nameNumber == 1) {
            countDownWhileWaitingThread1 = new countDownWhileWaitingThread(ttfUrl1) {
                @Override
                public void run() {
                    while(!this.isStop()) {
                        long tempMillis = System.currentTimeMillis();
                        String dot = "";
                        for (int i = 30; i > 0; i--) {
                            if (this.isStop()) break;
                            ttaStatus1.setText(getStatus(i) + dot);
                            long startMillis = System.currentTimeMillis();
                            while(System.currentTimeMillis() - startMillis < 1000) {
                                if (this.isStop()) break;
                                if (System.currentTimeMillis() - tempMillis == 400) {
                                    dot = refreshDot(dot);
                                    ttaStatus1.setText(getStatus(i) + dot);
                                    tempMillis = System.currentTimeMillis();
                                } } }
                    }
                }
            };
        }
        else {
            countDownWhileWaitingThread2 = new countDownWhileWaitingThread(ttfUrl2) {
                @Override
                public void run() {
                    while(!this.isStop()) {
                        long tempMillis = System.currentTimeMillis();
                        String dot = "";
                        for (int i = 30; i > 0; i--) {
                            ttaStatus2.setText(getStatus(i) + dot);
                            long startMillis = System.currentTimeMillis();
                            while(System.currentTimeMillis() - startMillis < 1000) {
                                if (System.currentTimeMillis() - tempMillis == 400) {
                                    dot = refreshDot(dot);
                                    ttaStatus2.setText(getStatus(i) + dot);
                                    tempMillis = System.currentTimeMillis();
                                } } }
                    }
                }
            };
        }
    }

    public static void main(String[] args) {
        JFrame frame = new Pingapp("Wakeup Heroku app");
        frame.setVisible(true);
        frame.setSize(500,280);
        frame.setLocation(300,300);
    }
}
