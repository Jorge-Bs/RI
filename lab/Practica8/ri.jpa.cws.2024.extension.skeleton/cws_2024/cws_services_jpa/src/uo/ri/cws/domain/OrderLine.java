package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class OrderLine {

    private double price;
    private int quantity;
    private SparePart sparePart;

    OrderLine() {
    }

    public OrderLine(double price, int quantity, SparePart sparePart) {
        ArgumentChecks.isTrue(price >= 0.0, "invalid price");
        ArgumentChecks.isTrue(quantity >= 0, "invalid quantity");
        ArgumentChecks.isNotNull(sparePart, null);

        ArgumentChecks.isTrue(sparePart.isUnderStock(), "invalid quantity");

        this.price = price;
        this.quantity = quantity;
        this.sparePart = sparePart;
    }

    public OrderLine(SparePart sp, double price) {
        this(price, sp.getQuantityToOrder(), sp);
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sparePart);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderLine other = (OrderLine) obj;
        return Objects.equals(sparePart, other.sparePart);
    }

    @Override
    public String toString() {
        return "OrderLine [price=" + price + ", quantity=" + quantity
            + ", sparePart=" + sparePart + "]";
    }

    public void receive() {
        SparePart part = getSparePart();
        int cantidad = getQuantity();
        double price = getPrice();

        part.updatePriceAndStock(price, cantidad);

    }

    public double getAmount() {
        return price * quantity;
    }

}
