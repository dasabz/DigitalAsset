package strategy;

import util.Order;
import util.PriceTimeMovingAverages;
import util.Side;

import java.util.List;
import java.util.function.BiPredicate;

public interface TradingStrategy {
    void  startTrading(List<PriceTimeMovingAverages> priceTimeMovingAverages) ;
    void  placeBuyOrderOfFixedNotional(Side side, PriceTimeMovingAverages entry, int index);
    Order placeSellOrder(int index, PriceTimeMovingAverages priceTimeMovingAverages) ;
    void generateTrade(Order sellOrder);
    void  dumpTradesToFile();

}
