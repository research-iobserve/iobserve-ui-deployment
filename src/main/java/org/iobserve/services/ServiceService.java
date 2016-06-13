package org.iobserve.services;
import org.iobserve.models.Service;
import org.iobserve.models.dataaccessobjects.ServiceDto;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceService extends AbstractSystemComponentService<Service,ServiceDto> {
    @Override
    protected ServiceDto transformModelToDto(Service service) {
        return this.transformModelToDto(service);
    }
}
