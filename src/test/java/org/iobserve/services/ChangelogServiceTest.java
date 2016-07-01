package org.iobserve.services;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.iobserve.models.Changelog;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.resources.ChangelogResource;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.LinkedList;
import java.util.List;


/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class ChangelogServiceTest extends JerseyTest {

    @Before
    public void setUp() throws Exception {

    }

    @Override
    protected Application configure() {
        return new ResourceConfig(ChangelogResource.class);
    }



    @Test
    public void addChangelogs() throws Exception {
        final List<Changelog> changelogs = new LinkedList<>();

        final Changelog changelog = new Changelog();
        changelog.setId("changelog-123");
        changelog.setOperation(ChangelogOperation.CREATE);


        changelogs.add(changelog);

        Entity<List<Changelog>> entity = new Entity<>(changelogs, MediaType.APPLICATION_JSON_TYPE);
        final Response response = target("v1/systems/system123/changelogs").request().post(entity);
    }


}