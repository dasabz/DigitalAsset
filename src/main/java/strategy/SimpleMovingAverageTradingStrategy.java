package strategy;


import org.decimal4j.util.DoubleRounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tradeFileHandling.TradesManager;
import util.Order;
import util.PriceTimeMovingAverages;
import util.Side;
import util.Trade;

import java.util.List;

/*
The main strategy which does the buy and sell based on the m and n mins moving average
 */
public class SimpleMovingAverageTradingStrategy implements TradingStrategy {
    private final static Logger logger = LoggerFactory.getLogger(String.valueOf(SimpleMovingAverageTradingStrategy.class));
    private final int fixedNotionalOrder = 20;
    private final TradesManager tradesManager = new TradesManager();
    private double outstandingBuyOrderPrice = Double.NaN;
    private int tradeNumber = 0;
    private boolean outstandingBuyOrder = false;
    private double outstandingBuyOrderQty;

    static boolean shouldBuy(double lastMMinsSMA, double lastNMinsSMA) {
        return Double.compare(lastMMinsSMA, lastNMinsSMA) == 1;
    }

    static boolean shouldSell(double lastMMinsSMA, double lastNMinsSMA) {
        return Double.compare(lastMMinsSMA, lastNMinsSMA) == -1;
    }

    /*
    Implement a simple trading strategy based on the moving averages such that you BUY every time the moving average of the last m minutes crosses above
     (i.e., exceeds) the moving average of the last n minutes, and SELL every time the reverse happens.
    */
    public void startTrading(List<PriceTimeMovingAverages> priceTimeMovingAverages) {
        for (int index = 0; index < priceTimeMovingAverages.size(); index++) {
            PriceTimeMovingAverages priceTimeMovingAverage = priceTimeMovingAverages.get(index);
            double lastMMinsSMA = priceTimeMovingAverage.getLastMMinsMovingAverage();
            double lastNMinsSMA = priceTimeMovingAverage.getLastNMinsMovingAverage();
            if (shouldBuy(lastMMinsSMA, lastNMinsSMA) && !outstandingBuyOrder) {
                placeBuyOrderOfFixedNotional(Side.Buy, priceTimeMovingAverage, index);
                outstandingBuyOrder = true;
            } else if (shouldSell(lastMMinsSMA, lastNMinsSMA) && outstandingBuyOrder) {
                Order sellOrder = placeSellOrder(index, priceTimeMovingAverage);
                outstandingBuyOrder = false;
                generateTrade(sellOrder);
            }
        }
    }

    public void generateTrade(Order sellOrder) {
        Trade trade = new Trade(tradeNumber++, Side.Sell, sellOrder.getQuantity(), sellOrder.getPrice(), outstandingBuyOrderQty, outstandingBuyOrderPrice);
        tradesManager.generateTradeOnSellOrder(trade);
    }

    public Order placeSellOrder(int index, PriceTimeMovingAverages priceTimeMovingAverages) {
        double sellPrice = priceTimeMovingAverages.getPrice();
        double sellQty = outstandingBuyOrderQty;
        String time = priceTimeMovingAverages.getCurrentTime();
        Order order = new Order(time, Side.Sell, sellPrice, sellQty);
        logger.info("Index=" + index + " Enter " + Side.Sell + " order " + order + " lastMMinsSMA=" + priceTimeMovingAverages.getLastMMinsMovingAverage() + " lastNMinsSMA=" + priceTimeMovingAverages.getLastNMinsMovingAverage());
        return order;
    }

    public void placeBuyOrderOfFixedNotional(Side side, PriceTimeMovingAverages entry, int index) {
        double price = entry.getPrice();
        double quantity = DoubleRounder.round(fixedNotionalOrder / price, 4);
        String time = entry.getCurrentTime();
        Order order = new Order(time, side, price, quantity);
        outstandingBuyOrderQty = order.getQuantity();
        outstandingBuyOrderPrice = order.getPrice();
        logger.info("Index=" + index + " Enter " + side + " order " + order + " lastMMinsSMA=" + entry.getLastMMinsMovingAverage() + " lastNMinsSMA=" + entry.getLastNMinsMovingAverage());
    }

    public TradesManager getTradesManager() {
        return tradesManager;
    }

    public void dumpTradesToFile() {
        tradesManager.dumpCsvFile();
    }
}

