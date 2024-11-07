package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TSparePart")
public class SparePart extends BaseEntity{
	// natural attributes
	@Column(unique = true) private String code;
	@Basic(optional = false) private String description;
	@Basic(optional = false) private double price;

	// accidental attributes
	@OneToMany(mappedBy = "sparePart" ) private Set<Substitution> substitutions = new HashSet<>();

	
	SparePart() {}

	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotEmpty(code, "invalid code");
		ArgumentChecks.isNotEmpty(description, "invalid description");
		ArgumentChecks.isTrue(price>=0.0, "invalid price");
		
		this.code = code;
		this.description = description;
		this.price = price;

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
