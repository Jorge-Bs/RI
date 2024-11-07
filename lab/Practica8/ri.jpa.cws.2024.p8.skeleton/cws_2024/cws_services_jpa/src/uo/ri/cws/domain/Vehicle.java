package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicle")
public class Vehicle extends BaseEntity{
	
	@Column(unique = true) private String plateNumber;
	@Basic(optional = false) private String make;
	@Basic(optional = false) private String model;
	
	//atributo accidental
	
	@ManyToOne private Client client;
	
	@ManyToOne private VehicleType type;
	
	@OneToMany(mappedBy = "vehicle") private Set<WorkOrder> workOrders = new HashSet<>();
	
	Vehicle(){
		
	}
	
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
