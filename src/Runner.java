import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Runner {

    public static void main(String[] args) {
        // Create a queue to emulate a messaging system
        BlockingQueue<Quote> quoteQueue = new LinkedBlockingQueue<>();

        // Create a map of nbboServices emulating shared services
        Map<String, NbboService> nbboServiceMap = prepNbboServiceMap();

        // Prep the runnable to read from the server
        ReadFromServerRunnable readFromServerRunnable = new ReadFromServerRunnable(quoteQueue);
        Thread readFromServerThread = new Thread(readFromServerRunnable);

        // Prep the runnable to pull quote out of the queue and process
        QuoteConsumerRunnable quoteConsumerRunnable = new QuoteConsumerRunnable(quoteQueue, nbboServiceMap);
        Thread quoteConsumerThread = new Thread(quoteConsumerRunnable);

        // Start up the thread to process stuff off of the queue
        quoteConsumerThread.start();

        // Now start up the thread to read data from the server
        readFromServerThread.start();
    }

    private static Map<String, NbboService> prepNbboServiceMap() {
        // "Shard" servies based on a criteria.
        // Considering the data, I'm assigning one symbol to one service.
        Map<String, NbboService> nbboServiceMap = new HashMap<>();
        nbboServiceMap.put("DELL", new NbboService("DELL"));
        nbboServiceMap.put("IBM", new NbboService("IBM"));
        return nbboServiceMap;
    }
}
