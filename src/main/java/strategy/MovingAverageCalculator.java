package strategy;

import java.util.List;
/*
Interface which abstracts the moving average calculation from a price history for the last x mins
 */
public interface MovingAverageCalculator {
    double calculate(int index, List<String> priceHistory, int lastXMins);
}
