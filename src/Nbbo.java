public class Nbbo {

    private String symbol;

    private long maxBid;

    private long minASk;

    public Nbbo(String symbol, long maxBid, long minASk) {
        this.symbol = symbol;
        this.maxBid = maxBid;
        this.minASk = minASk;
    }

    @Override
    public String toString() {
        return "Nbbo{" +
                "symbol='" + symbol + '\'' +
                ", maxBid=" + maxBid +
                ", minASk=" + minASk +
                '}';
    }
}
