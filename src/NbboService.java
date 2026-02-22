import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NbboService {

    private String servicedSymbol;

    // Map of Symbol to (map of exchange to quote)
    private Map<String, Map<String, Quote>> symbolToQuoteMap = new ConcurrentHashMap<>();

    // Map of symbol to NBBO
    private Map<String, Nbbo> nbboMap = new ConcurrentHashMap<>();

    public NbboService(String servicedSymbol) {
        this.servicedSymbol = servicedSymbol;
    }

    public void ingest(Quote quote) {
        String symbol = quote.getSymbol();
        String exchange = quote.getExchange();

        Map<String, Quote> exchangeToQuoteMap
                = symbolToQuoteMap.computeIfAbsent(symbol, s -> new ConcurrentHashMap<>());
        synchronized (exchangeToQuoteMap) {
            exchangeToQuoteMap.put(exchange, quote);
            recompute(symbol, exchangeToQuoteMap);
            getNbbo(symbol);
        }
    }

    private void recompute(String symbol, Map<String, Quote> exchangeToQuoteMap) {
        long maxBid = -1;
        long minAsk = Long.MAX_VALUE;
        if (exchangeToQuoteMap.isEmpty()) return;

        for (Quote quote : exchangeToQuoteMap.values()) {
            maxBid = Math.max(maxBid, quote.getBidPrice());
            minAsk = Math.min(minAsk, quote.getAskPrice());
        }
        nbboMap.put(symbol, new Nbbo(symbol, maxBid, minAsk));
    }

    private Nbbo getNbbo(String symbol) {
        Nbbo nbbo = nbboMap.get(symbol);
        System.out.println(NbboService.class.getSimpleName() + "_" + this.servicedSymbol + ": NBBO for " + symbol + " is " + nbbo.toString() + " \n");
        return nbbo;
    }

    public String getServicedSymbol() {
        return servicedSymbol;
    }
}
