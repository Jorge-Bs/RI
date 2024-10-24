package uo.ri.cws.domain;

import java.util.Objects;

public class Voucher extends PaymentMean {
	private String code; //clave
	private double available = 0.0;
	private String description;

	
	
	
	public Voucher(String code, double available, String description) {
		//Validaciones
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

	}




	public String getCode() {
		return code;
	}




	public double getAvailable() {
		return available;
	}
	
	




	@Override
	public int hashCode() {
		return Objects.hash(code);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		return Objects.equals(code, other.code);
	}




	public String getDescription() {
		return description;
	}
	
	

}
