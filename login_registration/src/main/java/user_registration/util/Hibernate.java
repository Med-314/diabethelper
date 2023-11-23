package user_registration.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	
	static {
		  configuration = new Configuration()
				  .configure("/properties.xml");
		  sessionFactory = configuration.buildSessionFactory();
	}
	
	public static Session getSession(){
		  Session session = null;
		  if (sessionFactory != null) {
			  session = sessionFactory.openSession();
		  }
		  return session;
	}

}
