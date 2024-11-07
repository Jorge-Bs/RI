package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TPaymentMean")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMean extends BaseEntity {
	// natural attributes
	@Basic(optional = false)private double accumulated = 0.0;

	// accidental attributes
	@Transient private Client client;
	@Transient private Set<Charge> charges = new HashSet<>();
	
	PaymentMean() {
	}

	public double getAccumulated() {
		return accumulated;
	}

	public Client getClient() {
		return client;
	}

	public abstract boolean canPay(Double amount);

	public void pay(double importe) {
		if(canPay(importe)) {
			this.accumulated += importe;
		}
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public int hashCode() {
		return Objects.hash(client);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentMean other = (PaymentMean) obj;
		return Objects.equals(client, other.client);
	}
	
	

}
