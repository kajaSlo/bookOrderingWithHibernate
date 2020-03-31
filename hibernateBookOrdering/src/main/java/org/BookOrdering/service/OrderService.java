package org.BookOrdering.service;

import java.util.Collection;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.Order;

public interface OrderService {

	public void createAnOrder(Order order, Collection<Book> booksInAnOrder, Client client);
	public void deleteAnOrder(int orderId);
	public Order readOrder(int orderId);
	
}
