package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.BookOrdering.Dao.ClientDao;
import org.BookOrdering.Dao.ClientDaoImp;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.ConfigurationClass;
import org.BookOrdering.models.Order;
import org.BookOrdering.service.BookService;
import org.BookOrdering.service.BookServiceImp;
import org.BookOrdering.service.ClientService;
import org.BookOrdering.service.ClientServiceImp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestingClientServiceLayer {

	public static ClientService clientService;
	public static BookService bookService;
	static Book book;
	static Session session;
	static Transaction transaction;
	static Order order;

	@BeforeClass
	public static void setup() {
		clientService = new ClientServiceImp();
		bookService = new BookServiceImp();

		book = new Book();
		book.setAuthor("Tom Hudson");
		book.setPrice(16.18f);
		book.setTitle("How to sell a house");
		book.setPublishing_house("Gakken");

		bookService.createBook(book);

		session = ConfigurationClass.getSessionFactory().openSession();
		transaction = session.beginTransaction();
	}

	static List<Integer> listOfClientsIds = new ArrayList <>();

	@Test
	public void testCreatingAClient() {
		Client client = new Client();
		client.setName("John");
		client.setSurname("Doe");
		client.setPhone_number(234432567);
		client.setStreet_name("Rosemary gardens");
		client.setHouse_number(10);
		client.setApartment_number(2);
		client.setPostal_code(12345);

		clientService.createAClient(client);

		listOfClientsIds.add(client.getClient_id());

		assertEquals("John", clientService.readClient(client.getClient_id()).getName());
		assertEquals("Doe", clientService.readClient(client.getClient_id()).getSurname());
		assertEquals(234432567, clientService.readClient(client.getClient_id()).getPhone_number());
		assertEquals("Rosemary gardens", clientService.readClient(client.getClient_id()).getStreet_name());
		assertEquals(10, clientService.readClient(client.getClient_id()).getHouse_number());
		assertEquals(2, clientService.readClient(client.getClient_id()).getApartment_number());
		assertEquals(12345, clientService.readClient(client.getClient_id()).getPostal_code());
	}

	@Test
	public void testCreatingAClientAndAnOrderAtTheSameTime() {
		Client client = new Client();
		client.setName("Mary");
		client.setSurname("Biel");
		client.setPhone_number(234432567);
		client.setStreet_name("Lantern Lane");
		client.setHouse_number(123);
		client.setApartment_number(5);
		client.setPostal_code(22345);

		Date date = new Date(5, 06, 1991);
		order = new Order();

		Book bookReadFromDatabase = bookService.readBook(book.getId());

		order.setDate(date);

		Collection<Book> books = new ArrayList <>();
		books.add(bookReadFromDatabase);

		Collection<Order> newOrder = new ArrayList <>();

		order.setClient(client);
		newOrder.add(order);
		client.setOrders(newOrder);

		clientService.createAClientAndAnOrder(client, order, books);

		Collection<Order> readFromDatabaseOrderAddedWithAClient = clientService.readClient(client.getClient_id()).getOrders();

		Date readFromDatabaseDate = null;

		Order itrOrder = null;
		Collection<Book> readFromDatabaseBookAddedWhileCreatingAClientAndAnOrder = null;
		Iterator it = readFromDatabaseOrderAddedWithAClient.iterator();
		while (it.hasNext()) {
			itrOrder = (Order) it.next();
			readFromDatabaseDate = itrOrder.getDate();
			readFromDatabaseBookAddedWhileCreatingAClientAndAnOrder = itrOrder.getBook();
		}

		String bookAuthorReadFromDatabase = null;
		Float bookPriceReadFromDatabase = null;
		String bookTitleReadFromDatabase = null;
		String bookPublishingHouseReadFromDatabase = null;

		Iterator bookIt = readFromDatabaseBookAddedWhileCreatingAClientAndAnOrder.iterator();
		while (bookIt.hasNext()) {

			Book itrBook = (Book) it2.next();

			bookAuthorReadFromDatabase = itrBook.getAuthor();
			bookPriceReadFromDatabase = itrBook.getPrice();
			bookTitleReadFromDatabase = itrBook.getTitle();
			bookPublishingHouseReadFromDatabase = itrBook.getPublishing_house();
		}

		listOfClientsIds.add(client.getClient_id());

		assertEquals("Mary", clientService.readClient(client.getClient_id()).getName());
		assertEquals("Biel", clientService.readClient(client.getClient_id()).getSurname());
		assertEquals(234432567, clientService.readClient(client.getClient_id()).getPhone_number());
		assertEquals(123, clientService.readClient(client.getClient_id()).getHouse_number());
		assertEquals(5, clientService.readClient(client.getClient_id()).getApartment_number());
		assertEquals(22345, clientService.readClient(client.getClient_id()).getPostal_code());
		assertEquals(date, readFromDatabaseDate);
		assertEquals("Tom Hudson", bookAuthorReadFromDatabase);
		assertEquals(16.18f, bookPriceReadFromDatabase, 16.18f);
		assertEquals("How to sell a house", bookTitleReadFromDatabase);
		assertEquals("Gakken", bookPublishingHouseReadFromDatabase);
	}

	@Test
	public void testDeletingAClient() {
		Client client = new Client();
		client.setName("Amy");
		client.setSurname("Logan");
		client.setPhone_number(984432567);
		client.setStreet_name("Route 100");
		client.setHouse_number(4);
		client.setApartment_number(2);
		client.setPostal_code(20045);

		clientService.createAClient(client);
		clientService.deleteAClient(client.getClient_id());

		assertNull(clientService.readClient(client.getClient_id()));
	}

	@Test
	public void testReadingAClient() {
		Client client = new Client();
		client.setName("Robert");
		client.setSurname("Winethouse");
		client.setPhone_number(984499968);
		client.setStreet_name("Broad Street West");
		client.setHouse_number(4);
		client.setApartment_number(2);
		client.setPostal_code(26545);

		clientService.createAClient(client);
		Client readFromDatabaseClient = clientService.readClient(client.client_id);

		listOfClientsIds.add(client.getClient_id());

		assertEquals(client.getName(), readFromDatabaseClient.getName());
		assertEquals(client.getSurname(), readFromDatabaseClient.getSurname());
		assertEquals(client.getPhone_number(), readFromDatabaseClient.getPhone_number());
		assertEquals(client.getStreet_name(), readFromDatabaseClient.getStreet_name());
		assertEquals(client.getHouse_number(), readFromDatabaseClient.getHouse_number());
		assertEquals(client.getApartment_number(), readFromDatabaseClient.getApartment_number());
		assertEquals(client.getPostal_code(), readFromDatabaseClient.getPostal_code());
	}

	@Test
	public void testUpdatingClientsPhoneNumber() {
		Client client = new Client();
		client.setName("Michael");
		client.setSurname("Smith");
		client.setPhone_number(984426879);
		client.setStreet_name("Canterbury Road");
		client.setHouse_number(10);
		client.setApartment_number(1);
		client.setPostal_code(26598);

		clientService.createAClient(client);
		clientService.updatePhoneNumber(client.getClient_id(), 123321789);

		listOfClientsIds.add(client.getClient_id());

		assertEquals(123321789, clientService.readClient(client.getClient_id()).getPhone_number());
	}

	@AfterClass
	public static void deletingCreatedBookOrderAndClients() {
		Book bookToBeDeleted = (Book) session.load(Book.class, book.getId());
		session.delete(bookToBeDeleted);
		Order orderToBeDeleted = (Order) session.load(Order.class, order.getOrder_number());
		session.delete(orderToBeDeleted);
		Iterator it = listOfClientsIds.iterator();
		
		while (it.hasNext()) {
			int itrClients = (int) it.next();
			Client clientToBeDeleted = (Client) session.load(Client.class, itrClients);
			session.delete(clientToBeDeleted);
		}

		session.getTransaction().commit();
		session.close();
	}

}
