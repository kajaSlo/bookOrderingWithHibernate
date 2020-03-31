package org.BookOrdering.Dao;

import java.util.List;
import org.BookOrdering.models.Book;

public interface BookDao {

	public void createBook(Book book);
	public void deleteBook(int bookId);
	public void deleteBooksWithAuthor(String author);
	public void updateBookSetNewPrice(int bookId, float newPrice);
	public Book readBook(int bookId);
	public List<Book> getBooksByAuthor(String author);
	
}
