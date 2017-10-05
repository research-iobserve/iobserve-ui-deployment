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
package org.iobserve.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.glassfish.hk2.api.ServiceLocator;
import org.iobserve.models.dataaccessobjects.DataTransportObject;
import org.iobserve.models.mappers.IDtoToBasePropertyEntityMapper;
import org.iobserve.models.mappers.IEntityToDtoMapper;
import org.iobserve.models.util.AbstractBaseEntity;

/**
 * @author Mathis Neumann <mne@informatik.uni-kiel.de>
 */
public abstract class AbstractSystemComponentService<Model extends AbstractBaseEntity, ModelDto extends DataTransportObject>
        extends AbstractService<Model, ModelDto> {

    @Inject
    public AbstractSystemComponentService(final EntityManagerFactory entityManagerFactory,
            final IEntityToDtoMapper modelToDtoMapper, final ServiceLocator serviceLocator,
            final IDtoToBasePropertyEntityMapper dtoToBasePropertyEntityMapper) {
        super(entityManagerFactory, modelToDtoMapper, serviceLocator, dtoToBasePropertyEntityMapper);
    }

    public List<ModelDto> findAllBySystem(final String systemId) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final Query query = entityManager
                .createQuery("Select t from " + this.persistentClass.getSimpleName() + " t where systemId = :systemId")
                .setParameter("systemId", systemId);
        @SuppressWarnings("unchecked")
        final List<Model> result = query.getResultList();
        entityManager.close();
        return this.transformModelToDto(result);
    }
}
