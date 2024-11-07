package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCash")
public class Cash extends PaymentMean {

	Cash(){
	
	}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client, "client is null");
		Associations.Hold.link(this, client);
	}

	@Override
	public boolean canPay(Double amount) {
		return true;
	}
	
	
}
