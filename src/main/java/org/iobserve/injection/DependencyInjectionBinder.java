package org.iobserve.injection;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.mappers.EntityToDtoMapper;
import org.iobserve.services.*;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Binds all classes that should be accessible via the @Inject annotation
 * @author Mathis Neumann (simpleTechs)
 * @see javax.inject.Inject
 * @see AbstractBinder
 */
public class DependencyInjectionBinder extends AbstractBinder {
    @Override
    protected void configure() {

        // EMFFactory is only used for EntityMangement construction
        this.bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
        this.bindFactory(EMFFactory.EMFactory.class).to(EntityManager.class).in(RequestScoped.class);

        this.bindFactory(EntityToDtoMapperFactory.class).to(EntityToDtoMapper.class).in(Singleton.class);

        // bind all services
        // TODO: possibility to use only generic services?
        this.bind(SystemService.class).to(SystemService.class).in(Singleton.class);
        this.bind(NodeService.class).to(NodeService.class).in(Singleton.class);
        this.bind(NodeGroupService.class).to(NodeGroupService.class).in(Singleton.class);
        this.bind(ServiceService.class).to(ServiceService.class).in(Singleton.class);
        this.bind(ServiceInstanceService.class).to(ServiceInstanceService.class).in(Singleton.class);
        this.bind(CommunicationService.class).to(CommunicationService.class).in(Singleton.class);
        this.bind(CommunicationInstanceService.class).to(CommunicationInstanceService.class).in(Singleton.class);
    }
}
