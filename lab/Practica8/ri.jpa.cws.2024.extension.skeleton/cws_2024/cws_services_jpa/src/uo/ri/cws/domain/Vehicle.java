package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle extends BaseEntity {

    private String plateNumber;
    private String make;
    private String model;

    private Client client;

    private VehicleType vehicleType;

    private Set<WorkOrder> workOrders = new HashSet<>();

    Vehicle() {

    }

    public Vehicle(String plateNumber, String make, String model) {
        ArgumentChecks.isNotBlank(plateNumber, "invalid plate");
        ArgumentChecks.isNotBlank(make, "invalid make");
        ArgumentChecks.isNotBlank(model, "invalid model");

        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
    }

    public Vehicle(String plateNumber) {
        this(plateNumber, "no-make", "no-model");
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
        this.client = client;

    }

    void _setVehicleType(VehicleType type) {
        this.vehicleType = type;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
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
