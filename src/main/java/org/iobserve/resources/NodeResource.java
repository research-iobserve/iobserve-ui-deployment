package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.NodeDto;
import org.iobserve.services.NodeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by cdor on 03.06.16.
 */
@Path("v1/nodes")
@Produces(MediaType.APPLICATION_JSON)
public class NodeResource {

    @Inject
    private NodeService service;


    @GET
    public List<NodeDto> getNodes() {
        return this.service.findAll();
    }

    @GET
    @Path("/{systemId}")
    public NodeDto getNode(@PathParam("systemId") String id) {
        return this.service.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public NodeDto createNode(NodeDto node) {
        return node;
    }
}
