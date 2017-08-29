package org.iobserve.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.iobserve.models.dataaccessobjects.UserGroupDto;
import org.iobserve.services.UserGroupService;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class UserGroupResource implements SystemComponentModelResource<UserGroupDto> {

    private final UserGroupService service;

    @Inject
    public UserGroupResource(final UserGroupService service) {
        this.service = service;
    }

    @Override
    @GET
    @Path("/usergroups/{id}")
    public UserGroupDto getById(@PathParam("id") final String id) {
        return this.service.findById(id);
    }

    @Override
    @GET
    @Path("/systems/{systemId}/usergroups")
    public List<UserGroupDto> getAllBySystem(@PathParam("systemId") final String systemId) {
        return this.service.findAllBySystem(systemId);
    }

}
