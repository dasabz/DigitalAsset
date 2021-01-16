package strategy;

import java.util.List;

public interface MovingAverageCalculator {
    double calculate(int index, List<String> priceHistory, int lastXMins);
}
