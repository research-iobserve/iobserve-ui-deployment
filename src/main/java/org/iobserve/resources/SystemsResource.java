package org.iobserve.resources;

import org.iobserve.models.System;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("v1/systems")
@Produces(MediaType.APPLICATION_JSON)
public class SystemsResource {

    private EntityManager entityManager;

    @Inject
    public SystemsResource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GET
    public List getSystems() {
        Query query = entityManager.createQuery("SELECT e FROM System e");
        return query.getResultList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public System createSystem(System system) {
        system.setId("123");
        return system;
    }
}
