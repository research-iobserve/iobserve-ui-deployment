package org.iobserve.resources;

import org.iobserve.models.Communication;
import org.iobserve.models.dataaccessobjects.CommunicationDto;
import org.iobserve.models.dataaccessobjects.NodeGroupDto;
import org.iobserve.services.CommunicationService;
import org.iobserve.services.NodeGroupService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class CommunicationResource implements SystemComponentModelResource<CommunicationDto> {

    @Inject
    private CommunicationService service;


    @GET
    @Path("/systems/{systemId}/communications")
    @Override
    public List<CommunicationDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/systems/{systemId}/communications/{id}")
    @Override
    public CommunicationDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
