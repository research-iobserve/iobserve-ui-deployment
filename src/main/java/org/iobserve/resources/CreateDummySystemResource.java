package org.iobserve.resources;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.util.*;
import org.iobserve.util.TestSystemCreator;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.*;

/**
 * Created by cdor on 03.06.16.
 */
@Path("v1")
public class CreateDummySystemResource {
    private final Random random;
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public CreateDummySystemResource(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.random = new Random();
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
