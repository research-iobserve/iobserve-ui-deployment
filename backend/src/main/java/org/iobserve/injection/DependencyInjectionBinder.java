package org.iobserve.injection;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.services.*;
import org.iobserve.services.websocket.ChangelogStreamService;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Binds all classes that should be accessible via the @Inject annotation
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 * @see javax.inject.Inject
 * @see AbstractBinder
 */
public class DependencyInjectionBinder extends AbstractBinder {
    @Override
    protected void configure() {

        // EMFFactory is only used for EntityMangement construction
        this.bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
        this.bindFactory(EMFFactory.EMFactory.class).to(EntityManager.class).in(RequestScoped.class);

        this.bindFactory(EntityToDtoMapperFactory.class).to(IEntityToDtoMapper.class).in(Singleton.class);
        this.bindFactory(DtoToBasePropertyEntityMapperFactory.class).to(IDtoToBasePropertyEntityMapper.class).in(Singleton.class);

        this.bindFactory(ChangelogStreamServiceFactory.class).to(ChangelogStreamService.class).in(Singleton.class);

        // bind all services
        // TODO: possibility to use only generic services?
        this.bind(SystemService.class).to(SystemService.class).in(Singleton.class);
        this.bind(NodeService.class).to(NodeService.class).in(Singleton.class);
        this.bind(NodeGroupService.class).to(NodeGroupService.class).in(Singleton.class);
        this.bind(ServiceService.class).to(ServiceService.class).in(Singleton.class);
        this.bind(ServiceInstanceService.class).to(ServiceInstanceService.class).in(Singleton.class);
        this.bind(CommunicationService.class).to(CommunicationService.class).in(Singleton.class);
        this.bind(CommunicationInstanceService.class).to(CommunicationInstanceService.class).in(Singleton.class);
        this.bind(ChangelogService.class).to(ChangelogService.class).in(Singleton.class);
        this.bind(TimeSeriesService.class).to(TimeSeriesService.class).in(Singleton.class);
        this.bind(SeriesElementService.class).to(SeriesElementService.class).in(Singleton.class);
        this.bind(StatusInfoService.class).to(StatusInfoService.class).in(Singleton.class);
        // this.bind(ChangelogStreamService.class).to(ChangelogStreamService.class).in(Singleton.class); // TODO via injection
    }
}
