package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.DependencyInjectionBinder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * a factory for EntityManagerFactories which is provided to avoid scoping issues,
 * due to jerseys threading model.
 * Avoids that the factory has to be recreated, as it is a singleton instance.
 * @see DependencyInjectionBinder
 * based on "http://stackoverflow.com/a/28122910/1249001"
 */
public class EMFFactory implements Factory<EntityManagerFactory> { // TODO: use CloseableService

    private final String PERSISTENCE_UNIT = "postgres";

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    @Override
    public EntityManagerFactory provide() {
        return factory;
    }

    @Override
    public void dispose(EntityManagerFactory entityManager) {
        if(factory.isOpen()) {
            factory.close();
        }
    }

    /**
     * this is called/created per request
     */
    public static class EMFactory implements Factory<EntityManager> {


        private final EntityManager em;

        @Inject // from factory above
        public EMFactory (EntityManagerFactory emf){
            em = emf.createEntityManager();
        }

        @Override
        public EntityManager provide() {
            return em;
        }

        @Override
        public void dispose(EntityManager entityManagerFactory) {
            if(em.isOpen()) {
                em.close();
            }
        }
    }
}
