package uo.ri.cws.domain;





import uo.ri.util.assertion.ArgumentChecks;


public class Voucher extends PaymentMean {
	private String code; //clave
	private double available = 0.0;
	private String description;

	
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
