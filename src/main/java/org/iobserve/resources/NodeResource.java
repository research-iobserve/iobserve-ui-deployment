package org.iobserve.resources;

import org.iobserve.models.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by cdor on 03.06.16.
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class NodeResource extends AbstractEntityResource<Node>{

    @GET
    @Path("/nodes/{entityId}")
    @Override
    public Node getSingle(@PathParam("entityId") String entityId) {
        return super.getSingle(entityId);
    }

    @GET
    @Path("/blob/{systemId}/nodes")
    @Override
    public List<Node> getAll(@PathParam("systemId") String systemId) {
        System.out.println("Test all call");
        return super.getAll(systemId);
    }
}
