package org.iobserve;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Initialize EntityManager Factory
 * For Heroku, try to find the environment property DATABASE_URL, and transform
 * it into a valid jdbc URL to initialize properly the DB.
 * Adapted according to Heroku documentation
 *
 * @source https://github.com/mlecoutre/nounou/blob/master/src/main/java/org/mat/nounou/servlets/EntityManagerLoaderListener.java
 */
@WebListener
public class EntityManagerSetup implements ServletContextListener {

    private static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {

            Map<String, String> properties = new HashMap<>();
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            System.out.println("SET JDBC URL TO " + dbUrl);
            properties.put("javax.persistence.jdbc.url", dbUrl);
            properties.put("javax.persistence.jdbc.user", username);
            properties.put("javax.persistence.jdbc.password", password);

            emf = Persistence.createEntityManagerFactory("postgres", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Close the entity manager
     *
     * @param event ServletContextEvent not used
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if(emf.isOpen())
            emf.close();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}