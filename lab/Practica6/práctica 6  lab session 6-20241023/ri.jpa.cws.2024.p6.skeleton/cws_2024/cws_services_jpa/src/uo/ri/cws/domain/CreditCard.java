package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {
	private String number;//clave
	private String type;
	private LocalDate validThru;
	
	
	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotEmpty(number, "invalid number");
		ArgumentChecks.isNotEmpty(type, "invalid type");
		ArgumentChecks.isNotNull(validThru, "invalid date");
		
		this.number = number;
		this.type = type;
		this.validThru = validThru;
		
	}


	public String getNumber() {
		return number;
	}


	public String getType() {
		return type;
	}


	public LocalDate getValidThru() {
		return validThru;
	}


	@Override
	public boolean canPay(Double amount) {
		if(validThru.isBefore(LocalDate.now())) {
			throw new IllegalStateException("is invalid");
		}
		return true;
	}


	@Override
	public String toString() {
		return "CreditCard [number=" + number + 
				", type=" + type + 
				", validThru=" + validThru + 
				"]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}
	
	

	
}
