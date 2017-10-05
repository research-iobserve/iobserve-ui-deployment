package org.iobserve.resources;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.iobserve.util.TestSystemCreator;

/**
 * Created by cdor on 03.06.16.
 */
@Path("v1")
public class CreateDummySystemResource {
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public CreateDummySystemResource(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @POST
    @Path("systems/createTest/{id}")
    public void createTestWithId(@PathParam("id") String id) {
        TestSystemCreator creator = new TestSystemCreator(entityManagerFactory);
        try {
            creator.createTestSystem(id);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }















}
