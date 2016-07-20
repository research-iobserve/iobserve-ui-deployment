package org.iobserve.services;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.iobserve.models.Changelog;
import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.dataaccessobjects.ChangelogDto;
import org.iobserve.models.util.ChangelogOperation;
import org.iobserve.resources.ChangelogResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import javax.ws.rs.core.Application;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;





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