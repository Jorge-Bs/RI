package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Charge extends BaseEntity {
    private double amount = 0.0;

    private Invoice invoice;
    private PaymentMean paymentMean;

    Charge() {

    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
        ArgumentChecks.isNotNull(invoice, "invoice is null");
        ArgumentChecks.isNotNull(paymentMean, "payment is null");
        ArgumentChecks.isTrue(amount >= 0, "can't be negative");

        Associations.Settle.link(invoice, this, paymentMean);

        this.amount = amount;

        paymentMean.pay(amount);

    }

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    void _setPaymentMean(PaymentMean payment) {
        this.paymentMean = payment;
    }

    public double getAmount() {
        return amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    @Override
    public String toString() {
        return "Charge [amount=" + amount + ", invoice=" + invoice
            + ", paymentMean=" + paymentMean + "]";
    }

    

}
