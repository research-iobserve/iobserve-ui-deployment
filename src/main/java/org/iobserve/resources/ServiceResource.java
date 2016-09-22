package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.ServiceDto;
import org.iobserve.services.ServiceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceResource implements SystemComponentModelResource<ServiceDto> {

    private ServiceService service;

    @Inject
    public ServiceResource(ServiceService service) {
        this.service = service;
    }

    @GET
    @Path("systems/{systemId}/services")
    public List<ServiceDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/services/{id}")
    public ServiceDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
