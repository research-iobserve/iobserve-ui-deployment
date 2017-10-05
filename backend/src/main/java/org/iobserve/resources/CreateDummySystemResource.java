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
    public CreateDummySystemResource(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @POST
    @Path("systems/createTest/{id}")
    public void createTestWithId(@PathParam("id") final String id) {
        final TestSystemCreator creator = new TestSystemCreator(this.entityManagerFactory);
        try {
            creator.createTestSystem(id);
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
