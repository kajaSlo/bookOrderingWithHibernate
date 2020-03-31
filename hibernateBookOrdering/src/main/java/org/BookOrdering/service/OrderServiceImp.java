package org.BookOrdering.service;

import java.util.Collection;
import org.BookOrdering.Dao.OrderDao;
import org.BookOrdering.Dao.OrderDaoImp;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.Order;

public class OrderServiceImp implements OrderService {

	OrderDao orderDao = new OrderDaoImp();

	public void createAnOrder(Order order, Collection<Book> booksInAnOrder, Client client) {
		orderDao.createAnOrder(order, booksInAnOrder, client);
	}

	public void deleteAnOrder(int orderId) {
		orderDao.deleteAnOrder(orderId);
	}

	public Order readOrder(int orderId) {
		return orderDao.readOrder(orderId);
	}

}