package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {
	private String plateNumber;
	private String make;
	private String model;
	
	//atributo accidental
	
	private Client client;
	
	private VehicleType type;
	
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	
	public Vehicle(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank(plateNumber, "invalid plate");
		ArgumentChecks.isNotBlank(make, "invalid make");
		ArgumentChecks.isNotBlank(model, "invalid model");
		
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}
	
	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}
	

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}



	public String getPlateNumber() {
		return plateNumber;
	}


	public String getMake() {
		return make;
	}


	public String getModel() {
		return model;
	}
	
	void _setClient(Client client) {
		this.client=client;
		
	}
	
	void _setVehicleType(VehicleType type) {
		this.type=type;
	}


	public VehicleType getVehicleType() {
		return type;
	}


	public Client getClient() {
		return client;
	}


	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vehicle [plateNumber=");
		builder.append(plateNumber);
		builder.append(", make=");
		builder.append(make);
		builder.append(", model=");
		builder.append(model);
		builder.append("]");
		return builder.toString();
	}


	
	
	

}
