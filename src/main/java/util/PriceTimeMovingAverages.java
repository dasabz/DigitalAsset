package util;

import java.util.Objects;

public class PriceTimeMovingAverages {
    private double price;
    private String currentTime;
    private double lastMMinsMovingAverage;
    private double lastNMinsMovingAverage;

    public PriceTimeMovingAverages(String currentTime, double currentPrice, double lastMMinsMovingAverage, double lastNMinsMovingAverage) {
        this.currentTime = currentTime;
        this.price = currentPrice;
        this.lastMMinsMovingAverage = lastMMinsMovingAverage;
        this.lastNMinsMovingAverage = lastNMinsMovingAverage;
    }

    @Override
    public String toString() {
        return "PriceTimeMovingAverages{" +
                "price=" + price +
                ", lastMMinsMovingAverage=" + lastMMinsMovingAverage +
                ", lastNMinsMovingAverage=" + lastNMinsMovingAverage +
                ", currentTime='" + currentTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceTimeMovingAverages that = (PriceTimeMovingAverages) o;
        return Double.compare(that.price, price) == 0 &&
                Double.compare(that.lastMMinsMovingAverage, lastMMinsMovingAverage) == 0 &&
                Double.compare(that.lastNMinsMovingAverage, lastNMinsMovingAverage) == 0 &&
                Objects.equals(currentTime, that.currentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, lastMMinsMovingAverage, lastNMinsMovingAverage, currentTime);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLastMMinsMovingAverage() {
        return lastMMinsMovingAverage;
    }

    public void setLastMMinsMovingAverage(double lastMMinsMovingAverage) {
        this.lastMMinsMovingAverage = lastMMinsMovingAverage;
    }

    public double getLastNMinsMovingAverage() {
        return lastNMinsMovingAverage;
    }

    public void setLastNMinsMovingAverage(double lastNMinsMovingAverage) {
        this.lastNMinsMovingAverage = lastNMinsMovingAverage;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


}
