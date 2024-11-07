package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicleType")
public class VehicleType extends BaseEntity{
	// natural attributes
	@Column(unique = true) private String name;
	@Basic(optional = false) private double pricePerHour;

	// accidental attributes
	@OneToMany(mappedBy = "type") private Set<Vehicle> vehicles = new HashSet<>();

	
	VehicleType() {
	}

	public VehicleType(String name, double pricePerHour) {
		ArgumentChecks.isNotBlank(name, "invalid name");
		ArgumentChecks.isTrue(pricePerHour>=0.0,"invalid price");
		
		this.name = name;
		this.pricePerHour = pricePerHour;
	}


	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleType [name=");
		builder.append(name);
		builder.append(", pricePerHour=");
		builder.append(pricePerHour);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name);
	}
	
	

}
