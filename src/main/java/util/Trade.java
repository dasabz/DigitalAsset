package util;

import org.decimal4j.util.DoubleRounder;

/*
  Trade number (how many trades so far - consider a trade completed only when you SELL)
- Trade time (assume the trade is placed at the exact same time the latest price was received)
- Trade side (BUY or SELL)
- Trade quantity (calculated as explained in assumption #3 above)
- Trade price (assume the trade is executed exactly at the last price received)
- PNL for the trade (to be calculated only if the trade side is SELL)
- Net(Cumulative) PNL up until now

*/
public class Trade {
    private int tradeNumber;
    private Side side;
    private double sellQty;
    private double buyQty;
    private double sellPrice;
    private double buyPrice;
    private double pnlForTrade;

    public Trade(int tradeNumber, Side side, double sellQty, double sellPrice, double buyQty, double buyPrice) {
        this.tradeNumber = tradeNumber;
        this.side = side;
        this.sellQty = sellQty;
        this.buyQty = buyQty;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.pnlForTrade = DoubleRounder.round(sellPrice * sellQty - buyPrice * buyQty, 4);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeNumber=" + tradeNumber +
                ", side=" + side +
                ", sellQty=" + sellQty +
                ", buyQty=" + buyQty +
                ", sellPrice=" + sellPrice +
                ", buyPrice=" + buyPrice +
                ", pnlForTrade=" + pnlForTrade +
                '}';
    }

    public int getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(int tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public double getSellQty() {
        return sellQty;
    }

    public void setSellQty(double sellQty) {
        this.sellQty = sellQty;
    }

    public double getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(double buyQty) {
        this.buyQty = buyQty;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getPnlForTrade() {
        return pnlForTrade;
    }

    public void setPnlForTrade(double pnlForTrade) {
        this.pnlForTrade = pnlForTrade;
    }

}
