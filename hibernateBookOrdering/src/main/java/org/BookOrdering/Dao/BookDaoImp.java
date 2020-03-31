package org.BookOrdering.Dao;

import java.util.List;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.ConfigurationClass;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class BookDaoImp implements BookDao {

	public void createBook(Book book) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.persist(book);
			session.save(book);
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

	public void deleteBook(int bookId) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();	
		try {
			transaction = session.beginTransaction();
			Book book = (Book) session.load(Book.class, bookId);
			session.delete(book);
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

	public void deleteBooksWithAuthor(String author) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			String deleteQuery = "delete from Book where author= :author";
			Query query = session.createQuery(deleteQuery);
			query.setString("author", author);
			query.executeUpdate();
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

	public void updateBookSetNewPrice(int bookId, float newPrice) {
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			Book book = (Book) session.load(Book.class, bookId);
			book.setPrice(newPrice);
			session.update(book);
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

	public Book readBook(int bookId) {
		Book book = null;
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			book = session.get(Book.class, bookId);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return book;
	}

	public List<Book> getBooksByAuthor(String author) {
		List<Book> books = null;
		Transaction transaction = null;
		Session session = ConfigurationClass.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			String queryString = "from Book where author = :author";
			Query query = session.createQuery(queryString);
			query.setParameter("author", author);
			books = (List<Book> ) query.getResultList();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return books;
	}

}
