package org.BookOrdering.service;

import java.util.List;
import org.BookOrdering.Dao.BookDao;
import org.BookOrdering.Dao.BookDaoImp;
import org.BookOrdering.models.Book;

public class BookServiceImp implements BookService {

	BookDao bookDao = new BookDaoImp();

	public void createBook(Book book) {
		bookDao.createBook(book);
	}

	public void deleteBook(int bookId) {
		bookDao.deleteBook(bookId);
	}

	public void deleteBooksWithAuthor(String author) {
		bookDao.deleteBooksWithAuthor(author);
	}

	public void updateBookSetNewPrice(int bookId, float newPrice) {
		bookDao.updateBookSetNewPrice(bookId, newPrice);
	}

	public Book readBook(int bookId) {
		return bookDao.readBook(bookId);
	}

	public List<Book> getBooksByAuthor(String author) {
		return bookDao.getBooksByAuthor(author);
	}

}