package org.iobserve.services;

import org.iobserve.models.ServiceInstance;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;

/**
 * Created by cdor on 13.06.16.
 */
public class ServiceInstanceService extends AbstractSystemComponentService<ServiceInstance,ServiceInstanceDto> {
    @Override
    protected ServiceInstanceDto transformModelToDto(ServiceInstance serviceInstance) {
        return this.transformModelToDto(serviceInstance);
    }
}
