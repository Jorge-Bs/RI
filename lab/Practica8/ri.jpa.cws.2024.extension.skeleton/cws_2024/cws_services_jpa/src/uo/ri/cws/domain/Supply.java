package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Supply extends BaseEntity {

    private int deliveryTerm;

    private double price;

    private SparePart sparePart;
    private Provider provider;

    Supply() {

    }

    public Supply(Provider provider, SparePart spare, double price,
        int deliveryTerm) {
        ArgumentChecks.isNotNull(provider, "invalid provider");
        ArgumentChecks.isNotNull(spare, "invalid spare");
        ArgumentChecks.isTrue(deliveryTerm >= 0, "invalid delivery");
        ArgumentChecks.isTrue(price >= 0.0, "invalid price");
        this.deliveryTerm = deliveryTerm;
        this.price = price;

        Associations.Supplies.link(spare, this, provider);
    }

    public int getDeliveryTerm() {
        return deliveryTerm;
    }

    public double getPrice() {
        return price;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public Provider getProvider() {
        return provider;
    }

    void _setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    void _setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Supply [deliveryTerm=" + deliveryTerm + ", price=" + price
            + "]";
    }

    public void setDeliveryTerm(int deliveryTerm) {
        this.deliveryTerm = deliveryTerm;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
