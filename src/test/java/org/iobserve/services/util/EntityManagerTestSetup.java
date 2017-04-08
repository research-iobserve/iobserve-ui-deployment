package org.iobserve.services.util;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.util.*;
import org.iobserve.util.TestSystemCreator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.transaction.Transactional;
import java.net.URI;
import java.sql.DriverManager;
import java.util.*;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

public class EntityManagerTestSetup {

    private static EntityManagerFactory emf;

    private static void createFactory() {
        try {


            Map<String, String> properties = new HashMap<>();
            String dbUrl = "jdbc:derby:memory:playprototype;create=true";

            properties.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
            properties.put("javax.persistence.jdbc.url", dbUrl);
            properties.put("javax.persistence.jdbc.user", "");
            properties.put("javax.persistence.jdbc.password", "");

            emf = Persistence.createEntityManagerFactory("derby",properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static EntityManagerFactory getEntityManagerFactory() {
         if(emf == null){
             createFactory();
         }
         final TestSystemCreator creator = new TestSystemCreator(emf);
         creator.createTestSystem("system123");

         return emf;
    }
}