package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.services.NodeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class NodeResource implements SystemComponentModelResource<NodeDto> {

    @Inject
    private NodeService service;


    @GET
    @Path("/systems/{systemId}/nodes")
    public List<NodeDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/nodes/{id}")
    public NodeDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }

}
