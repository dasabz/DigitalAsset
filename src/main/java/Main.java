import strategy.PerMinuteMovingAverageCache;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import strategy.SimpleMovingAverageTradingStrategy;
import util.PriceTimeMovingAverages;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiPredicate;

public class Main {
    private static PerMinuteMovingAverageCache perMinuteMovingAverageCache = new PerMinuteMovingAverageCache();
    private static final SimpleMovingAverageTradingStrategy simpleMovingAverageStrategy = new SimpleMovingAverageTradingStrategy();
    private final static Logger logger = LoggerFactory.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            List<String> priceHistory = Files.readAllLines(getInputPriceFile());
            int m = Integer.parseInt(args[0]);
            int n = Integer.parseInt(args[1]);
            if (m > n) {
                throw new IllegalArgumentException("m cannot be greater than n");
            }
            List<PriceTimeMovingAverages> priceTimeMovingAverages = perMinuteMovingAverageCache.getPriceTimeMovingAverages(m, n, priceHistory);
            logger.info("M="+ m + " N="+n);
            simpleMovingAverageStrategy.startTrading(priceTimeMovingAverages);
            simpleMovingAverageStrategy.dumpTradesToFile();
        } catch (IOException | URISyntaxException e) {
            logger.error("Unable to read input price file " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static Path getInputPriceFile() throws URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource("Prices.csv");
        if (resource == null) {
            throw new IllegalArgumentException("Prices.csv not found!");
        } else {
            return Paths.get(resource.toURI());
        }
    }
}
