package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;


import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;


public class SparePart extends BaseEntity{
	// natural attributes
	private String code;
	private String description;
	private double price;

	// accidental attributes
	private Set<Substitution> substitutions = new HashSet<>();

	
	SparePart() {}

	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotEmpty(code, "invalid code");
		ArgumentChecks.isNotEmpty(description, "invalid description");
		ArgumentChecks.isTrue(price>=0.0, "invalid price");
		
		this.code = code;
		this.description = description;
		this.price = price;

	}
	




	public SparePart(String code) {
		this(code,"no-description",0.0);
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	public String getCode() {
		return code;
	}



	public String getDescription() {
		return description;
	}



	public double getPrice() {
		return price;
	}



	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

}
