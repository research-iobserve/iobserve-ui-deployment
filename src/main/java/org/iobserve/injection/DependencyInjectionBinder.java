package org.iobserve.injection;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.iobserve.injection.EMFFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Mathis Neumann (simpleTechs)
 */
public class DependencyInjectionBinder extends AbstractBinder {
    @Override
    protected void configure() {

        // EMFFactory is only used for EntityMangement construction
        this.bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
        this.bindFactory(EMFFactory.EMFactory.class).to(EntityManager.class).in(RequestScoped.class);

        // TODO: bind generic service

    }
}
