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

import org.iobserve.models.dataaccessobjects.NodeGroupDto;
import org.iobserve.services.NodeGroupService;

/**
 * Created by cdor on 03.06.16.
 */

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public class NodeGroupResource implements ISystemComponentModelResource<NodeGroupDto> {

    private final NodeGroupService service;

    @Inject
    public NodeGroupResource(final NodeGroupService service) {
        this.service = service;
    }

    @GET
    @Path("/systems/{systemId}/nodegroups")
    @Override
    public List<NodeGroupDto> getAllBySystem(@PathParam("systemId") final String systemId) {
        return this.service.findAllBySystem(systemId);
    }

    @GET
    @Path("/nodegroups/{id}")
    @Override
    public NodeGroupDto getById(@PathParam("id") final String id) {
        return this.service.findById(id);
    }
}
