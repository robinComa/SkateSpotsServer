package Util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = configureSessionFactory();

	private static SessionFactory configureSessionFactory()    
            throws HibernateException {    
        Configuration configuration = new Configuration();    
        configuration.configure();    
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()    
                .applySettings(configuration.getProperties())    
                .buildServiceRegistry();    
        return configuration.buildSessionFactory(serviceRegistry);    
    }   

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}