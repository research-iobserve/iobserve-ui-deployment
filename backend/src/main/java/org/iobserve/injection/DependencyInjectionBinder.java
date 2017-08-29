package org.iobserve.injection;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;
import org.iobserve.services.ChangelogService;
import org.iobserve.services.CommunicationInstanceService;
import org.iobserve.services.CommunicationService;
import org.iobserve.services.NodeGroupService;
import org.iobserve.services.NodeService;
import org.iobserve.services.SeriesElementService;
import org.iobserve.services.ServiceInstanceService;
import org.iobserve.services.ServiceService;
import org.iobserve.services.StatusInfoService;
import org.iobserve.services.SystemService;
import org.iobserve.services.TimeSeriesService;
import org.iobserve.services.UserGroupService;
import org.iobserve.services.websocket.ChangelogStreamService;

/**
 * Binds all classes that should be accessible via the @Inject annotation
 * 
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

        this.bindFactory(EntityToDtoMapperFactory.class).to(EntityToDtoMapper.class).in(Singleton.class);
        this.bindFactory(DtoToBasePropertyEntityMapperFactory.class).to(DtoToBasePropertyEntityMapper.class)
                .in(Singleton.class);

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
        this.bind(UserGroupService.class).to(UserGroupService.class).in(Singleton.class);
        this.bind(ChangelogService.class).to(ChangelogService.class).in(Singleton.class);
        this.bind(TimeSeriesService.class).to(TimeSeriesService.class).in(Singleton.class);
        this.bind(SeriesElementService.class).to(SeriesElementService.class).in(Singleton.class);
        this.bind(StatusInfoService.class).to(StatusInfoService.class).in(Singleton.class);
        // this.bind(ChangelogStreamService.class).to(ChangelogStreamService.class).in(Singleton.class);
        // // TODO via injection
    }
}
