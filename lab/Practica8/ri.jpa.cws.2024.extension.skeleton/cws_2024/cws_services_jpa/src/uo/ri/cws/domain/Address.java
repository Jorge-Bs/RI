package uo.ri.cws.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * This class is a Value Type, thus
 *    - no setters
 *	  - hashcode and equals over all attributes
 */
@Embeddable
public class Address {
	@Basic(optional = false) private String street;
	@Basic(optional = false) private String city;
	@Basic(optional = false) private String zipCode;
	
	Address(){
		
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
	
	

	
}
