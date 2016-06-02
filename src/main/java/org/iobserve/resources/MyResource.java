package org.iobserve.resources;

import org.iobserve.models.System;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("v1/myresource")
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {

    @GET
    public System getIt() {
        System system = new System();
        system.setName("test");
        return system;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public System createSystem(System system) {
        system.setId("123");
        return system;
    }
}
