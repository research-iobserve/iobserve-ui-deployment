package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.services.ServiceInstanceService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceInstanceResource implements SystemComponentModelResource<ServiceInstanceDto> {

    private ServiceInstanceService service;

    @Inject
    public ServiceInstanceResource(ServiceInstanceService service) {
        this.service = service;
    }

    @GET
    @Path("systems/{systemId}/serviceinstances")
    public List<ServiceInstanceDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/serviceinstances/{id}")
    public ServiceInstanceDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
