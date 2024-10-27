package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {


	public Cash(Client client) {
		ArgumentChecks.isNotNull(client, "client is null");
		Associations.Hold.link(this, client);
	}

	@Override
	public boolean canPay(Double amount) {
		return true;
	}
	
	
}
