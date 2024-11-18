package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class Address {
    private String street;
    private String city;
    private String zipCode;

    Address() {

    }

    public Address(String street, String city, String zipCode) {
        ArgumentChecks.isNotBlank(street, "invalid street");
        ArgumentChecks.isNotBlank(city, "invalid city");
        ArgumentChecks.isNotBlank(zipCode, "invalid zipCode");

        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", zipCode="
            + zipCode + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        return Objects.equals(city, other.city)
            && Objects.equals(street, other.street)
            && Objects.equals(zipCode, other.zipCode);
    }

}
