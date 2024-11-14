package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class SparePart extends BaseEntity {

    private String code;
    private String description;
    private double price;

    private int stock;
    private int minStock;
    private int maxStock;

    private Set<Substitution> substitutions = new HashSet<>();

    private Set<Supply> supplies = new HashSet<>();

    SparePart() {
    }

    public SparePart(String code, String description, double price) {
        this(code, description, price, 0, 0, 0);

    }

    public SparePart(String code, String description, double price, int stock,
        int minStock, int maxStock) {
        ArgumentChecks.isNotEmpty(code, "invalid code");
        ArgumentChecks.isNotEmpty(description, "invalid description");
        ArgumentChecks.isTrue(price >= 0.0, "invalid price");

        ArgumentChecks.isTrue(stock >= 0, "invalid stock");
        ArgumentChecks.isTrue(minStock >= 0, "invalid minStock");
        ArgumentChecks.isTrue(maxStock >= 0, "invalid maxStock");

        this.code = code;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.minStock = minStock;
        this.maxStock = maxStock;

    }

    Set<Supply> _getSupplies() {
        return supplies;
    }

    public Set<Supply> getSupplies() {
        return new HashSet<>(supplies);
    }

    public SparePart(String code) {
        this(code, "no-description", 0.0);
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    public String getCode() {
        return code;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    public int getStock() {
        return stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public int getQuantityToOrder() {
        int value = getMaxStock() - getStock();
        if (isUnderStock()) {
            return value > 0 ? value : 0;
        }
        return 0;
    }

    public void updatePriceAndStock(double price, int cantidad) {
        ArgumentChecks.isTrue(price >= 0.0, "invalid price");
        ArgumentChecks.isTrue(cantidad > 0, "invalid stock");
        this.price = (this.price * this.stock + price * cantidad * 1.2);
        this.stock += cantidad;
        this.price /= stock;
    }

    public boolean isUnderStock() {
        return getStock() < getMinStock();
    }

    public int getTotalUnitsSold() {
        return substitutions.stream()
            .mapToInt(substitution -> substitution.getQuantity()).sum();
    }

}
