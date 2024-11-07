package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;
@Entity
@Table(name = "TWorkOrder", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"date","vehicle_id"})
		})
public class WorkOrder extends BaseEntity{
	public enum WorkOrderState {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	@Basic(optional = false)  private LocalDateTime date;
	@Basic(optional = false)private String description;
	@Basic(optional = false)private double amount = 0.0;
	@Basic(optional = false)private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	@ManyToOne private Vehicle vehicle;
	@ManyToOne private Mechanic mechanic;
	@ManyToOne private Invoice invoice;
	@OneToMany(mappedBy = "workOrder") private Set<Intervention> interventions = new HashSet<>();

	
	WorkOrder(){
		
	}
	
	
	
	
	public WorkOrder( Vehicle vehicle,LocalDateTime date, String description) {
		ArgumentChecks.isNotNull(vehicle, "invalid vehicle");
		ArgumentChecks.isNotNull(date, "invalid date");
		ArgumentChecks.isNotNull(description, "invalid description");
		
		//EL vehiculo esta mal
		//la relacion se establece en ambos sentidos
		//this.vehicle = vehicle;
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.description = description;
		Associations.Fix.link(vehicle, this);//Solucion
	}

	public WorkOrder(Vehicle vehiculo, String description) {
		this(vehiculo,LocalDateTime.now(),description);
	}
	
	

	public WorkOrder(Vehicle vehicle, LocalDateTime now) {
		this(vehicle,now,"");
	}

	public WorkOrder(Vehicle vehicle) {
		this(vehicle,LocalDateTime.now());
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderState getState() {
		return state;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(isFinished(),"the workorder is not finished");
		StateChecks.isNotNull(invoice, "there is not link with an invoice");
		this.state=WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		StateChecks.isTrue(state.equals(WorkOrderState.ASSIGNED),"the workorder is not assigned");
		StateChecks.isNotNull(mechanic, "there is not link with an mechanic");
		this.state=WorkOrderState.FINISHED;
		computeAmuount();
	}

	private void computeAmuount() {
		double amount=0.0;
		for (Intervention inte : interventions) {
			amount+=inte.getAmount();
		}
		this.amount=amount;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(isInvoiced(),"the workorder is not invoiced");
		StateChecks.isNull(invoice,"is still linked with the invoice");
		this.state=WorkOrderState.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		StateChecks.isTrue(isOpen(),"the workorder is not open");
		StateChecks.isNull(this.mechanic, "there is mechanic");
		
		Associations.Assign.link(mechanic, this);
		state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void desassign() {
		StateChecks.isTrue(state.equals(WorkOrderState.ASSIGNED),"is not assigned");
		
		Associations.Assign.unlink(mechanic, this);
		state=WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {
		StateChecks.isTrue(isFinished(),"the workorder is finished");
		state=WorkOrderState.ASSIGNED;
		desassign();
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date) && Objects.equals(vehicle, other.vehicle);
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date 
				+ ", description=" + description 
				+ ", amount=" + amount 
				+ ", state=" + state
				+ ", vehicle=" + vehicle 
				+ ", mechanic=" + mechanic 
				+ ", invoice=" + invoice 
				+ "]";
	}
	
	public boolean isFinished() {
		return WorkOrderState.FINISHED.equals(state);
	}
	
	public boolean isOpen() {
		return WorkOrderState.OPEN.equals(state);
	}

	public boolean isInvoiced() {
		return WorkOrderState.INVOICED.equals(state);
	}
	
	

}
