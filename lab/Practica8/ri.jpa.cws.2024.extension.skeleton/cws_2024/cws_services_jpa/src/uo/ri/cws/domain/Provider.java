package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Provider extends BaseEntity{
	
	private String nif;
	private String name;
	private String phone;
	private String email;
	
	private Set<Supply> supplies = new HashSet<>();
	
	private Set<Order> orders = new HashSet<>();
	
	
	Provider(){}
	
	
	public Provider(String nif, String name, String phone, String email) {
		ArgumentChecks.isNotEmpty(nif, "invalid nif");
		ArgumentChecks.isNotEmpty(name, "invalid name");
		ArgumentChecks.isNotEmpty(phone, "invalid phone");
		ArgumentChecks.isNotEmpty(email, "invalid email");
		
		this.nif = nif;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public Provider(String nif) {
		this(nif,"no-name","no-phone","no-email");
	}
	
	
	Set<Order> _getOrders() {
		return orders;
	}
	
	public Set<Order> getOrders(){
		return new HashSet<>(orders);
	}


	public Set<Supply> getSupplies() {
		return new HashSet<>(supplies);
	}


	Set<Supply> _getSupplies() {
		return supplies;
	}


	public String getNif() {
		return nif;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "Provider [nif=" + nif + ", name=" + name + ", phone=" + phone + ", email=" + email + "]";
	}


	public void setName(String name) {
		this.name=name;
	}


	public void setEmail(String email) {
		this.email=email;
	}


	public void setPhone(String phone) {
		this.phone=phone;
	}
	
	
	
	
}
