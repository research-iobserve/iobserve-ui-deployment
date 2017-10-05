package org.iobserve.resources;

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
public class NodeGroupResource implements ISystemComponentModelResource<NodeGroupDto> {

    private NodeGroupService service;

    @Inject
    public NodeGroupResource(NodeGroupService service) {
        this.service = service;
    }

    @GET
    @Path("/systems/{systemId}/nodegroups")
    @Override
    public List<NodeGroupDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/nodegroups/{id}")
    @Override
    public NodeGroupDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
