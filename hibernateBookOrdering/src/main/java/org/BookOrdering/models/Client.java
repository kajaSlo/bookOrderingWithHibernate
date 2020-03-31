package org.BookOrdering.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "Client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int client_id;
	public String name;
	public String surname;
	public int phone_number;
	public String street_name;
	public int house_number;
	public int apartment_number;
	public int postal_code;
	
	public Client() {}
	
	public Client(int client_id, String name, String surname, int phone_number, String street_name, int house_number,
			int apartment_number, int postal_code, Collection<Order> orders) {
			this.client_id = client_id;
			this.name = name;
			this.surname = surname;
			this.phone_number = phone_number;
			this.street_name = street_name;
			this.house_number = house_number;
			this.apartment_number = apartment_number;
			this.postal_code = postal_code;
			this.orders = orders;
		}

	@OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, targetEntity = Order.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	public Collection<Order> orders = new ArrayList<>();

	public int getClient_id() {
		return client_id;
	}
	
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public int getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(int phone_number) {
		this.phone_number = phone_number;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
	public String getStreet_name() {
		return street_name;
	}
	
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	
	public int getHouse_number() {
		return house_number;
	}
	
	public void setHouse_number(int house_number) {
		this.house_number = house_number;
	}
	
	public int getApartment_number() {
		return apartment_number;
	}
	
	public void setApartment_number(int apartment_number) {
		this.apartment_number = apartment_number;
	}
	
	public int getPostal_code() {
		return postal_code;
	}
	
	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}

}