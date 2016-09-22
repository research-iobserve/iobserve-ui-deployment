package org.iobserve.resources;

import org.iobserve.models.dataaccessobjects.CommunicationInstanceDto;
import org.iobserve.services.CommunicationInstanceService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class CommunicationInstanceResource implements SystemComponentModelResource<CommunicationInstanceDto> {
    private CommunicationInstanceService service;

    @Inject
    public CommunicationInstanceResource(CommunicationInstanceService service) {
        this.service = service;
    }

    @GET
    @Path("systems/{systemId}/communicationinstances")
    public List<CommunicationInstanceDto> getAllBySystem(@PathParam("systemId") String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/communicationinstances/{id}")
    public CommunicationInstanceDto getById(@PathParam("id") String id) {
        return this.service.findById(id);
    }
}
