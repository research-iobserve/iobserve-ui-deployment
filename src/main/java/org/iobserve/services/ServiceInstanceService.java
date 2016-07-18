package org.iobserve.services;

import org.iobserve.models.*;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;

import javax.persistence.EntityManager;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceInstanceService extends AbstractSystemComponentService<ServiceInstance,ServiceInstanceDto> {
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
