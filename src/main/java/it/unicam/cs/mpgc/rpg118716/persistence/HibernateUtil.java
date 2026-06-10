package it.unicam.cs.mpgc.rpg118716.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class that manages the Hibernate SessionFactory.
 * Only one SessionFactory exists for the entire app.
 * Only manages the Hibernate lifecycle.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    /**
     * Returns the singleton SessionFactory, creating it if necessary.
     *
     * @return the Hibernate SessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Closes the SessionFactory, releasing all DB connections.
     * Should be called when the application shuts down.
     */
    public static synchronized void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}