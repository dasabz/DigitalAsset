package util;

import java.util.Objects;

public class Order {
    Side side;
    double price;
    double quantity;
    String time;

    @Override
    public String toString() {
        return "common.Order{" +
                "side=" + side +
                ", price=" + price +
                ", quantity=" + quantity +
                ", time='" + time + '\'' +
                '}';
    }

    public Order(String time, Side side, double price, double quantity) {
        this.time = time;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 &&
                Double.compare(order.quantity, quantity) == 0 &&
                side == order.side &&
                Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, price, quantity, time);
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
