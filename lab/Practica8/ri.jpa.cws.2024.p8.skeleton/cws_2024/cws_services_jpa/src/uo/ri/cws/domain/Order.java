package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Order extends BaseEntity{
	 enum OrderState{
		PENDING, RECEIVED
	}
	

	private String code;
	private LocalDate orderedDate;
	private double amount;
	private LocalDate receptionDate;
	private OrderState state = OrderState.PENDING;
	
	private Provider provider;
	
	private Set<OrderLine> orderLines = new HashSet<>();
	
	Order(){
		
	}
	
	
	public Order(String code, LocalDate orderedDate, double amount, LocalDate receptionDate) {
		ArgumentChecks.isNotEmpty(code, "invalid code");
		ArgumentChecks.isNotNull(orderedDate, "invalid date");
		ArgumentChecks.isTrue(amount>=0.0, "invalid amount");
		ArgumentChecks.isNotNull(state, "invalid state");
		
		this.code = code;
		this.orderedDate = orderedDate;
		this.amount = amount;
		this.receptionDate = receptionDate;
	}
	
	public Order(String code) {
		this(code,LocalDate.now(),0.0,null);
	}
	
	Set<OrderLine> _getOrderLines(){
		return orderLines;
	}
	
	public Set<OrderLine> getOrderLines(){
		return new HashSet<>(orderLines);
	}
	
	public Provider getProvider() {
		return provider;
	}
	
	void _setProvider(Provider provider) {
		this.provider=provider;
	}


	public String getCode() {
		return code;
	}


	public LocalDate getOrderedDate() {
		return orderedDate;
	}


	public double getAmount() {
		return amount;
	}


	public LocalDate getReceptionDate() {
		return receptionDate;
	}


	public OrderState getState() {
		return state;
	}


	@Override
	public String toString() {
		return "Order [code=" + code + ", orderedDate=" + orderedDate + ", amount=" + amount + ", receptionDate="
				+ receptionDate + ", state=" + state + "]";
	}
	
	public void addSparePartFromSupply(Supply supply) {	
		ArgumentChecks.isNotNull(supply, "invalid supply");
		SparePart part = supply.getSparePart();
		
		StateChecks.isTrue(notInOrders(part), "ya existe la pieza en un pedido");
		
		if(part.isUnderStock()) {
			double price = supply.getPrice();
			OrderLine ordeline = new OrderLine(price, part.getQuantityToOrder(),part);
			this.orderLines.add(ordeline);
			this.amount+=ordeline.getAmount();
		}
		
		
	}
	
	private boolean notInOrders(SparePart part) {
		for (OrderLine orderLine : orderLines) {
			if(orderLine.getSparePart().equals(part)) {
				return false;
			}
		}
		return true;
	}
	
	
	public void receive() {
		StateChecks.isFalse(isReceived(), "ya se recibio");
		
		this.state=OrderState.RECEIVED;
		
		this.orderLines.forEach((t)->{
			t.receive();
		});
		
		this.receptionDate= LocalDate.now();
		
	}


	public boolean isReceived() {
		return this.state.equals(OrderState.RECEIVED);
	}


	public void removeSparePart(SparePart sp) {
		ArgumentChecks.isNotNull(sp, "null sparePart");
		
		OrderLine line = null;
		
		for (OrderLine orderLine : orderLines) {
			if(orderLine.getSparePart().equals(sp)) {
				line= orderLine;
				break;
			}
		}
		
		if(line!=null) {
			this.orderLines.remove(line);
		}
		
	}


	public boolean isPending() {
		return this.state.equals(OrderState.PENDING);
	}
	
	
	
	
	
}
