package org.BookOrdering.models;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConfigurationClass {
	private final static SessionFactory sF = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			return configuration.buildSessionFactory();
		} catch (Throwable exception) {
			throw new ExceptionInInitializerError("Cannot create SessioFactory" + exception);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sF;
	}
	
}