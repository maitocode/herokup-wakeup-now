import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class SendHttpRequestThread extends Thread {
    private JTextField ttfUrl;
    private HttpResponse<String> response;
    private boolean stop;

    public SendHttpRequestThread(JTextField ttfUrl) {
        super();
        this.ttfUrl = ttfUrl;
        this.response = null;
        this.stop = false;
    }

    void sendRequest() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        String ipAddress = ttfUrl.getText();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ipAddress))
                .build();
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public String getResponse() {
        return "\n[" + response.statusCode() + "] (" + LocalDateTime.now().getHour()
                + ":" + LocalDateTime.now().getMinute()
                + ":" + LocalDateTime.now().getSecond()
                + ") " + response.body();
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
