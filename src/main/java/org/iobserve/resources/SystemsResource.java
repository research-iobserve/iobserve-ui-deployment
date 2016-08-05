package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.RevisionDto;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.services.ChangelogService;
import org.iobserve.services.SystemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class SystemsResource {
    @Inject
    private SystemService service;

    @Inject
    ChangelogService changelogService;

    @GET
    @Path("/systems")
    public List<SystemDto> getSystems() {
        return this.service.findAll();
    }

    @GET
    @Path("/systems/{systemId}")
    public SystemDto getSystem(@PathParam("systemId") String id) {
        return this.service.findById(id);
    }

    @GET
    @Path("/systems/{systemId}/revision")
    public RevisionDto getLatestRevision(@PathParam("systemId") String id) {
        return changelogService.getLatestRevision(id);
    }

}
