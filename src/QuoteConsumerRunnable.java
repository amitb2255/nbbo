import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class QuoteConsumerRunnable implements Runnable {

    private BlockingQueue<Quote> quoteQueue;

    private Map<String, NbboService> nbboServiceMap;

    public QuoteConsumerRunnable(BlockingQueue<Quote> quoteQueue, Map<String, NbboService> nbboServiceMap) {
        this.quoteQueue = quoteQueue;
        this.nbboServiceMap = nbboServiceMap;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Quote quote = quoteQueue.take();
                NbboService nbboServiceToUse = getShardedService(quote);
                System.out.println(QuoteConsumerRunnable.class.getSimpleName() + " : assigning quote from queue: " + quote.toString()
                        + " to NbboService_" + nbboServiceToUse.getServicedSymbol());
                nbboServiceToUse.ingest(quote);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private NbboService getShardedService(Quote quote) {
        if (!nbboServiceMap.containsKey(quote.getSymbol())) {
            throw new RuntimeException("ERROR - No service available for symbol " + quote.getSymbol());
        }
        return nbboServiceMap.get(quote.getSymbol());
    }
}
