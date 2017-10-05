package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.services.CommunicationService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class CommunicationResource implements ISystemComponentModelResource<CommunicationDto> {

    private final CommunicationService service;

    @Inject
    public CommunicationResource(CommunicationService service) {
        this.service = service;
    }

    @GET
    @Path("/systems/{systemId}/communications")
    @Override
    public List<CommunicationDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/communications/{id}")
    @Override
    public CommunicationDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
