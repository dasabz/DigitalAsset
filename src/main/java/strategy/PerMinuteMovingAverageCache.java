package strategy;

import org.decimal4j.util.DoubleRounder;
import util.PriceTimeMovingAverages;

import java.util.LinkedList;
import java.util.List;
/*
    Caches the m and n mins moving average at every interval for easier decision making of buy and sell for the SimpleMovingAverageTradingStrategy
 */
public class PerMinuteMovingAverageCache {
    private final SimpleMovingAverageCalculator simpleMovingAverageCalculator = new SimpleMovingAverageCalculator();
    private final List<PriceTimeMovingAverages> priceTimeMovingAverages = new LinkedList<>();

    public List<PriceTimeMovingAverages> getPriceTimeMovingAverages(int m, int n, List<String> priceHistory) {
        List<String> priceHistoryData = priceHistory.subList(1, priceHistory.size());
        for (int index = 1; index < priceHistory.size(); index++) {
            String[] value = priceHistory.get(index).split(",");
            PriceTimeMovingAverages priceTimeMovingAverages = calculatePriceTimeMovingAverages(m, n, priceHistoryData, index, value);
            this.priceTimeMovingAverages.add(priceTimeMovingAverages);
        }
        return priceTimeMovingAverages;
    }

    private PriceTimeMovingAverages calculatePriceTimeMovingAverages(int m, int n, List<String> priceHistoryData, int index, String[] value) {
        String currentTime            = value[0];
        double currentPrice           = Double.parseDouble(value[2]);
        double movingAverageLastMMins = DoubleRounder.round(simpleMovingAverageCalculator.calculate(index, priceHistoryData, m),4);
        double movingAverageLastNMins = DoubleRounder.round(simpleMovingAverageCalculator.calculate(index, priceHistoryData, n),4);
        return new PriceTimeMovingAverages(currentTime, currentPrice, movingAverageLastNMins, movingAverageLastMMins);
    }
}

