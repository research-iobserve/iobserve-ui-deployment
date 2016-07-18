package org.iobserve.services;
import org.iobserve.models.Service;
import org.iobserve.models.dataaccessobjects.ServiceDto;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceService extends AbstractSystemComponentService<Service,ServiceDto> {
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
