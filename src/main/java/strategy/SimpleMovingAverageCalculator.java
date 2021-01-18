package strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/*
    Calculates the moving average
 */
public class SimpleMovingAverageCalculator implements MovingAverageCalculator {
    private final static Logger logger = LoggerFactory.getLogger(String.valueOf(SimpleMovingAverageCalculator.class));

    public double calculate(int index, List<String> priceHistory, int lastXMins) {
        if (index < lastXMins) {
            logger.warn("X mins has not passed yet, so cannot compute moving average");
            return 0;
        }
        List<String> subListLastXMins = priceHistory.subList(index - lastXMins, index);
        double sum = 0d;
        for (String subListPriceHistory : subListLastXMins) {
            String price = subListPriceHistory.split(",")[2];
            sum += Double.parseDouble(price);
        }
        return sum / subListLastXMins.size();
    }
}
