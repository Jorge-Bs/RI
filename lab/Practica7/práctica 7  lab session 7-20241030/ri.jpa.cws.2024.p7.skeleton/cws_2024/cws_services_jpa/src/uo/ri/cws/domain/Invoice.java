package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TInvoice")
public class Invoice extends BaseEntity{
	public enum InvoiceState { NOT_YET_PAID, PAID }

	// natural attributes
	@Column(unique = true) private Long number;
	@Basic(optional = false) private LocalDate date;
	@Basic(optional = false) private double amount;
	@Basic(optional = false) private double vat;
	@Basic(optional = false) private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy = "invoice") private Set<WorkOrder> workOrders = new HashSet<>();
	@Transient private Set<Charge> charges = new HashSet<>();

	Invoice(){
		
	}
	
	public Invoice(Long number) {
		this(number,LocalDate.now(),List.of());
	}

	public Invoice(Long number, LocalDate date) {
		this(number,date,List.of());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(number, "invalid number");
		ArgumentChecks.isTrue(number>=0,"precio invalido");
		ArgumentChecks.isNotNull(date, "invalid date");
		ArgumentChecks.isNotNull(workOrders, "invalid workOrders");
		
		this.number=number;
		this.date=date;
		workOrders.forEach(t->addWorkOrder(t));
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double precio=0;
		for (WorkOrder order : workOrders) {
			precio+=order.getAmount();
		}
		LocalDate date = LocalDate.of(2012, 7, 1);
		if(this.date.isAfter(date)) {
			 amount=precio*1.21;
		}else {
			amount=precio*1.18;
		}
		vat=amount-precio;
		amount=Round.twoCents(amount);
		vat=Round.twoCents(vat);
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(isNotSettled(),"invoice is settled");
		
		Associations.Bill.link(this, workOrder);
		computeAmount();
		workOrder.markAsInvoiced();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(isNotSettled(),"the workorder is finished");
		Associations.Bill.unlink(this, workOrder);
		computeAmount();
		workOrder.markBackToFinished();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		StateChecks.isTrue(isNotSettled(),"the invoiced is settled");
		double[] amount = {0};
		charges.forEach(cargo->amount[0]=amount[0]+cargo.getAmount());
		
		StateChecks.isTrue(amount[0]>=this.amount,"invalid amount cantity");
		this.state=InvoiceState.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}
	
	public boolean isNotSettled() {
		return InvoiceState.NOT_YET_PAID.equals(state);
	}
	
	public boolean isSettled() {
		return InvoiceState.PAID.equals(state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [number=");
		builder.append(number);
		builder.append(", date=");
		builder.append(date);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", vat=");
		builder.append(vat);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}


	
	

}
