package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic {
	// natural attributes
	private String nif;
	private String surname;
	private String name;

	// accidental attributes
	private Set<WorkOrder> assigned = new HashSet<>();
	private Set<Intervention> interventions = new HashSet<>();
	
	
	

	public Mechanic(String nif, String surname, String name) {
		ArgumentChecks.isNotEmpty(nif, "invalid nif");
		ArgumentChecks.isNotEmpty(surname, "invalid surname");
		ArgumentChecks.isNotEmpty(name, "invalid name");
		
		this.nif = nif;
		this.surname = surname;
		this.name = name;
	}



	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}



	public String getNif() {
		return nif;
	}



	public String getSurname() {
		return surname;
	}



	public String getName() {
		return name;
	}



	@Override
	public int hashCode() {
		return Objects.hash(nif);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(nif, other.nif);
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mechanic [nif=");
		builder.append(nif);
		builder.append(", surname=");
		builder.append(surname);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	

}
