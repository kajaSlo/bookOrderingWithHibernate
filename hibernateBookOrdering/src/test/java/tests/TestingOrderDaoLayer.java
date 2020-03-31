package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.BookOrdering.Dao.BookDao;
import org.BookOrdering.Dao.BookDaoImp;
import org.BookOrdering.Dao.ClientDao;
import org.BookOrdering.Dao.ClientDaoImp;
import org.BookOrdering.Dao.OrderDao;
import org.BookOrdering.Dao.OrderDaoImp;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.ConfigurationClass;
import org.BookOrdering.models.Order;
import org.BookOrdering.service.BookService;
import org.BookOrdering.service.BookServiceImp;
import org.BookOrdering.service.ClientService;
import org.BookOrdering.service.ClientServiceImp;
import org.BookOrdering.service.OrderService;
import org.BookOrdering.service.OrderServiceImp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

public class TestingOrderDaoLayer {

	public static OrderService orderService;
	public static ClientService clientService;
	public static BookService bookService;
	static Client client;
	static Book book;
	static Transaction transaction;
	static Session session;

	@BeforeClass
	public static void setup() {
		orderService = new OrderServiceImp();
		clientService = new ClientServiceImp();
		bookService = new BookServiceImp();

		client = new Client();
		client.setName("Mary");
		client.setSurname("Louise");
		client.setPhone_number(123456654);
		client.setStreet_name("2nd Street West");
		client.setHouse_number(8);
		client.setApartment_number(9);
		client.setPostal_code(99345);

		clientService.createAClient(client);

		book = new Book();
		book.setAuthor("Erwin Chemerinsky");
		book.setPrice(244.15f);
		book.setTitle("Constitutonal law");
		book.setPublishing_house("Wiley");

		bookService.createBook(book);
		transaction = null;
		session = ConfigurationClass.getSessionFactory().openSession();
		transaction = session.beginTransaction();
	}

	static List<Integer> listOfOrderIds = new ArrayList <>();

	@Test
	public void testAddingNewOrder() {
		Date date = new Date(3, 12, 1990);
		Order order = new Order();

		Book bookReadFromDatabase = bookService.readBook(book.getId());

		order.setDate(date);

		Collection<Book> books = new ArrayList <>();
		books.add(bookReadFromDatabase);

		Collection<Order> newOrder = new ArrayList <>();
		newOrder.add(order);

		Client clientReadFromDatabase = clientService.readClient(client.getClient_id());
		clientReadFromDatabase.setOrders(newOrder);
		orderService.createAnOrder(order, books, clientReadFromDatabase);

		Order orderReadFromDatabase = orderService.readOrder(order.getOrder_number());

		Collection <Book> bookreadFromOrderRedFromDatabase = orderReadFromDatabase.getBook();

		String readFromDatabaseBookTitle = null;
		Float readFromDatabaseBookPrice = null;
		String readFromDatabaseBookAuthor = null;
		String readFromDatabaseBooksPublishingHouse = null;

		Iterator<Book> it = bookreadFromOrderRedFromDatabase.iterator();
		
		while (it.hasNext()) {
			Book itrBook = it.next();
			readFromDatabaseBookAuthor = itrBook.getAuthor();
			readFromDatabaseBookPrice = itrBook.getPrice();
			readFromDatabaseBookTitle = itrBook.getTitle();
			readFromDatabaseBooksPublishingHouse = itrBook.getPublishing_house();
		}

		Client clientReadFromDatabaseAssignedToActualOrder = orderReadFromDatabase.getClient();

		listOfOrderIds.add(order.order_number);

		assertEquals(date, orderService.readOrder(order.getOrder_number()).getDate());
		assertEquals(books.size(), orderService.readOrder(order.getOrder_number()).getBook().size());
		assertEquals("Constitutonal law", readFromDatabaseBookTitle);
		assertEquals(244.15f, readFromDatabaseBookPrice, 244.15f);
		assertEquals("Erwin Chemerinsky", readFromDatabaseBookAuthor);
		assertEquals("Wiley", readFromDatabaseBooksPublishingHouse);
		assertEquals("Mary", clientReadFromDatabaseAssignedToActualOrder.getName());
		assertEquals("Louise", clientReadFromDatabaseAssignedToActualOrder.getSurname());
		assertEquals(123456654, clientReadFromDatabaseAssignedToActualOrder.getPhone_number());
		assertEquals("2nd Street West", clientReadFromDatabaseAssignedToActualOrder.getStreet_name());
		assertEquals(8, clientReadFromDatabaseAssignedToActualOrder.getHouse_number());
		assertEquals(9, clientReadFromDatabaseAssignedToActualOrder.getApartment_number());
		assertEquals(99345, clientReadFromDatabaseAssignedToActualOrder.getPostal_code());
	}

	@Test
	public void testReadingAnOrder() {
		Date date = new Date(11, 02, 1995);
		Order order = new Order();

		Book bookReadFromDatabase = bookService.readBook(book.getId());

		order.setDate(date);

		Collection<Book> books = new ArrayList <>();
		books.add(bookReadFromDatabase);

		Collection<Order> newOrder = new ArrayList <>();
		newOrder.add(order);

		Client clientReadFromDatabase = clientService.readClient(client.getClient_id());
		clientReadFromDatabase.setOrders(newOrder);
		orderService.createAnOrder(order, books, clientReadFromDatabase);

		Order orderReadFromDatabase = orderService.readOrder(order.getOrder_number());

		Collection<Book> orderFromDatabase = orderReadFromDatabase.getBook();

		String readFromDatabaseBookTitleAssignedToAnOrder = null;
		Float readFromDatabaseBookPriceAssignedToAnOrder = null;
		String readFromDatabaseBookAuthorAssignedToAnOrder = null;
		String readFromDatabaseBooksPublishingHouse = null;


		Iterator<Book> it = orderFromDatabase.iterator();

		while (it.hasNext()) {
			Book itrBook = it.next();
			readFromDatabaseBookTitleAssignedToAnOrder = itrBook.getTitle();
			readFromDatabaseBookPriceAssignedToAnOrder = itrBook.getPrice();
			readFromDatabaseBookAuthorAssignedToAnOrder = itrBook.getAuthor();
			readFromDatabaseBooksPublishingHouse = itrBook.getPublishing_house();
		}

		Client clientReadFromDatabaseAssignedToAnOrder = orderReadFromDatabase.getClient();

		listOfOrderIds.add(order.order_number);

		assertEquals(order.getDate(), orderReadFromDatabase.getDate());
		assertEquals(bookReadFromDatabase.getTitle(), readFromDatabaseBookTitleAssignedToAnOrder);
		assertEquals(bookReadFromDatabase.getPrice(), readFromDatabaseBookPriceAssignedToAnOrder, 244.15f);
		assertEquals(bookReadFromDatabase.getAuthor(), readFromDatabaseBookAuthorAssignedToAnOrder);
		assertEquals(bookReadFromDatabase.getPublishing_house, readFromDatabaseBooksPublishingHouseAssignedToAnOrder);
		assertEquals(clientReadFromDatabase.getName(), clientReadFromDatabaseAssignedToAnOrder.getName());
		assertEquals(clientReadFromDatabase.getSurname(), clientReadFromDatabaseAssignedToAnOrder.getSurname());
		assertEquals(clientReadFromDatabase.getPhone_number(), clientReadFromDatabaseAssignedToAnOrder.getPhone_number());
		assertEquals(clientReadFromDatabase.getStreet_name(), clientReadFromDatabaseAssignedToAnOrder.getStreet_name());
		assertEquals(clientReadFromDatabase.getHouse_number(), clientReadFromDatabaseAssignedToAnOrder.getHouse_number());
		assertEquals(clientReadFromDatabase.getApartment_number(), clientReadFromDatabaseAssignedToAnOrder.getApartment_number());
		assertEquals(clientReadFromDatabase.getPostal_code(), clientReadFromDatabaseAssignedToAnOrder.getPostal_code());
	}

	@Test
	public void testDeletingAnOrder() {
		Date date = new Date(25, 11, 1890);
		Order order = new Order();

		Book bookReadFromDatabase = bookService.readBook(book.getId());

		order.setDate(date);

		Collection<Book> books = new ArrayList <>();
		books.add(bookReadFromDatabase);

		Collection<Order> newOrder = new ArrayList <>();
		newOrder.add(order);
		Client clientReadFromDatabase = clientService.readClient(client.getClient_id());
		clientReadFromDatabase.setOrders(newOrder);
		orderService.createAnOrder(order, books, clientReadFromDatabase);
		orderService.deleteAnOrder(order.getOrder_number());

		assertNull(orderService.readOrder(order.getOrder_number()));
	}

	@AfterClass
	public static void deletingCreatedBookClientAndOrders() {
		Iterator it = listOfOrderIds.iterator();
		while (it.hasNext()) {
			int itrOrder = (int) it.next();
			Order orderToBeDeleted = (Order) session.load(Order.class, itrOrder);
			session.delete(orderToBeDeleted);
		}

		Book bookToBeDeleted = (Book) session.load(Book.class, book.getId());
		session.delete(bookToBeDeleted);

		Client clientToBeDeleted = (Client) session.load(Client.class, client.getClient_id());
		session.delete(clientToBeDeleted);
		session.getTransaction().commit();
		session.close();
	}

}
