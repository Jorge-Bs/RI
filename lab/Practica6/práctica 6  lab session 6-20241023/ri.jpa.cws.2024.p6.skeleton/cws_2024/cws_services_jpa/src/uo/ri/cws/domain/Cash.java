package uo.ri.cws.domain;

public class Cash extends PaymentMean {


	public Cash(Client client) {
		//valida
		Associations.Hold.link(this, client);
	}
	
	
}
