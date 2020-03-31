package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.ConfigurationClass;
import org.BookOrdering.models.Order;
import org.BookOrdering.service.BookService;
import org.BookOrdering.service.BookServiceImp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestingBookServiceLayer {

	public static BookService bookService;
	static Session session;
	static Transaction transaction;
	static List<Integer> listOfBooksIds = new ArrayList<>();

	@BeforeClass
	public static void setup() {
		bookService = new BookServiceImp();
		transaction = null;
		session = ConfigurationClass.getSessionFactory().openSession();
		transaction = session.beginTransaction();
	}

	@Test
	public void testAddingNewBook() {
		Book book = new Book();
		book.setAuthor("Lasse Koskela");
		book.setPrice(10.14 f);
		book.setTitle("Practical TDD");
		book.setPublishing_house("Pearson");

		bookService.createBook(book);
		listOfBooksIds.add(book.getId());

		assertEquals(bookService.readBook(book.getId()).getAuthor(), "Lasse Koskela");
		assertEquals(bookService.readBook(book.getId()).getPrice(), 10.14 f, 10.14 f);
		assertEquals(bookService.readBook(book.getId()).getTitle(), "Practical TDD");
		assertEquals(bookService.readBook(book.getId()).getPublishing_house(), "Pearson");
	}

	@Test
	public void testGettingBooksByAuthor() {
		Book book = new Book();
		book.setAuthor("Kevin Wayne");
		book.setPrice(30.34 f);
		book.setTitle("Algorithms");
		book.setPublishing_house("Reed Elsevier");

		bookService.createBook(book);

		Book book2 = new Book();
		book2.setAuthor("Kevin Wayne");
		book2.setPrice(50.50 f);
		book2.setTitle("Clean Code");
		book2.setPublishing_house("Wolters Kluwer");

		bookService.createBook(book2);

		List<Book> books = bookService.getBooksByAuthor("Kevin Wayne");
		listOfBooksIds.add(book.getId());
		listOfBooksIds.add(book2.getId());

		boolean isCleanCodeBookInAList = false;
		boolean isAlgorithmsBookInAList = false;

		for (Book bookList: books) {

			if (bookList.getTitle().equals("Clean Code")) {
				isCleanCodeBookInAList = true;
			}

			if (bookList.getTitle().equals("Algorithms")) {
				isAlgorithmsBookInAList = true;
			}

		}

		assertTrue(isCleanCodeBookInAList);
		assertTrue(isAlgorithmsBookInAList);
	}
	
	@Test
	public void testReadingBook() {
		Book book = new Book();
		book.setAuthor("Vincent Massol");
		book.setPrice(145.99 f);
		book.setTitle("JUnit in Action");
		book.setPublishing_house("Random House");

		bookService.createBook(book);
		listOfBooksIds.add(book.getId());

		Book bookReadFromDatabase = bookService.readBook(book.getId());

		assertEquals(book.getAuthor(), bookReadFromDatabase.getAuthor());
		assertEquals(book.getPrice(), bookReadFromDatabase.getPrice(), 10.99 f);
		assertEquals(book.getTitle(), bookReadFromDatabase.getTitle());
		assertEquals(book.getPublishing_house(), bookReadFromDatabase.getPublishing_house());
	}
	
	@Test
	public void testUpdatingBookSettingNewPrice() {
		Book book = new Book();
		book.setAuthor("Christian Bauer");
		book.setPrice(15.45 f);
		book.setTitle("Hibernate in Action");
		book.setPublishing_house("Hachette Livre");

		bookService.createBook(book);
		bookService.updateBookSetNewPrice(book.getId(), 20.45 f);
		listOfBooksIds.add(book.getId());

		assertEquals("Christian Bauer", bookService.readBook(book.getId()).getAuthor());
		assertEquals(20.45 f, bookService.readBook(book.getId()).getPrice(), 20.45 f);
		assertEquals("Hibernate in Action", bookService.readBook(book.getId()).getTitle());
	}

	@Test
	public void testDeletingBook() {
		Book book = new Book();
		book.setAuthor("Brandon Turner");
		book.setPrice(90.99 f);
		book.setTitle("Rental Property Investing");
		book.setPublishing_house("Grupo Planeta");

		bookService.createBook(book);
		bookService.deleteBook(book.getId());

		assertNull(bookService.readBook(book.getId()));
	}

	@Test
	public void testDeletingBooksWithAuthor() {
		Book book = new Book();
		book.setAuthor("Robert Biltzer");
		book.setPrice(273.99 f);
		book.setTitle("Thinking Mathematically");
		book.setPublishing_house("McGrawHill Education");

		bookService.createBook(book);

		Book book2 = new Book();
		book2.setAuthor("Robert Biltzer");
		book2.setPrice(273.99 f);
		book2.setTitle("Thinking Mathematically");
		book2.setPublishing_house("Holtzbrinck");

		bookService.createBook(book2);

		bookService.deleteBooksWithAuthor("Robert Biltzer");

		assertNull(bookService.readBook(book.getId()));
		assertNull(bookService.readBook(book2.getId()));
	}

	@AfterClass
	public static void deletingCreatedBooks() {
		Iterator it = listOfBooksIds.iterator();
		if (session != null) {
			while (it.hasNext()) {
				int itrBook = (int) it.next();
				Book bookToBeDeleted = (Book) session.load(Book.class, itrBook);
				session.delete(bookToBeDeleted);
			}
			session.getTransaction().commit();
			session.close();
		}
	}

}