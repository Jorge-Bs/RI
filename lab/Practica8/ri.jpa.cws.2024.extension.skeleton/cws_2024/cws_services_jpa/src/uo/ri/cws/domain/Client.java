package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Client extends BaseEntity {

    private String nif;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;

    private Set<Vehicle> vehicles = new HashSet<>();

    private Set<PaymentMean> paymentMeans = new HashSet<>();

    Client() {

    }

    public Client(String nif, String name, String surname, String email,
        String phone, Address address) {
        ArgumentChecks.isNotBlank(nif, "Nif invalido");
        ArgumentChecks.isNotBlank(name, "Nombre invalido");
        ArgumentChecks.isNotBlank(surname, "Apellido invalido");
        ArgumentChecks.isNotBlank(email, "Correo invalido");
        ArgumentChecks.isNotBlank(nif, "Nif invalido");
        ArgumentChecks.isNotNull(address, "direccion invalida");

        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client(String nif, String name, String apellido) {
        this(nif, name, apellido, "no-email", "no-phone",
            new Address("no-street", "no-city", "no-zipCode"));
    }

    public Client(String nif) {
        this(nif, "no-name", "no-surname");
    }

    public String getNif() {
        return nif;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }

    public Set<PaymentMean> getPaymentMeans() {
        return new HashSet<>(paymentMeans);
    }

    public void setPaymentMeans(Set<PaymentMean> paymentMeans) {
        this.paymentMeans = paymentMeans;
    }

    Set<Vehicle> _getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Client [nif=");
        builder.append(nif);
        builder.append(", name=");
        builder.append(name);
        builder.append(", surname=");
        builder.append(surname);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", address=");
        builder.append(address);
        builder.append("]");
        return builder.toString();
    }

    public Set<PaymentMean> _getPaymentMeans() {
        return paymentMeans;
    }

    public void setAddress(Address address) {
        ArgumentChecks.isNotNull(address, "invalid addres");
        this.address = address;
    }

}
