package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCreditCard")
public class CreditCard extends PaymentMean {
	@Column(unique =  true) private String number;//clave
	@Basic(optional = false)private String type;
	@Basic(optional = false) private LocalDate validThru;
	
	
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
