package uo.ri.cws.domain;



import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;


public class Substitution extends BaseEntity{
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	Intervention intervention;

	Substitution(){
		
	}

	public Substitution( SparePart sparePart, Intervention intervention,int quantity) {
		ArgumentChecks.isNotNull(sparePart,"invalid sparePart");
		ArgumentChecks.isNotNull(intervention,"invalid intervention");
		ArgumentChecks.isTrue(quantity>0,"invalid quantity");
		
		this.quantity = quantity;
		Associations.Substitute.link(sparePart, this, intervention);
	}


	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public double getAmount() {
		return quantity*sparePart.getPrice();
	}

}
