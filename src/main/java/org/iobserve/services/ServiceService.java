package org.iobserve.services;
import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.Service;
import org.iobserve.models.dataaccessobjects.ServiceDto;
import org.iobserve.models.mappers.DtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.EntityToDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceService extends AbstractSystemComponentService<Service,ServiceDto> {

    @Inject
    public ServiceService(EntityManagerFactory entityManagerFactory, EntityToDtoMapper modelToDtoMapper, ServiceLocator serviceLocator, DtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    @Override
    protected ServiceDto transformModelToDto(Service service) {
        return this.modelToDtoMapper.transform(service);
    }

    @Override
    protected Service transformDtoToModel(ServiceDto serviceDto) {
        final Service service = dtoToBasePropertyEntityMapper.transform(serviceDto);

        return service;
    }
}
