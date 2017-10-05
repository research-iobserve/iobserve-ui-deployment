/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.iobserve.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
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

}
