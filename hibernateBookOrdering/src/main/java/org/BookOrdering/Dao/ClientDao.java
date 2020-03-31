package org.BookOrdering.Dao;

import java.util.Collection;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.Order;

public interface ClientDao {

	public void createAClient(Client client);
	public void createAClientAndAnOrder(Client client, Order order, Collection<Book> books);
	public void deleteAClient(int clientId);
	public Client readClient(int clientId);
	public void updatePhoneNumber(int cliendId, int newPhoneNumber);
	
}
