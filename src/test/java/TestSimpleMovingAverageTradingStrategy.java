import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import strategy.PerMinuteMovingAverageCache;
import strategy.SimpleMovingAverageTradingStrategy;
import util.PriceTimeMovingAverages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
 This is more for demo, for production case i think we can add some more test cases to test for rounding cases as well
 */

public class TestSimpleMovingAverageTradingStrategy {
    SimpleMovingAverageTradingStrategy simpleMovingAverageStrategy = new SimpleMovingAverageTradingStrategy();
    PerMinuteMovingAverageCache perMinuteMovingAverageCache = new PerMinuteMovingAverageCache();
    List<String> priceHistory = new ArrayList<>();
    int m = 1;
    int n = 2;

    @Before
    public void setup(){
        priceHistory.add("timestamp,time_in_milliseconds,price,volume");
        priceHistory.add("t1,1593276720000,3,4328.29");
        priceHistory.add("t2,1593276780000,2,6737.97");
        priceHistory.add("t3,1593276840000,1,5078.15");
        priceHistory.add("t4,1593276840000,4,5078.15");
    }
    @Test
    public void testSimpleMovingAverageCalc() {

        List<PriceTimeMovingAverages> priceTimeMovingAverages = new LinkedList<>();
        priceTimeMovingAverages.add(new PriceTimeMovingAverages("t1", 3, 0.0d, 3.0d));
        priceTimeMovingAverages.add(new PriceTimeMovingAverages("t2", 2, 2.5d, 2.0d));
        priceTimeMovingAverages.add(new PriceTimeMovingAverages("t3", 1, 1.5d, 1.0d));
        priceTimeMovingAverages.add(new PriceTimeMovingAverages("t4", 4, 2.5d, 4.0d));
        Assert.assertEquals(priceTimeMovingAverages, perMinuteMovingAverageCache.getPriceTimeMovingAverages(m, n, priceHistory));
    }

    @Test
    public void testSimpleMovingAverageStrategy(){
        List<PriceTimeMovingAverages> priceTimeMovingAverages = perMinuteMovingAverageCache.getPriceTimeMovingAverages(m, n, priceHistory);
        simpleMovingAverageStrategy.startTrading(priceTimeMovingAverages);
        List<String[]> expectedTrades = new ArrayList<>();
        expectedTrades.add(new String[]{"0", "Sell", "10.0", "4.0", "19.98", "1019.98"});
        Assert.assertArrayEquals(expectedTrades.toArray(), simpleMovingAverageStrategy.getTradesManager().getTrades().toArray());

    }
}
