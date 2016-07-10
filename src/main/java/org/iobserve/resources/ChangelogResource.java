package org.iobserve.resources;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.iobserve.models.Changelog;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.services.ChangelogService;
import org.iobserve.services.CommunicationInstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChangelogResource {
    @Inject
    private ChangelogService service;

    @POST
    @Path("/systems/{systemId}/changelogs")
    public void postChangelog(@PathParam("systemId") String id, List<ChangelogDto> changelogs) {
        service.addChangelogs(id, changelogs);
    }
}
