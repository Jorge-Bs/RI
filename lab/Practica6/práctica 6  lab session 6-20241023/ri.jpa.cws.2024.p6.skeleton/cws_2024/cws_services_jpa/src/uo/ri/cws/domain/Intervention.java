package uo.ri.cws.domain;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class Intervention {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Set<Substitution> substitutions = new HashSet<>();
	
	

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
	public int hashCode() {
		return Objects.hash(mechanic, workOrder, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date) && Objects.equals(mechanic, other.mechanic)
				&& Objects.equals(workOrder, other.workOrder);
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
