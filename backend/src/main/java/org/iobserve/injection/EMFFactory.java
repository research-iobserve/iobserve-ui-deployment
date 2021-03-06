package org.iobserve.injection;

import org.glassfish.hk2.api.Factory;
import org.iobserve.EntityManagerSetup;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * A factory for EntityManagerFactories which is provided to avoid scoping issues,
 * due to jerseys threading model.
 * Avoids that the factory has to be recreated, as it is a singleton instance.
 * @see DependencyInjectionBinder
 * @source http://stackoverflow.com/a/28122910/1249001
 */
public class EMFFactory implements Factory<EntityManagerFactory> { // TODO: use CloseableService

    private final EntityManagerFactory factory = EntityManagerSetup.getEntityManagerFactory();

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
