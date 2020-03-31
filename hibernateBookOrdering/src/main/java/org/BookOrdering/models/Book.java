package org.BookOrdering.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int book_id;
	public String title;
	public String author;
	public float price;
	public String publishing_house;

	@ManyToMany(mappedBy = "book", fetch = FetchType.EAGER)
	public Collection<Order> orders = new ArrayList<Order> ();
	
	public Book() {}
	
	public Book(int book_id, String title, String author, float price, String publishing_house,
		Collection<Order> orders) {
		this.book_id = book_id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.publishing_house = publishing_house;
		this.orders = orders;
	}

	public int getId() {
		return book_id;
	}

	public void setId(int id) {
		this.book_id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getPublishing_house() {
		return publishing_house;
	}

	public void setPublishing_house(String publishing_house) {
		this.publishing_house = publishing_house;
	}
	
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

}
