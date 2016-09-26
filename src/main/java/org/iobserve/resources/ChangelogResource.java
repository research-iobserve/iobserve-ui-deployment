package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.services.ChangelogService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChangelogResource {

    private final ChangelogService service;

    @Inject
    public ChangelogResource(ChangelogService service) {
        this.service = service;
    }

    @POST
    @Path("/systems/{systemId}/changelogs")
    @ValidateOnExecution
    public Response postChangelog(@PathParam("systemId") String id, @Valid List<ChangelogDto> changelogs) {
        boolean valid = service.addChangelogs(id, changelogs);
        if(valid){
            return Response.status(200).type("text/plain")
                    .entity("ok").build();
        }else{
            return Response.status(403).type("text/plain")
                    .entity("model is invalid").build();
        }
    }
}
