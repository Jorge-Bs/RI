package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Charge extends BaseEntity{
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isNotNull(invoice,"invoice is null");
		ArgumentChecks.isNotNull(paymentMean,"payment is null");
		ArgumentChecks.isTrue(amount>=0,"can't be negative");
		
		Associations.Settle.link(invoice, this, paymentMean);
		
		this.amount = amount;
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
		Associations.Settle.link(invoice, this, paymentMean);
		
		paymentMean.pay(amount);
		
	}
	
	void _setInvoice(Invoice invoice) {
		this.invoice=invoice;
	}
	
	void _setPaymentMean(PaymentMean payment) {
		this.paymentMean=payment;
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
		return "Charge [amount=" + amount 
				+ ", invoice=" + invoice 
				+ ", paymentMean=" + paymentMean 
				+"]";
	}
	
	

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
	}

}
