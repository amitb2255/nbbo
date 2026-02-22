import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class ReadFromServerRunnable implements Runnable {

    private BlockingQueue<Quote> quoteQueue;

    public ReadFromServerRunnable(BlockingQueue<Quote> quoteQueue) {
        this.quoteQueue = quoteQueue;
    }

    public void connectToServer() {
        String serverAddress = "199.83.14.77"; // Replace with your server's IP address
        int portNumber = 7777; // Replace with your server's port number

        try (Socket socket = new Socket(serverAddress, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server. Type 'exit' to quit.");

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (!serverResponse.startsWith("Q")) {
                    continue;
                }
                Quote quote = prepQuote(serverResponse);
                System.out.println(ReadFromServerRunnable.class.getSimpleName() + " : inserting into queue: " + quote.toString());
                quoteQueue.put(quote);
            }
        } catch (IOException e) {
            System.err.println("Could not connect to server " + serverAddress + " on port " + portNumber);
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Quote prepQuote(String str) {
        //Q|DELL|NASDAQ|1045|1118
        String[] strArr = str.split("\\|");
        Quote quote = new Quote(strArr[1], strArr[2], Long.parseLong(strArr[3]), Long.parseLong(strArr[4]));
        return quote;
    }

    @Override
    public void run() {
        connectToServer();
    }
}
