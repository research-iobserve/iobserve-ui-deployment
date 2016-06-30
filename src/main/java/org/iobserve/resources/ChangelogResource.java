package org.iobserve.resources;

import org.iobserve.models.Changelog;
import org.iobserve.models.dataaccessobjects.ServiceInstanceDto;
import org.iobserve.services.ChangelogService;
import org.iobserve.services.CommunicationInstanceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class ChangelogResource {
    @Inject
    private ChangelogService service;

    @POST
    @Path("/systems/{systemId}/changelogs")
    public void postChangelog(@PathParam("systemId") String id, List<Changelog> changelogs) {
        service.addChangelogs(changelogs);
        return;
    }
}
