package org.iobserve.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.iobserve.models.dataaccessobjects.RevisionDto;
import org.iobserve.models.dataaccessobjects.SystemDto;
import org.iobserve.services.ChangelogService;
import org.iobserve.services.SystemService;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class SystemsResource {

    private final SystemService service;
    private final ChangelogService changelogService;

    @Inject
    public SystemsResource(final SystemService service, final ChangelogService changelogService) {
        this.service = service;
        this.changelogService = changelogService;
    }

    @GET
    @Path("/systems")
    public List<SystemDto> getSystems() {
        return this.service.findAll();
    }

    @GET
    @Path("/systems/{systemId}")
    public SystemDto getSystem(@PathParam("systemId") final String id) {
        return this.service.findById(id);
    }

    @GET
    @Path("/systems/{systemId}/revision")
    public RevisionDto getLatestRevision(@PathParam("systemId") final String id) {
        return this.changelogService.getLatestRevision(id);
    }

    @POST
    @Path("/systems/")
    public void newSystem(@Valid final SystemDto system) throws ConstraintViolationException {
        this.service.createSystem(system);
    }

}
