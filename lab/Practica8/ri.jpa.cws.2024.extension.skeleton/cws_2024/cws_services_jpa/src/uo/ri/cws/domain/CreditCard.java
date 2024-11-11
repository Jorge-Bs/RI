package uo.ri.cws.domain;

import java.time.LocalDate;
import uo.ri.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {
	private String number;//clave
	private String type;
	private LocalDate validThru;
	
	
	CreditCard(){
		
	}
	
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

	
	

	
}
