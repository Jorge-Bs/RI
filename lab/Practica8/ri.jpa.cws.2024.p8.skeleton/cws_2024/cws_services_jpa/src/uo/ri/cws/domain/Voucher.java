package uo.ri.cws.domain;


import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVoucher")
public class Voucher extends PaymentMean {
	@Column(unique =  true) private String code; //clave
	@Basic(optional = false)private double available = 0.0;
	@Basic(optional = false)private String description;

	
	 Voucher() {
	}
	
	public Voucher(String code,  String description,double available) {
		ArgumentChecks.isNotEmpty(code, "invalid code");
		ArgumentChecks.isTrue(available>=0,"invalid availabe");
		ArgumentChecks.isNotEmpty(description, "invalid description");
		
		
		this.code = code;
		this.available = available;
		this.description = description;
	}




	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		super.pay(amount);
		available-=amount;
	}




	public String getCode() {
		return code;
	}




	public double getAvailable() {
		return available;
	}




	public String getDescription() {
		return description;
	}




	@Override
	public boolean canPay(Double amount) {
		if(amount>available) throw new IllegalStateException("invalid amount");
		return true;
	}
	
	

}
