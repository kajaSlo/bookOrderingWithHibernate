package org.BookOrdering.service;

import java.util.Collection;
import org.BookOrdering.Dao.ClientDao;
import org.BookOrdering.Dao.ClientDaoImp;
import org.BookOrdering.models.Book;
import org.BookOrdering.models.Client;
import org.BookOrdering.models.Order;

public class ClientServiceImp implements ClientService {

	ClientDao clientDao = new ClientDaoImp();

	public void createAClient(Client client) {
		clientDao.createAClient(client);
	}

	public void createAClientAndAnOrder(Client client, Order order, Collection<Book> books) {
		clientDao.createAClientAndAnOrder(client, order, books);
	}

	public void deleteAClient(int clientId) {
		clientDao.deleteAClient(clientId);
	}

	public Client readClient(int clientId) {
		return clientDao.readClient(clientId);
	}

	public void updatePhoneNumber(int cliendId, int newPhoneNumber) {
		clientDao.updatePhoneNumber(cliendId, newPhoneNumber);
	}

}
