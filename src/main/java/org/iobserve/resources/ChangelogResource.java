package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.services.ChangelogService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
    public Response postChangelog(@PathParam("systemId") String id, @Valid List<ChangelogDto> changelogs) throws ConstraintViolationException {
        service.addChangelogs(id, changelogs);

        return Response.status(200).type("text/plain")
                .entity("ok").build();
    }
}
