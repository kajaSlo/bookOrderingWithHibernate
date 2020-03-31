package org.BookOrdering.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "BooksOrder")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int order_number;
	public Date date;
	
	@JoinTable(name = "Book_BooksOrder",
			joinColumns = {
				@JoinColumn(name = "order_number")
			},
			inverseJoinColumns = {
				@JoinColumn(name = "book_id")
			})
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	public Collection<Book> book = new ArrayList<Book> ();

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "client_id")
	public Client client = new Client();
	
	public Order() {}
	
	public Order(int order_number, Date date) {
		super();
		this.order_number = order_number;
		this.date = date;
	}

	public Collection<Book> getBook() {
		return book;
	}

	public void setBook(Collection<Book> book) {
		this.book = book;
	}

	public int getOrder_number() {
		return order_number;
	}
	
	public void setOrder_number(int order_number) {
		this.order_number = order_number;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
