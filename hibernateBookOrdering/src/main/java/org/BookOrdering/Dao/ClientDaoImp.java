package org.BookOrdering.Dao;

import java.util.Collection;
import java.util.Iterator;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.ConfigurationClass;
import org.BookOrdering.models.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientDaoImp implements ClientDao {

	public void createAClient(Client client) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.save(client);
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

	public void createAClientAndAnOrder(Client client, Order order, Collection<Book> books) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			client.getOrders().add(order);
			Iterator it = books.iterator();
			while (it.hasNext()) {
				Book itrBook = (Book) it.next();
				Book bookInAnOrder = session.load(Book.class, itrBook.getId());
				order.getBook().add(bookInAnOrder);
			}
			session.persist(client);
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

	public void deleteAClient(int clientId) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			Client client = (Client) session.load(Client.class, clientId);
			session.delete(client);
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

	public Client readClient(int clientId) {
		Client client = null;
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			client = session.get(Client.class, clientId);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {

			session.close();
		}
		return client;
	}

	public void updatePhoneNumber(int cliendId, int newPhoneNumber) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			Client client = (Client) session.load(Client.class, cliendId);
			client.setPhone_number(newPhoneNumber);
			session.update(client);
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

}