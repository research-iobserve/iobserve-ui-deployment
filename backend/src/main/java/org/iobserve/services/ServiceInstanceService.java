package org.iobserve.services;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.*;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceInstanceService extends AbstractSystemComponentService<ServiceInstance,ServiceInstanceDto> {

    @Inject
    public ServiceInstanceService(EntityManagerFactory entityManagerFactory, IEntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected ServiceInstanceDto transformModelToDto(ServiceInstance serviceInstance) {
        return this.modelToDtoMapper.transform(serviceInstance);
    }

    @Override
    protected ServiceInstance transformDtoToModel(ServiceInstanceDto serviceInstanceDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final ServiceInstance serviceInstance = dtoToBasePropertyEntityMapper.transform(serviceInstanceDto);

        serviceInstance.setNode(entityManager.find(Node.class, serviceInstanceDto.getNodeId()));
        serviceInstance.setService(entityManager.find(org.iobserve.models.Service.class, serviceInstanceDto.getServiceId()));

        entityManager.close();

        return serviceInstance;
    }
}
