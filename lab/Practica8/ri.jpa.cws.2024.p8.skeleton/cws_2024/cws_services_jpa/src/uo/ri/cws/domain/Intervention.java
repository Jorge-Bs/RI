package uo.ri.cws.domain;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TIntervention",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"workOrder_id","mechanic_id","date"})
		})
public class Intervention extends BaseEntity{
	// natural attributes
	@Column(unique = true) private LocalDateTime date;
	@Basic(optional = false) private int minutes;

	// accidental attributes
	@ManyToOne private WorkOrder workOrder;
	@ManyToOne private Mechanic mechanic;
	@OneToMany(mappedBy = "intervention") private Set<Substitution> substitutions = new HashSet<>();
	
	Intervention() {
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int min) {
		this(mechanic,workOrder,LocalDateTime.now(),min);
	}
	
	public Intervention(Mechanic mechanic, WorkOrder workOrder,LocalDateTime date, int min) {
		ArgumentChecks.isNotNull(mechanic, "invalid mechanic");
		ArgumentChecks.isNotNull(workOrder, "invalid workOrder");
		ArgumentChecks.isNotNull(date, "invalid min");
		ArgumentChecks.isTrue(min>=0,"invalid price");
		
		
		Associations.Intervene.link(workOrder, this, mechanic);
		
		this.minutes=min;
		this.date=date.truncatedTo(ChronoUnit.MILLIS);

	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}
	
	

	public LocalDateTime getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	@Override
	public String toString() {
		return "Intervention [date=" + date 
				+ ", minutes=" + minutes 
				+ ", workOrder=" + workOrder 
				+ ", mechanic="+ mechanic 
				+ "]";
	}

	public Double getAmount() {
		double mins = minutes/60.0;
		double price= workOrder.getVehicle().getVehicleType().getPricePerHour()*mins;
		for (Substitution substitution : substitutions) {
			price+= substitution.getAmount();
		}
		 return price;
	}
	
	

}
