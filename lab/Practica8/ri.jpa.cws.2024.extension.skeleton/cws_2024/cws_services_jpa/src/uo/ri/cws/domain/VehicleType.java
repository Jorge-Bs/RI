package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType extends BaseEntity {

    private String name;
    private double pricePerHour;

    private Set<Vehicle> vehicles = new HashSet<>();

    VehicleType() {
    }

    public VehicleType(String name, double pricePerHour) {
        ArgumentChecks.isNotBlank(name, "invalid name");
        ArgumentChecks.isTrue(pricePerHour >= 0.0, "invalid price");

        this.name = name;
        this.pricePerHour = pricePerHour;
    }

    public VehicleType(String name) {
        this(name, 0.0);
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
        return new HashSet<>(vehicles);
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

}
