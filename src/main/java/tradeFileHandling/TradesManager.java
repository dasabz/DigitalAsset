package tradeFileHandling;

import com.opencsv.CSVWriter;
import org.decimal4j.util.DoubleRounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Trade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
 Is responsible for doing some pnl calculations as well as dumping the trades to a file.
 Based on need in future we can further split this class later on into a seperate tradeFileWriter class which can be used by the tradesManager
 */
public class TradesManager {
    private CSVWriter csvWriter;

    public List<String[]> getTrades() {
        return trades;
    }

    private final List<String[]> trades = new ArrayList<>();
    private static final String outputTradeFilePath = "./src/main/java/Trades.csv";
    private final int startingBalance = 1000;
    private double commissionRate = 0.001; //0.1% per trade
    private double cumulativePnl = startingBalance;
    private final static Logger logger = LoggerFactory.getLogger(String.valueOf(TradesManager.class));

    public TradesManager() {
        try {
            csvWriter = new CSVWriter(new FileWriter(new File(outputTradeFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] header = {"TradeNumber", "common.Side", "Quantity", "Price", "PnlForTrade", "CumulativePnl"};
        csvWriter.writeNext(header);
    }

    public void generateTradeOnSellOrder(Trade trade) {
        double pnlForTrade = DoubleRounder.round(trade.getPnlForTrade(), 4);
        double commission = DoubleRounder.round(pnlForTrade*commissionRate,4);
        pnlForTrade = DoubleRounder.round(pnlForTrade - commission,4);
        cumulativePnl += pnlForTrade;
        cumulativePnl = DoubleRounder.round(cumulativePnl, 4);
        String[] outputTrade = {String.valueOf(trade.getTradeNumber()),
                String.valueOf(trade.getSide()),
                String.valueOf(trade.getSellQty()),
                String.valueOf(trade.getSellPrice()),
                String.valueOf(pnlForTrade),
                String.valueOf(cumulativePnl)};
        logger.info("Generating trade "+ trade + " originalPnl=" + trade.getPnlForTrade() + " pnlAfterFees="+ pnlForTrade + " cumulativePnlAfterFees="+ cumulativePnl);
        trades.add(outputTrade);
    }

    public void dumpCsvFile() {
        csvWriter.writeAll(trades);
        try {
            csvWriter.close();
        } catch (IOException e) {
            logger.error("Unable to write trades file "+ e.getMessage());
            e.printStackTrace();
        }
    }




}
