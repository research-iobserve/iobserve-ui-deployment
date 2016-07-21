package org.iobserve.services;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.iobserve.EntityManagerSetup;
import org.iobserve.IObserveApplication;
import org.iobserve.filters.CORSResponseFilter;
import org.iobserve.filters.GeneralExceptionMapper;
import org.iobserve.injection.DependencyInjectionBinder;
import org.iobserve.models.Changelog;
import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.resources.ChangelogResource;

import org.iobserve.resources.CreateDummySystemResource;
import org.iobserve.resources.SystemsResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import javax.ejb.Singleton;
import javax.enterprise.deploy.spi.Target;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;





/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */

public class ChangelogServiceTest extends JerseyTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected Application configure() {
        ResourceConfig config =   new IObserveApplication();
        return config;
    }

    @Override
    protected void configureClient(ClientConfig config) {
        super.configureClient(config);
    }

    @Test
    public void addChangelogs() throws Exception {
        Entity<String> entity = Entity.entity("",MediaType.APPLICATION_JSON_TYPE);
        //WebTarget target = target("v1/systems/createTest/system123");
        WebTarget target = target();
        Response response = target.request().post(entity);
    }


    private List<ChangelogDto> generateChangelogList(){
        final List<ChangelogDto> changelogs = new LinkedList<>();


        return changelogs;
    }


    private ChangelogDto generateCreateChangelog(){
        final ChangelogDto changelog = new ChangelogDto();

        return changelog;
    }





    private  String generateId(){
        return UUID.randomUUID().toString();
    }


}