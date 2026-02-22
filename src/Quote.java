public class Quote {

    private String symbol;

    private String exchange;

    private long bidPrice;

    private long askPrice;

    public Quote(String symbol, String exchange, long bidPrice, long askPrice) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public long getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(long bidPrice) {
        this.bidPrice = bidPrice;
    }

    public long getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(long askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", exchange='" + exchange + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                '}';
    }
}
