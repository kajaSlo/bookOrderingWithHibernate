package org.BookOrdering.Dao;

import java.util.Collection;
import java.util.Iterator;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.ConfigurationClass;
import org.BookOrdering.models.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderDaoImp implements OrderDao {

	public void createAnOrder(Order order, Collection<Book> booksInAnOrder, Client client) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			Client clientLoadedFromDatabase = session.load(Client.class, client.getClient_id());
			Iterator it = booksInAnOrder.iterator();
			while (it.hasNext()) {
				Book itrBook = (Book) it.next();
				Book bookInAnOrder = session.load(Book.class, itrBook.getId());
				order.getBook().add(bookInAnOrder);
			}
			order.setClient(clientLoadedFromDatabase);
			session.save(order);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteAnOrder(int orderId) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			Order order = (Order) session.load(Order.class, orderId);
			session.delete(order);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Order readOrder(int orderId) {
		Order order = null;
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			order = session.get(Order.class, orderId);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}

}
