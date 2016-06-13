package org.iobserve.resources;

import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.models.dataaccessobjects.NodeGroupDto;
import org.iobserve.services.NodeGroupService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by cdor on 03.06.16.
 */

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class NodeGroupResource {

    @Inject
    private NodeGroupService service;


    @GET
    @Path("/systems/{systemId}/nodegroups")
    public List<NodeGroupDto> getBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/systems/{systemId}/nodegroups/{id}")
    public NodeGroupDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public NodeGroupDto createNode(NodeGroupDto node) {
        return node;
    }
}
